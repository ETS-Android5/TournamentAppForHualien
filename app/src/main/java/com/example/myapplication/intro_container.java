package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class intro_container extends FragmentActivity {

    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    private List<Fragment> fragments=new ArrayList<>();
    private int currentPosition2;
    private LinearLayout linearLayout;
    private int custome_position=1;
    private int old_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_container);
        viewPager = findViewById(R.id.mainpager);
        linearLayout = findViewById(R.id.fragemnt_indicator);

        Intent it = getIntent();
        int number = it.getIntExtra("pos",0);
        String str = it.getStringExtra("kinds");
        if(str.equals("l")){
            /*if(number==0)
                fragments.add(new PageFragment_first(1,this));
            else
                fragments.add(new PageFragment_first(number-1,this));*/
            fragments.add(new PagerFragmentFirst(1,this));
            for(int i=0;i<2;i++) {
                fragments.add(new PagerFragmentFirst(i, this));
                if(i==1)
                    fragments.add(new PagerFragmentFirst(0, this));
            }
        }else if(str.equals("p")){
            fragments.add(new PagerFragmentSecond(9,this));
            for(int i=0;i<10;i++) {
                fragments.add(new PagerFragmentSecond(i, this));
                if(i==9)
                    fragments.add(new PagerFragmentSecond(0, this));
            }
        }else if(str.equals("e")){
            fragments.add(new PagerFragmentThird(7,this));
            for(int i=0;i<8;i++) {
                fragments.add(new PagerFragmentThird(i, this));
                if(i==7)
                    fragments.add(new PagerFragmentThird(0, this));
            }
        }

        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(),getLifecycle(),fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(number+1,false);
        DotsPrepare(number+1);
        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(15));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + 0.15f * r);
            }
        });
        viewPager.setPageTransformer(compositePageTransformer);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPosition2 = position;
                if(position==0)
                    custome_position = fragments.size()-2;
                else if(position == fragments.size()-1)
                    custome_position = 1;
                else
                    custome_position = position;

                DotsPrepare(custome_position);
                /*
                fragments.get(position).getView().findViewById(R.id.BackTo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (currentPosition2 == 0) {
                        viewPager.setCurrentItem(fragments.size() - 2, false);//切換，不要動畫效果
                    } else if (currentPosition2 == fragments.size() - 1) {
                        viewPager.setCurrentItem(1, false);//切換，不要動畫效果
                    }
                }
            }
        });
    }

    private void DotsPrepare(int currentposition){
        if(old_position == currentposition)
            return;
        else {
            if (linearLayout.getChildCount() > 0)
                linearLayout.removeAllViews();
            old_position = currentposition;
            ImageView dots[] = new ImageView[fragments.size() - 2];
            for (int i = 0; i < fragments.size() - 2; i++) {
                dots[i] = new ImageView(this);
                if (i == currentposition - 1) {
                    Log.d("tag", "current::::" + i);
                    dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_dots));
                } else
                    dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.inactive_dots));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(10, 0, 10, 0);
                linearLayout.addView(dots[i], layoutParams);
            }
        }
    }
}