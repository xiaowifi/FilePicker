package com.lzy.imagepicker.video;

import android.media.MediaPlayer;

/**
 * 落叶播放器的生命周期。QWQ
 */
public interface LYLifeCycle {
    void onPrepared(MediaPlayer mp);//准备成功之后
    void onVideoSizeChanged(MediaPlayer mp, int width, int height);//播放器尺寸发生改变的时候。
    void onCompletion(MediaPlayer mp);//播放完成后。
    void onError(MediaPlayer mp, int what, int extra);//播放发生错误。
    void onInfo(MediaPlayer mp, int what, int extra);//状态信息回调
    void onBufferingUpdate(MediaPlayer mp, int percent);//缓冲进度回调
    void onDestroyed();//当布局被销毁的时候。
}
