package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.List;

public class transport_bus extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=this.getIntent();
        String url = intent.getStringExtra("weburl");
        setContentView(R.layout.transport_bus);
        ImageButton btn = findViewById(R.id.imageButton8);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        WebView webview = (WebView) findViewById(R.id.web_view);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient()); //不調用系統瀏覽器
        webview.loadUrl(url);
    }

    public void back(){
        Intent intent = new Intent(this, transport.class);
        startActivity(intent);
    }

}
