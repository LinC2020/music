package com.example.music.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.example.music.species.Music;
import com.example.music.util.MusicUtils;

import java.io.IOException;
import java.util.List;

public class MusicService extends Service {

    private MediaPlayer mediaPlayer;
    private List<Music> list;

    /**
     * 1.播放
     * 2.暂停
     * 3.上一首
     * 4.下一首
     * 5.加载数据源
     * 6.通知
     * 7.通知自定义布局
     *
     * 既能在后台运行，有能调用服务器中的方法
     *
     * 1.start 2.bind
     *
     */
    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        list= MusicUtils.getMusicList(this);
    }

    //中间的代理对象
    public class MyBinder extends Binder{
        public void callPlay(int position) {
            play(position);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    //services  中开始播放的方法
    public void play(int position) {
        try {
            mediaPlayer.setDataSource(list.get(position).getData());
            mediaPlayer.prepareAsync(); //异步
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start(); //开始播放
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
