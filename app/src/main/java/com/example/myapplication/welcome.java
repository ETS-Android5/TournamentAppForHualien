package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class welcome extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2800);
                    startActivity(new Intent().setClass(welcome.this, home.class));
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                finish();
            }
        }).start();


    }
}