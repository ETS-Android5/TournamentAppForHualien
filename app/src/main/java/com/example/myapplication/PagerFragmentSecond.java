package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class PagerFragmentSecond extends Fragment{
    private int position;
    private TextView title,content,address,phone,time,website;
    private String[] show;
    private List<SliderItem> sliderItems = new ArrayList<>();
    private ViewPager2 viewPager2;
    private Context context;
    private int currentPosition2;
    private LinearLayout Main;
    private LinearLayout dot_container;
    private int custome_position=1;
    private int old_position;
    private ImageButton imageButton;
    private ImageView imageView;

    public PagerFragmentSecond(int pos, Context context){
        this.position = pos;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_introduction, container, false);
        Log.d("tag",position + " onCreateView");

        title = root.findViewById(R.id.topic);
        content = root.findViewById(R.id.description);
        address = root.findViewById(R.id.address);
        phone = root.findViewById(R.id.phone);
        time = root.findViewById(R.id.time);
        website = root.findViewById(R.id.website);
        viewPager2 = root.findViewById(R.id.viewPager2);
        Main = root.findViewById(R.id.main);
        dot_container = root.findViewById(R.id.indicator_view);
        imageButton = root.findViewById(R.id.BackTo);
        imageView = root.findViewById(R.id.imageView8);

        switch (position){
            case 0:
                show = getResources().getStringArray(R.array.play_intro_1);
                sliderItems.add(new SliderItem(R.drawable.play1_05));
                sliderItems.add(new SliderItem(R.drawable.play1_01));
                sliderItems.add(new SliderItem(R.drawable.play1_02));
                sliderItems.add(new SliderItem(R.drawable.play1_03));
                sliderItems.add(new SliderItem(R.drawable.play1_04));
                sliderItems.add(new SliderItem(R.drawable.play1_05));
                sliderItems.add(new SliderItem(R.drawable.play1_01));
                break;
            case 1:
                show = getResources().getStringArray(R.array.play_intro_2);
                sliderItems.add(new SliderItem(R.drawable.play2_05));
                sliderItems.add(new SliderItem(R.drawable.play2_01));
                sliderItems.add(new SliderItem(R.drawable.play2_02));
                sliderItems.add(new SliderItem(R.drawable.play2_03));
                sliderItems.add(new SliderItem(R.drawable.play2_04));
                sliderItems.add(new SliderItem(R.drawable.play2_05));
                sliderItems.add(new SliderItem(R.drawable.play2_01));
                break;
            case 2:
                show = getResources().getStringArray(R.array.play_intro_3);
                sliderItems.add(new SliderItem(R.drawable.play3_04));
                sliderItems.add(new SliderItem(R.drawable.play3_01));
                sliderItems.add(new SliderItem(R.drawable.play3_02));
                sliderItems.add(new SliderItem(R.drawable.play3_03));
                sliderItems.add(new SliderItem(R.drawable.play3_04));
                sliderItems.add(new SliderItem(R.drawable.play3_01));
                break;
            case 3:
                show = getResources().getStringArray(R.array.play_intro_4);
                sliderItems.add(new SliderItem(R.drawable.play4_05));
                sliderItems.add(new SliderItem(R.drawable.play4_01));
                sliderItems.add(new SliderItem(R.drawable.play4_02));
                sliderItems.add(new SliderItem(R.drawable.play4_03));
                sliderItems.add(new SliderItem(R.drawable.play4_04));
                sliderItems.add(new SliderItem(R.drawable.play4_05));
                sliderItems.add(new SliderItem(R.drawable.play4_01));
                break;
            case 4:
                show = getResources().getStringArray(R.array.play_intro_5);
                sliderItems.add(new SliderItem(R.drawable.play5_05));
                sliderItems.add(new SliderItem(R.drawable.play5_01));
                sliderItems.add(new SliderItem(R.drawable.play5_02));
                sliderItems.add(new SliderItem(R.drawable.play5_03));
                sliderItems.add(new SliderItem(R.drawable.play5_04));
                sliderItems.add(new SliderItem(R.drawable.play5_05));
                sliderItems.add(new SliderItem(R.drawable.play5_01));
                break;
            case 5:
                show = getResources().getStringArray(R.array.play_intro_6);
                sliderItems.add(new SliderItem(R.drawable.play6_05));
                sliderItems.add(new SliderItem(R.drawable.play6_01));
                sliderItems.add(new SliderItem(R.drawable.play6_02));
                sliderItems.add(new SliderItem(R.drawable.play6_03));
                sliderItems.add(new SliderItem(R.drawable.play6_04));
                sliderItems.add(new SliderItem(R.drawable.play6_05));
                sliderItems.add(new SliderItem(R.drawable.play6_01));
                break;
            case 6:
                show = getResources().getStringArray(R.array.play_intro_7);
                sliderItems.add(new SliderItem(R.drawable.play7_05));
                sliderItems.add(new SliderItem(R.drawable.play7_01));
                sliderItems.add(new SliderItem(R.drawable.play7_02));
                sliderItems.add(new SliderItem(R.drawable.play7_03));
                sliderItems.add(new SliderItem(R.drawable.play7_04));
                sliderItems.add(new SliderItem(R.drawable.play7_01));
                break;
            case 7:
                show = getResources().getStringArray(R.array.play_intro_8);
                sliderItems.add(new SliderItem(R.drawable.play8_04));
                sliderItems.add(new SliderItem(R.drawable.play8_01));
                sliderItems.add(new SliderItem(R.drawable.play8_02));
                sliderItems.add(new SliderItem(R.drawable.play8_03));
                sliderItems.add(new SliderItem(R.drawable.play8_04));
                sliderItems.add(new SliderItem(R.drawable.play8_01));
                break;
            case 8:
                show = getResources().getStringArray(R.array.play_intro_9);
                sliderItems.add(new SliderItem(R.drawable.play9_05));
                sliderItems.add(new SliderItem(R.drawable.play9_01));
                sliderItems.add(new SliderItem(R.drawable.play9_02));
                sliderItems.add(new SliderItem(R.drawable.play9_03));
                sliderItems.add(new SliderItem(R.drawable.play9_04));
                sliderItems.add(new SliderItem(R.drawable.play9_05));
                sliderItems.add(new SliderItem(R.drawable.play9_01));
                break;
            case 9:
                show = getResources().getStringArray(R.array.play_intro_10);
                sliderItems.add(new SliderItem(R.drawable.play10_05));
                sliderItems.add(new SliderItem(R.drawable.play10_01));
                sliderItems.add(new SliderItem(R.drawable.play10_02));
                sliderItems.add(new SliderItem(R.drawable.play10_03));
                sliderItems.add(new SliderItem(R.drawable.play10_04));
                sliderItems.add(new SliderItem(R.drawable.play10_05));
                sliderItems.add(new SliderItem(R.drawable.play10_01));
                break;
        }
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        title.setText(show[0]);
        content.setText(show[1]);
        address.setText(show[2]);
        time.setText(show[3]);
        phone.setText(show[4]);
        website.setText(show[5]);
        Main.setBackgroundResource(R.drawable.bg_5);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri uri = Uri.parse(String.format("tel:%1$s",show[4]));
                intent.setData(uri);
                startActivity(intent);
            }
        });

        viewPager2.setAdapter(new ViewpagerAdapter(sliderItems,context));
        viewPager2.setCurrentItem(1,false);
        DotsPrepare(custome_position);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPosition2 = position;
                if(position==0)
                    custome_position = sliderItems.size()-2;
                else if(position == sliderItems.size()-1)
                    custome_position = 1;
                else
                    custome_position = position;

                Log.d("tag","custome_position:" + custome_position);
                DotsPrepare(custome_position);
                /*if(custome_position>sliderItems.size())
                    custome_position = 0;
                DotsPrepare(custome_position++);*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (currentPosition2 == 0) {
                        viewPager2.setCurrentItem(sliderItems.size() - 2, false);//切換，不要動畫效果
                    } else if (currentPosition2 == sliderItems.size() - 1) {
                        viewPager2.setCurrentItem(1, false);//切換，不要動畫效果
                    }
                }
            }
        });
    }
    private void DotsPrepare(int currentposition){
        if(old_position == currentposition)
            return;
        else {
            if (dot_container.getChildCount() > 0)
                dot_container.removeAllViews();
            old_position = currentposition;
            ImageView dots[] = new ImageView[sliderItems.size() - 2];
            for (int i = 0; i < sliderItems.size() - 2; i++) {
                dots[i] = new ImageView(context);
                if (i == currentposition - 1) {
                    Log.d("tag", "current::::" + i);
                    dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dots));
                } else
                    dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.inactive_dots));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(10, 0, 10, 0);
                dot_container.addView(dots[i], layoutParams);
            }
        }
    }
}
