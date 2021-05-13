package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class life extends AppCompatActivity {
private ImageButton btn1,btn2,btn3,btn4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);
        btn1=findViewById(R.id.imageButton5);
        btn2=findViewById(R.id.imageButton6);
        btn3=findViewById(R.id.imageButton7);
        btn4=findViewById(R.id.imageButton8);

        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                eat();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                live();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                play();
            }
        });
        btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }
    public void eat(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void live(){
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
    public void play(){
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }
}