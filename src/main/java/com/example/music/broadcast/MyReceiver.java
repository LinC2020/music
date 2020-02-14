package com.example.music.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.music.services.MusicService;
import com.example.music.wechat_fragment.MusicFragment;

/**
 * 广播接受器
 */
public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case "com.pause":
                MusicFragment.handler.sendEmptyMessage(110);
                break;

            case "com.restart":
                MusicFragment.handler.sendEmptyMessage(120);
                break;

            case "com.last":
                MusicFragment.handler.sendEmptyMessage(130);
                break;

            case "com.next":
                MusicFragment.handler.sendEmptyMessage(140);
                break;
        }
    }
}
