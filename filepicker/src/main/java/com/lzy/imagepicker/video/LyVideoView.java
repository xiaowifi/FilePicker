package com.lzy.imagepicker.video;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.lzy.imagepicker.R;
import com.lzy.imagepicker.view.RedSpotView;

/**
 *基于 videoview 播放视频。
 */
public class LyVideoView extends RelativeLayout implements View.OnTouchListener {
    private String TAG="LyVideoView";
    private VideoView video_content;

    private String path;//播放地址。
    private ImageView img_back;
    private RedSpotView red_view;
    private ImageView image_video_play;
    private SeekBar bottom_seek_progress;
    private TextView t_play_time;
    private LinearLayout l_laoding;
    private TextView t_percentage;
    private TextView t_network;
    private ProgressBar pr_loading;

    private boolean showRed=false;
    private LinearLayout lin_bottom;
    private long uptime;
    private long downtime;
    MediaPlayer mmediaPlayer;
    Handler handler=new Handler();
    private long duration;

    public void setPath(String path) {
        this.path = path;
    }

    public void start(){
        video_content.setVideoPath(path);
        video_content.start();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public LyVideoView(Context context) {
        super(context);
        initViews();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public LyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public ImageView getImg_back() {
        return img_back;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initViews() {
        //添加子项到内容中。
        addView(LayoutInflater.from(getContext()).inflate(R.layout.ly_video_manger,null));
        video_content = findViewById(R.id.video_content);
        img_back = findViewById(R.id.imge_back);
        red_view = findViewById(R.id.red_view);
        image_video_play = findViewById(R.id.image_video_play);
        bottom_seek_progress = findViewById(R.id.bottom_seek_progress);
        t_play_time = findViewById(R.id.t_play_time);
        l_laoding = findViewById(R.id.l_laoding);
        t_percentage = findViewById(R.id.t_percentage);
        t_network = findViewById(R.id.t_network);
        pr_loading = findViewById(R.id.bottom_progress);
        lin_bottom = findViewById(R.id.lin_bottom);
        pr_loading.setMax(100);
        //全局捕获触摸事件。
        setOnTouchListener(this);
        image_video_play.setOnClickListener(onImagePlayListener);
        initVideo();
        initSeekbar();
    }

    /**
     * 初始化拖动条。
     */
    private void initSeekbar() {
        bottom_seek_progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (!b){
                    return;
                }
                long newposition =  (duration*i)/1000L;
                t_play_time.setText(" - "+generateTime(newposition));
                video_content.seekTo((int) newposition);
                Log.e(TAG, "onProgressChanged: "+newposition );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                video_content.pause();
                handler.removeCallbacks(showSeekBar);
                //Log.e(TAG, "onStartTrackingTouch: "+mDragging );
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                try {
                    video_content.start();
                    handler.postDelayed(showSeekBar,200);
                    image_video_play.setImageResource(R.drawable.ic_video_pause);
                }catch (Exception e){

                }

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initVideo() {
        l_laoding.setVisibility(VISIBLE);
        lin_bottom.setVisibility(GONE);
        video_content.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mmediaPlayer=mediaPlayer;
                mmediaPlayer.setOnBufferingUpdateListener(bufferingUpdateListener);
                //播放器内核准备好了。
                l_laoding.setVisibility(GONE);
                lin_bottom.setVisibility(VISIBLE);
                image_video_play.setImageResource(R.drawable.ic_video_pause);
                handler.removeCallbacks(showSeekBar);
                handler.postDelayed(showSeekBar,200);
                duration = mediaPlayer.getDuration();
            }
        });

        video_content.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
                switch (i){
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        //填充缓冲区后，MediaPlayer正在恢复播放。。
                        l_laoding.setVisibility(GONE);
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        l_laoding.setVisibility(VISIBLE);
                        break;
                    case MediaPlayer.MEDIA_ERROR_IO:
                        //文件或网络相关的操作错误。
                        break;
                }
                return false;
            }
        });
        //播放器发生错误。
        video_content.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                handler.removeCallbacks(showSeekBar);
                return false;
            }
        });
        //播放完成、
        video_content.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                handler.removeCallbacks(showSeekBar);
            }
        });
    }

    MediaPlayer.OnBufferingUpdateListener bufferingUpdateListener=new MediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
            Log.e(TAG, "onBufferingUpdate: "+i );
            pr_loading.setProgress(i);
        }
    };



    @Override
    public boolean onTouch(View view, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP){
            uptime = System.currentTimeMillis();
            Log.e(TAG, "onTouch: "+(uptime-downtime ));
            if (uptime-downtime<200){
                showRed=!showRed;
                img_back.setVisibility(showRed?GONE:VISIBLE);
                lin_bottom.setVisibility(showRed?GONE:VISIBLE);
            }else {
                red_view.setVisibility(GONE);
            }
        }else if (event.getAction()==MotionEvent.ACTION_DOWN){
            downtime = System.currentTimeMillis();
        }else if (event.getAction()==MotionEvent.ACTION_MOVE){
            if (showRed){
                red_view.setVisibility(VISIBLE);
                red_view.onShow(event.getX(),event.getY());
            }else {
                red_view.setVisibility(GONE);
            }
        }
        return true;
    }


     Runnable showSeekBar = new Runnable() {
        @Override
        public void run() {

            if (!video_content.isPlaying()){
                return;
            }
            long position = video_content.getCurrentPosition();
            t_play_time.setText(" - "+generateTime(position));
            if (bottom_seek_progress != null) {
                if (duration > 0) {
                    long pos = 1000L * position / duration;
                    bottom_seek_progress.setProgress((int) pos);
                    handler.postDelayed(showSeekBar,200);
                }
            }
        }
    };


    /**
     * 点击播放按钮，
     */
    View.OnClickListener onImagePlayListener=new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (video_content.isPlaying()){
                video_content.pause();
                image_video_play.setImageResource(R.drawable.ic_video_play);
            }else {
                video_content.start();
                image_video_play.setImageResource(R.drawable.ic_video_pause);
            }
            handler.removeCallbacks(showSeekBar);
            handler.postDelayed(showSeekBar,200);
        }
    };

    /**
     * 格式化时间。
     * @param time
     * @return
     */
    private  String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
    }

    public void stopPlayback(){
        if (video_content!=null){
            video_content.stopPlayback();
        }

    }

}
