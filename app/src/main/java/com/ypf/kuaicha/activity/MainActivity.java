package com.ypf.kuaicha.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;

import com.ypf.kuaicha.R;
import com.ypf.kuaicha.TApplication;
import com.ypf.kuaicha.fragment.RecordFragment;
import com.ypf.kuaicha.fragment.SearchFragment;
import com.ypf.kuaicha.util.ConnectionChangeReceiver;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity {

    ViewPager viewPager;
    RadioButton radioButton1;
    RadioButton radioButton2;
    //    RadioButton radioButton3;
    private ArrayList<Fragment> fragments;
    private MyPageAdapter adapter;
    private BroadcastReceiver mNetworkStateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        TApplication.activities.add(this);
        checkNetWorkState();
        initViews();
        setListener();//设置监听
        setFragment();//初始化fragment
        setAdapter();//设置适配器
        setDefault();//设置默认界面
    }

    private void checkNetWorkState() {
        mNetworkStateReceiver = new ConnectionChangeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkStateReceiver, filter);
    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        radioButton1 = (RadioButton) findViewById(R.id.rb_record);
        radioButton2 = (RadioButton) findViewById(R.id.rb_search);
//        radioButton3 = (RadioButton) findViewById(R.id.rb_post);
    }

    private void setDefault() {
        radioButton1.setChecked(false);
        radioButton2.setChecked(true);
        viewPager.setCurrentItem(1);
    }

    private void setAdapter() {
        adapter = new MyPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    private void setFragment() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new RecordFragment());
        fragments.add(new SearchFragment());
//        fragments.add(new PostFragment());
    }

    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.rb_record:
                viewPager.setCurrentItem(0);
                break;
            case R.id.rb_search:
                viewPager.setCurrentItem(1);
                break;
//            case R.id.rb_post:
//                viewPager.setCurrentItem(2);
//                break;case R.id.rb_post:
//                viewPager.setCurrentItem(2);
//                break;
        }
    }

    private void setListener() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        radioButton1.setChecked(true);
                        break;
                    case 1:
                        radioButton2.setChecked(true);
                        break;
                    case 2:
//                        radioButton3.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class MyPageAdapter extends FragmentPagerAdapter {
        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mNetworkStateReceiver);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (TApplication.activities != null && TApplication.activities.size() > 0) {
            for (Activity activity : TApplication.activities) {
                activity.finish();
            }
        }
        super.onBackPressed();
    }
}
