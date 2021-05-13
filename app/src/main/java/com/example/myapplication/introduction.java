package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.net.Uri;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class introduction extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private TextView title,content,address,phone,time,website;
    private String [] show;
    private LinearLayout Main;
    private ImageButton back,locate;
    public int number ;
    public String str;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        Main = findViewById(R.id.main);
        title = findViewById(R.id.topic);
        content = findViewById(R.id.description);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        time = findViewById(R.id.time);
        website = findViewById(R.id.website);
        locate=findViewById(R.id.imageView6);
        viewPager2 = findViewById(R.id.viewPager2);
        back = findViewById(R.id.BackTo);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        List<SliderItem> sliderItems = new ArrayList<>();
        Intent it = getIntent();
        number = it.getIntExtra("pos",0);
        str = it.getStringExtra("kinds");
        if(str.equals("live")){
            switch (number){
                case 0:
                    show = getResources().getStringArray(R.array.live_intro_1);
                    sliderItems.add(new SliderItem(R.drawable.live1_01));
                    sliderItems.add(new SliderItem(R.drawable.live1_02));
                    sliderItems.add(new SliderItem(R.drawable.live1_03));
                    sliderItems.add(new SliderItem(R.drawable.live1_04));
                    sliderItems.add(new SliderItem(R.drawable.live1_05));
                    break;
                case 1:
                    show = getResources().getStringArray(R.array.live_intro_2);
                    sliderItems.add(new SliderItem(R.drawable.live2_01));
                    sliderItems.add(new SliderItem(R.drawable.live2_02));
                    sliderItems.add(new SliderItem(R.drawable.live2_03));
                    sliderItems.add(new SliderItem(R.drawable.live2_04));
                    sliderItems.add(new SliderItem(R.drawable.live2_05));
                    break;
            }
            Main.setBackgroundResource(R.drawable.bg_4);
        }
        else if(str.equals("play")){
            switch (number){
                case 0:
                    show = getResources().getStringArray(R.array.play_intro_1);
                    sliderItems.add(new SliderItem(R.drawable.play1_01));
                    sliderItems.add(new SliderItem(R.drawable.play1_02));
                    sliderItems.add(new SliderItem(R.drawable.play1_03));
                    sliderItems.add(new SliderItem(R.drawable.play1_04));
                    sliderItems.add(new SliderItem(R.drawable.play1_05));
                    break;
                case 1:
                    show = getResources().getStringArray(R.array.play_intro_2);
                    sliderItems.add(new SliderItem(R.drawable.play2_01));
                    sliderItems.add(new SliderItem(R.drawable.play2_02));
                    sliderItems.add(new SliderItem(R.drawable.play2_03));
                    sliderItems.add(new SliderItem(R.drawable.play2_04));
                    sliderItems.add(new SliderItem(R.drawable.play2_05));
                    break;
                case 2:
                    show = getResources().getStringArray(R.array.play_intro_3);
                    sliderItems.add(new SliderItem(R.drawable.play3_01));
                    sliderItems.add(new SliderItem(R.drawable.play3_02));
                    sliderItems.add(new SliderItem(R.drawable.play3_03));
                    sliderItems.add(new SliderItem(R.drawable.play3_04));
                    break;
                case 3:
                    show = getResources().getStringArray(R.array.play_intro_4);
                    sliderItems.add(new SliderItem(R.drawable.play4_01));
                    sliderItems.add(new SliderItem(R.drawable.play4_02));
                    sliderItems.add(new SliderItem(R.drawable.play4_03));
                    sliderItems.add(new SliderItem(R.drawable.play4_04));
                    sliderItems.add(new SliderItem(R.drawable.play4_05));
                    break;
                case 4:
                    show = getResources().getStringArray(R.array.play_intro_5);
                    sliderItems.add(new SliderItem(R.drawable.play5_01));
                    sliderItems.add(new SliderItem(R.drawable.play5_02));
                    sliderItems.add(new SliderItem(R.drawable.play5_03));
                    sliderItems.add(new SliderItem(R.drawable.play5_04));
                    sliderItems.add(new SliderItem(R.drawable.play5_05));
                    break;
                case 5:
                    show = getResources().getStringArray(R.array.play_intro_6);
                    sliderItems.add(new SliderItem(R.drawable.play6_01));
                    sliderItems.add(new SliderItem(R.drawable.play6_02));
                    sliderItems.add(new SliderItem(R.drawable.play6_03));
                    sliderItems.add(new SliderItem(R.drawable.play6_04));
                    sliderItems.add(new SliderItem(R.drawable.play6_05));
                    break;
                case 6:
                    show = getResources().getStringArray(R.array.play_intro_7);
                    sliderItems.add(new SliderItem(R.drawable.play7_01));
                    sliderItems.add(new SliderItem(R.drawable.play7_02));
                    sliderItems.add(new SliderItem(R.drawable.play7_03));
                    sliderItems.add(new SliderItem(R.drawable.play7_04));
                    sliderItems.add(new SliderItem(R.drawable.play7_05));
                    break;
                case 7:
                    show = getResources().getStringArray(R.array.play_intro_8);
                    sliderItems.add(new SliderItem(R.drawable.play8_01));
                    sliderItems.add(new SliderItem(R.drawable.play8_02));
                    sliderItems.add(new SliderItem(R.drawable.play8_03));
                    sliderItems.add(new SliderItem(R.drawable.play8_04));
                    break;
                case 8:
                    show = getResources().getStringArray(R.array.play_intro_9);
                    sliderItems.add(new SliderItem(R.drawable.play9_01));
                    sliderItems.add(new SliderItem(R.drawable.play9_02));
                    sliderItems.add(new SliderItem(R.drawable.play9_03));
                    sliderItems.add(new SliderItem(R.drawable.play9_04));
                    sliderItems.add(new SliderItem(R.drawable.play9_05));
                    break;
                case 9:
                    show = getResources().getStringArray(R.array.play_intro_10);
                    sliderItems.add(new SliderItem(R.drawable.play10_01));
                    sliderItems.add(new SliderItem(R.drawable.play10_02));
                    sliderItems.add(new SliderItem(R.drawable.play10_03));
                    sliderItems.add(new SliderItem(R.drawable.play10_04));
                    sliderItems.add(new SliderItem(R.drawable.play10_05));
                    break;
            }
            Main.setBackgroundResource(R.drawable.bg_5);
        }
        else if(str.equals("eat")){
            switch (number){
                case 0:
                    show = getResources().getStringArray(R.array.eat_intro_1);
                    sliderItems.add(new SliderItem(R.drawable.eat1_01));
                    sliderItems.add(new SliderItem(R.drawable.eat1_02));
                    sliderItems.add(new SliderItem(R.drawable.eat1_03));
                    sliderItems.add(new SliderItem(R.drawable.eat1_04));
                    sliderItems.add(new SliderItem(R.drawable.eat1_05));
                    break;
                case 1:
                    show = getResources().getStringArray(R.array.eat_intro_2);
                    sliderItems.add(new SliderItem(R.drawable.eat2_01));
                    sliderItems.add(new SliderItem(R.drawable.eat2_02));
                    sliderItems.add(new SliderItem(R.drawable.eat2_03));
                    sliderItems.add(new SliderItem(R.drawable.eat2_04));
                    sliderItems.add(new SliderItem(R.drawable.eat2_05));
                    break;
                case 2:
                    show = getResources().getStringArray(R.array.eat_intro_3);
                    sliderItems.add(new SliderItem(R.drawable.eat3_01));
                    sliderItems.add(new SliderItem(R.drawable.eat3_02));
                    sliderItems.add(new SliderItem(R.drawable.eat3_03));
                    sliderItems.add(new SliderItem(R.drawable.eat3_04));
                    sliderItems.add(new SliderItem(R.drawable.eat3_05));
                    break;
                case 3:
                    show = getResources().getStringArray(R.array.eat_intro_4);
                    sliderItems.add(new SliderItem(R.drawable.eat4_01));
                    sliderItems.add(new SliderItem(R.drawable.eat4_02));
                    sliderItems.add(new SliderItem(R.drawable.eat4_03));
                    sliderItems.add(new SliderItem(R.drawable.eat4_04));
                    sliderItems.add(new SliderItem(R.drawable.eat4_05));
                    break;
                case 4:
                    show = getResources().getStringArray(R.array.eat_intro_5);
                    sliderItems.add(new SliderItem(R.drawable.eat5_01));
                    sliderItems.add(new SliderItem(R.drawable.eat5_02));
                    sliderItems.add(new SliderItem(R.drawable.eat5_03));
                    sliderItems.add(new SliderItem(R.drawable.eat5_04));
                    sliderItems.add(new SliderItem(R.drawable.eat5_05));
                    break;
                case 5:
                    show = getResources().getStringArray(R.array.eat_intro_6);
                    sliderItems.add(new SliderItem(R.drawable.eat6_01));
                    sliderItems.add(new SliderItem(R.drawable.eat6_02));
                    sliderItems.add(new SliderItem(R.drawable.eat6_03));
                    sliderItems.add(new SliderItem(R.drawable.eat6_04));
                    sliderItems.add(new SliderItem(R.drawable.eat6_05));
                    break;
                case 6:
                    show = getResources().getStringArray(R.array.eat_intro_7);
                    sliderItems.add(new SliderItem(R.drawable.eat7_01));
                    sliderItems.add(new SliderItem(R.drawable.eat7_02));
                    sliderItems.add(new SliderItem(R.drawable.eat7_03));
                    sliderItems.add(new SliderItem(R.drawable.eat7_04));
                    sliderItems.add(new SliderItem(R.drawable.eat7_05));
                    break;
                case 7:
                    show = getResources().getStringArray(R.array.eat_intro_8);
                    sliderItems.add(new SliderItem(R.drawable.eat8_01));
                    sliderItems.add(new SliderItem(R.drawable.eat8_02));
                    sliderItems.add(new SliderItem(R.drawable.eat8_03));
                    sliderItems.add(new SliderItem(R.drawable.eat8_04));
                    sliderItems.add(new SliderItem(R.drawable.eat8_05));
                    break;
            }
            Main.setBackgroundResource(R.drawable.bg_6);
        }

        title.setText(show[0]);
        content.setText(show[1]);
        address.setText(show[2]);
        time.setText(show[3]);
        phone.setText(show[4]);
        website.setText(show[5]);

        imageView = findViewById(R.id.imageView8);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri uri = Uri.parse(String.format("tel:%1$s",show[4]));
                intent.setData(uri);
                startActivity(intent);
            }
        });

        viewPager2.setAdapter(new ViewpagerAdapter(sliderItems,introduction.this));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        //compositePageTransformer.addTransformer(new MarginPageTransformer(10));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + 0.15f * r);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);

    }


    public void onBackPressed() {
        finish();
    }
}