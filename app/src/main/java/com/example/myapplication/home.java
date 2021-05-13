package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class home extends AppCompatActivity {
    private ImageButton btn1, btn2, btn3, btn4, btnOrien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btn1=(ImageButton)findViewById(R.id.imageButton);
        btn2=(ImageButton)findViewById(R.id.imageButton2);
        btn3=(ImageButton)findViewById(R.id.imageButton3);
        btn4=(ImageButton)findViewById(R.id.imageButton4);
        btnOrien=(ImageButton)findViewById(R.id.imageButton_Orienteering);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                map();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                list();
            }
        });
        btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                createl();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                photo();
            }
        });
        btnOrien.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                homeOrienteering();
            }
        });
    }
    public void list(){
        Intent intent = new Intent(this, life.class);
        startActivity(intent);
    }
    public void map(){
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("pos",2);
        intent.putExtra("kinds","live");
        startActivity(intent);
    }
    public void createl(){
        Intent intent = new Intent(this, create.class);
        startActivity(intent);
    }
    public void photo(){
        Intent intent = new Intent(this, phto.class);
        startActivity(intent);
    }
    public void homeOrienteering(){
        Intent intent = new Intent(this, orienteering.class);
        startActivity(intent);
    }
}