package com.example.music.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteAction;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.example.music.R;
import com.example.music.species.Music;
import com.example.music.util.MusicUtils;

import java.io.IOException;
import java.util.List;

/**
 * 服务
 */
public class MusicService extends Service {

    private int index=0;  //播放音乐的下标
    private MediaPlayer mediaPlayer;  //播放音乐时用的
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

        public void callPause(){
            pause();
        }

        public void callRestart(){
            reStart();
        }

        public void callPlayNext(){
            playNext();
        }

        public void callPlayLast(){
            playLast();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Notification.Builder builder = new Notification.Builder(this);  //创建通知
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //自定义广播中的布局
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notificat_layout);
        builder.setCustomContentView(remoteViews);
        /**
         * 通过一个PendingIntent 发送一个intent.
         * getBroadcast的方法是跳往广播的一个intent.
         * getBroadcast的参数,1,上下文,2是请求码唯一即可,3,intent对象,4,PendingIntent的创建方式.
         * setOnClickPendingIntent.这个方法可以给组件设置一个点击事件.参数:1,组件id,2pendingIntent对象
         * 也就是点击以后要跳转到何方.
         */

        Intent intent1 = new Intent();
        intent1.setAction("com.pause");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 110, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notificat_pause,pendingIntent);

        Intent intent2 = new Intent();
        intent2.setAction("com.restart");
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(this, 120, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notificat_restart,pendingIntent2);

        Intent intent3 = new Intent();
        intent3.setAction("com.last");
        PendingIntent pendingIntent3 = PendingIntent.getBroadcast(this, 130, intent3, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notificat_lsat,pendingIntent3);

        Intent intent4 = new Intent();
        intent4.setAction("com.next");
        PendingIntent pendingIntent4 = PendingIntent.getBroadcast(this, 140, intent4, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notificat_next,pendingIntent4);

        Notification build = builder.build();
        startForeground(1,build); //发送通知
        return super.onStartCommand(intent, flags, startId);
    }

    //services  中开始播放的方法
    public void play(int position) {
        mediaPlayer.reset();
        try {
            index=position;
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

    //暂停
    public void pause(){
        mediaPlayer.pause();
    }
    //恢复播放
    public void reStart(){
        mediaPlayer.start();
    }
    //下一首
    public void playNext(){
        if (++index > list.size()-1) {
            index=0;
        }
        play(index);
    }

    //上一首
    public void playLast(){
        if (--index <= 0) {
            index=0;
        }
        play(index);
    }
}
