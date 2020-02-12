package com.example.music;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.music.adapter.WelcomeAdapter;
import com.example.music.fragment.OneFragment;
import com.example.music.fragment.TwoFragment;
import com.example.music.fragment.WelcomeFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {
    private ViewPager vp;
    private LinearLayout ll;
    private static final String TAG = "WelcomeActivity";
    private List<Fragment> list;
    private WelcomeFragment fragmenth;
    private int index=0;

    /*
    //Handler机制: 是一个消息传递机制,作用是将子线程需要的UI操作,传递到UI线程执行,保证线程安全
     */
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            vp.setCurrentItem(index++,false);
        }
    };

    private List<ImageView> listI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //得到sp对象 参数一 xml文件的名字  参数二 模式 MODE_PRIVATE 指定该SharedPreferences数据只能被本应用程序读写
        SharedPreferences welcome = getSharedPreferences("welcome", MODE_PRIVATE);
        boolean welcome1 = welcome.getBoolean("welcome", false);//直接读取
        if (welcome1) { //判断是否是第一次运行
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
        }

        vp = (ViewPager) findViewById(R.id.vp);
        ll = (LinearLayout) findViewById(R.id.ll);
        list=new ArrayList<>();
        list.add(new OneFragment());
        list.add(new TwoFragment());
        fragmenth=new WelcomeFragment();
        list.add(fragmenth);

        WelcomeAdapter welcomeAdapter = new WelcomeAdapter(getSupportFragmentManager(), list);
        vp.setAdapter(welcomeAdapter);

        listI=new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            ImageView imageView = new ImageView(this);
            if (i == 0) {
                imageView.setImageResource(R.drawable.zz);
            } else {
                imageView.setImageResource(R.drawable.yy);
            }
            listI.add(imageView);
            ll.addView(imageView);
        }

        //得到sp对象
        SharedPreferences welcome2 = getSharedPreferences("welcome", MODE_PRIVATE);
        SharedPreferences.Editor edit = welcome2.edit(); //获取编辑对象
        edit.putBoolean("welcome",true); //写数据
        edit.commit(); //提交数据


        //进行自动翻页
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1); //子线程不能更新ui 所以借助Handler
            }
        },500,1000);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == list.size() - 1) {
                    fragmenth.handler.sendEmptyMessage(1);

                }
                for (int i = 0; i < listI.size(); i++) {
                    if (i == position) {
                        listI.get(i).setImageResource(R.drawable.zz);
                    } else {
                        listI.get(i).setImageResource(R.drawable.yy);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
