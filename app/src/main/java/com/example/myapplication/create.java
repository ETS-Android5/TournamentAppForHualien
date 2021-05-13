package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class create extends AppCompatActivity {
    private ImageButton btn1, btn2, btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        btn1=(ImageButton)findViewById(R.id.imageButton13);
        btn2=(ImageButton)findViewById(R.id.imageButton14);
        btn3=(ImageButton)findViewById(R.id.imageButton15);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                sv1();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                sv2();
            }
        });
    }
    public void sv1(){
        Intent intent = new Intent(this, video1.class);
        startActivity(intent);
    }
    public void sv2(){
        Intent intent = new Intent(this, video2.class);
        startActivity(intent);
    }
}