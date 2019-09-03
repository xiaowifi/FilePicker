package com.lzy.imagepicker.video;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * 基于Android 的播放器内核实现的播放器。
 */
@SuppressLint("NewApi")
public class LYVideo extends FrameLayout implements TextureView.SurfaceTextureListener,
        MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener, MediaPlayer.OnVideoSizeChangedListener {
    String TAG="LYVideo";
    private LYTextureView textureView;
    private Surface surface;
    LYLifeCycle lyLifeCycle;
    private MediaPlayer mMediaPlayer;
    private SurfaceTexture mSurfaceTexture;

    public void setLyLifeCycle(LYLifeCycle lyLifeCycle) {
        this.lyLifeCycle = lyLifeCycle;
    }

    public LYVideo(Context context) {
        super(context);
        initLayout();
    }

    public LYVideo( Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout();
    }

    public LYVideo( Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    private void initLayout() {
        //开启硬件加速、
        Activity activity= (Activity) getContext();
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        textureView = new LYTextureView(getContext());
        textureView.setKeepScreenOn(true);
        textureView.setLayerType(TextureView.LAYER_TYPE_SOFTWARE, null);
        this.addView(textureView,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        textureView.setSurfaceTextureListener(this);
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        if (mSurfaceTexture == null) {
            mSurfaceTexture = surface;
            Log.e(TAG, "mSurfaceTexture:null " );
            initMediaPlayer();
        } else {
            Log.e(TAG, "setSurfaceTexture: " );
            textureView.setSurfaceTexture(mSurfaceTexture);
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
      //  Log.e(TAG, "onSurfaceTextureSizeChanged: " );
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        Log.e(TAG, "onSurfaceTextureDestroyed: " );
        if (lyLifeCycle!=null){
            lyLifeCycle.onDestroyed();
        }
        return mSurfaceTexture==null;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
      //  Log.e(TAG, "onSurfaceTextureUpdated: " );

    }
    String path;
    public void setVideoPath(String path){
        this.path=path;
        release();
        removeAllViews();
        initLayout();
    }
    private void initMediaPlayer() {
        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(path);
            if (surface==null){
                surface=new Surface(mSurfaceTexture);
            }
            mMediaPlayer.setSurface(surface);
            mMediaPlayer.setOnBufferingUpdateListener(this);
            mMediaPlayer.setOnCompletionListener(this);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnErrorListener(this);
            mMediaPlayer.setOnInfoListener(this);
            mMediaPlayer.setOnVideoSizeChangedListener(this);
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp){
                    if (lyLifeCycle!=null){
                        lyLifeCycle.onPrepared(mp);
                    }
                    mMediaPlayer.start();
                }
            });
            mMediaPlayer.prepare();
        }catch (Exception e){
        }
    }


    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        if (lyLifeCycle!=null){
            lyLifeCycle.onBufferingUpdate(mp,percent);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (lyLifeCycle!=null){
            lyLifeCycle.onCompletion(mp);
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (lyLifeCycle!=null){
            lyLifeCycle.onPrepared(mp);
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        if (lyLifeCycle!=null){
            lyLifeCycle.onError(mp,what,extra);
        }
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        if (lyLifeCycle!=null){
            lyLifeCycle.onInfo(mp,what,extra);
        }
        return true;
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        if (lyLifeCycle!=null){
            lyLifeCycle.onVideoSizeChanged(mp,width,height);
        }
    }

    /**
     * 停止播放并且那个啥。
     */
    public void stopPlayback() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    /**
     * 释放那个啥。
     */
    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;

        }
    }
}
