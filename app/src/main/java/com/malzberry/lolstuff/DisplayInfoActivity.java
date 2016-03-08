package com.malzberry.lolstuff;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

// TODO: add Champion skills page, scrape from lolking OR safer: get from riot servers yourself
public class DisplayInfoActivity extends FragmentActivity/* implements AsyncResponseTips*/{
    static final int NUMBER_OF_LISTS = 3;

    MyFragmentAdapter fAdapter;
    ViewPager vPager;
    String player, champ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.display_info_activity);

        player = getIntent().getStringExtra("player");
        champ = getIntent().getStringExtra("champion");

        fAdapter = new MyFragmentAdapter(getSupportFragmentManager(), player, champ);
        vPager = (android.support.v4.view.ViewPager) findViewById(R.id.pager);
        vPager.setOffscreenPageLimit(2);
        vPager.setAdapter(fAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
