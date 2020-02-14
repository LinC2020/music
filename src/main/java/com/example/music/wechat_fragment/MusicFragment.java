package com.example.music.wechat_fragment;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.music.R;
import com.example.music.adapter.MyAdapter;
import com.example.music.services.MusicService;
import com.example.music.species.Music;
import com.example.music.util.MusicUtils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends Fragment {
    private ListView lv;
    private static MusicService.MyBinder binder;

    private Button pause;
    private Button reStart;
    private Button last;
    private Button next;

    public static Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {  //用来接收广播发过来的消息
            super.handleMessage(msg);
            if (msg.what == 110) {
                binder.callPause();
            } else if (msg.what == 120) {
                binder.callRestart();
            }else if (msg.what == 130) {
                binder.callPlayLast();
            }else if (msg.what == 140) {
                binder.callPlayNext();
            }
        }
    };

    public MusicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View inflate = inflater.inflate(R.layout.fragment_music, container, false);


        pause = (Button)inflate.findViewById(R.id.pause);
        reStart = (Button)inflate.findViewById(R.id.reStart);
        last = (Button)inflate.findViewById(R.id.last);
        next = (Button)inflate.findViewById(R.id.next);

        lv = (ListView)inflate.findViewById(R.id.lv);

        List<Music> list = MusicUtils.getMusicList(getActivity());
        MyAdapter myAdapter = new MyAdapter(list, getActivity());
        lv.setAdapter(myAdapter);
        //启动服务
        Intent intent = new Intent(getActivity(), MusicService.class);
        getActivity().startService(intent);

        ServiceConnection serviceConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                binder= (MusicService.MyBinder) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };

        getActivity().bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //播放的功能放到了service 不在活动页面
                binder.callPlay(position);
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binder.callPause();
            }
        });

        reStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binder.callRestart();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binder.callPlayNext();
            }
        });

        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binder.callPlayLast();
            }
        });
        return inflate;
    }

}
