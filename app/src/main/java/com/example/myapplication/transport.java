package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class transport extends AppCompatActivity {
    String url302="https://www.taiwanbus.tw/eBUSPage/Query/QueryResult.aspx?rno=03020&rn=1610357653078",
    url310="https://www.taiwanbus.tw/eBUSPage/Query/QueryResult.aspx?rno=03000&rn=1620376017945",
    url1133="https://www.taiwanbus.tw/eBUSPage/Query/QueryResult.aspx?rno=11330&rn=1600844608874";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageButton btn1,btn2,btn3,btn;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);
        btn = findViewById(R.id.imageButton8);
        btn1 = findViewById(R.id.imageButton19);
        btn2 = findViewById(R.id.imageButton20);
        btn3 = findViewById(R.id.imageButton21);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bus1();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bus2();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bus3();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn();
            }
        });
    }

    public void bus1(){
        Intent intent = new Intent(this, transport_bus.class);
        intent.putExtra("weburl", url302);
        startActivity(intent);
    }
    public void bus2(){
        Intent intent = new Intent(this, transport_bus.class);
        intent.putExtra("weburl", url310);
        startActivity(intent);
    }
    public void bus3(){
        Intent intent = new Intent(this, transport_bus.class);
        intent.putExtra("weburl", url1133);
        startActivity(intent);
    }
    public void btn(){
        Intent intent = new Intent(this, home.class);
        startActivity(intent);
    }

}