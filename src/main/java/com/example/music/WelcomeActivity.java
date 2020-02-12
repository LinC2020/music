package com.example.music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.music.adapter.WelcomeAdapter;
import com.example.music.fragment.OneFragment;
import com.example.music.fragment.TwoFragment;
import com.example.music.fragment.WelcomeFragment;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {
    private ViewPager vp;
    private LinearLayout ll;
    private static final String TAG = "WelcomeActivity";
    private List<Fragment> list;
    private WelcomeFragment fragmenth;

    private List<ImageView> listI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Log.i(TAG, "onCreate: ");
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
