package com.example.music.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.music.species.Music;

import java.util.ArrayList;
import java.util.List;

public class MusicUtils {
    private static final String TAG = "MusicUtils";

    public static List<Music> getMusicList(Context context){
        List<Music> list=new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            Log.i(TAG, "getMusicList: "+"不为空");
            while (cursor.moveToNext()) {
                Music music = new Music();
                music.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                music.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
                String string = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                music.setDuration(string);
                music.setData(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
                if (string.length()>4) {
                    list.add(music);
                }
            }
            cursor.close();
        } else {
            Log.i(TAG, "getMusicList: "+"为空");
        }
        return list;
    }

    //格式化时间
    public static String formatTime(int time){
        //为什么要除1000呢 因为1000毫秒=1秒
        if (time / 1000 % 60 < 10) {
            return time / 1000 / 60 + ":0" + time / 1000 % 60;
        } else {
            return time / 1000 / 60 + ":" + time / 1000 % 60;
        }
    }
}
