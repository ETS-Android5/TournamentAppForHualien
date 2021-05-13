package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class video1 extends AppCompatActivity {
    private ImageButton btn,btn1,btn2,btn3;
    private YouTubePlayerView youTubePlayerView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video1);
        btn=(ImageButton)findViewById(R.id.imageButton17);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        youTubePlayerView1=(YouTubePlayerView)findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView1);
        youTubePlayerView1.setEnableAutomaticInitialization(false);
        btn1=(ImageButton) findViewById(R.id.bt1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youTubePlayerView1.getYouTubePlayerWhenReady(new YouTubePlayerCallback() {
                    @Override
                    public void onYouTubePlayer(YouTubePlayer youTubePlayer) {
                        youTubePlayer.cueVideo("00wLSEAhNx8", 0);
                    }
                });
            }
        });
        btn2=(ImageButton)findViewById(R.id.bt2);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                youTubePlayerView1.getYouTubePlayerWhenReady(new YouTubePlayerCallback() {
                                                                @Override
                                                                public void onYouTubePlayer(YouTubePlayer youTubPlayer) {
                                                                    youTubPlayer.cueVideo("xTNBGXD65AA", 0);
                                                                }
                                                            }

                );

            }
        });
        btn3=(ImageButton)findViewById(R.id.bt3);
        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                youTubePlayerView1.getYouTubePlayerWhenReady(new YouTubePlayerCallback() {
                                                                @Override
                                                                public void onYouTubePlayer(YouTubePlayer youTubPlayer) {
                                                                    youTubPlayer.cueVideo("DUIMq3vxH9M", 0);
                                                                }
                                                            }

                );

            }
        });
    }
}

