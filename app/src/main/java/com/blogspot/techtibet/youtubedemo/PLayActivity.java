package com.blogspot.techtibet.youtubedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PLayActivity extends YouTubeBaseActivity {
    private YouTubePlayerView player;
    private static final String TAG="";
    YouTubePlayer.OnInitializedListener mInitializedListener;
    Button mPLayBtn;
    String link="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Intent intent=getIntent();
        link=intent.getStringExtra("link");
        Toast.makeText(this, ""+link, Toast.LENGTH_SHORT).show();

        player=findViewById(R.id.play_now);
        mPLayBtn=findViewById(R.id.playbtn);
        Log.d(TAG, "onCreate: "+link);
        mInitializedListener=new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                youTubePlayer.loadVideo(link);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        mPLayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.initialize(YoutubeConfig.getApiKey(),mInitializedListener);

            }
        });

    }

}
