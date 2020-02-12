package com.example.music.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.music.MainActivity;
import com.example.music.R;

import java.util.Timer;
import java.util.TimerTask;


public class WelcomeFragment extends Fragment {
    private ImageView three;
    private TextView time;
    private Button btn;
    private Timer timer;

    private int js=5;
    @SuppressLint("HandlerLeak")
    public Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(2);
                    }
                }, 0, 1000);
            } else {
                time.setText("倒计时"+js--+"秒");
                if (js == 0) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        }
    };


    public WelcomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View inflate = inflater.inflate(R.layout.fragment_welcome, container, false);
        three = (ImageView) inflate.findViewById(R.id.three);
        time = (TextView) inflate.findViewById(R.id.time);
        btn = (Button)inflate.findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return inflate;
    }
}
