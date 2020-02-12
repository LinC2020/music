package com.example.music;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.music.wechat_fragment.DiscoverFragment;
import com.example.music.wechat_fragment.MeFragment;
import com.example.music.wechat_fragment.MessageFragment;
import com.example.music.wechat_fragment.MusicFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager vp;
    private RadioGroup rg;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private static final String TAG = "MainActivity";
    private List<Fragment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vp = (ViewPager) findViewById(R.id.vp);
        rg = (RadioGroup) findViewById(R.id.rg);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);
        rb3 = (RadioButton) findViewById(R.id.rb3);
        rb4 = (RadioButton) findViewById(R.id.rb4);

        list = new ArrayList<>();

        list.add(new MusicFragment());
        list.add(new MessageFragment());
        list.add(new DiscoverFragment());
        list.add(new MeFragment());

        vp.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb1:
                        vp.setCurrentItem(0,false);
                        break;
                    case R.id.rb2:
                        vp.setCurrentItem(1,false);
                        break;

                    case R.id.rb3:
                        vp.setCurrentItem(2,false);
                        break;

                    case R.id.rb4:
                        vp.setCurrentItem(3,false);
                        break;
                }
            }
        });

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    rb1.setChecked(true);
                } else if (position == 1) {
                    rb2.setChecked(true);
                } else if (position == 2) {
                    rb3.setChecked(true);
                } else if (position == 3) {
                    rb4.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
