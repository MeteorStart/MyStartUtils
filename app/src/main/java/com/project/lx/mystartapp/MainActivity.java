package com.project.lx.mystartapp;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    ViewGroup viewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //构造方法传入ViewPager  ViewGroup  Intent
        AppStartUtils appStartUtils = new AppStartUtils(this,viewPager,viewGroup,new Intent(this,MainActivity.class));
        //设置指示器的图标（0 为选中 1 为为选中）
        appStartUtils.setIconResources(new int[]{R.mipmap.ic_launcher,R.mipmap.ic_launcher_round});
        //设置引导页图片（可以本地也可以传url）
        appStartUtils.setImageResources(new int[]{R.mipmap.ic_launcher_round,R.mipmap.ic_launcher_round,R.mipmap.ic_launcher_round});
//        appStartUtils.setImageResources(new String[]{"url","url","url"});
        appStartUtils.start();

    }
}
