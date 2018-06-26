package com.blogspot.techtibet.youtubedemo;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements YouTubeThumbnailView.OnInitializedListener, YouTubeThumbnailLoader.OnThumbnailLoadedListener, YouTubePlayer.OnInitializedListener {

    YouTubePlayer Player;
    YouTubeThumbnailView thumbnailView;
    YouTubeThumbnailLoader thumbnailLoader;
   // YouTubePlayerView playerView;

    RecyclerView VideoList;
    RecyclerView.Adapter adapter;
    List<Drawable> thumbnailViews;
    List<String> VideoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     //   playerView=findViewById(R.id.mainplayer);
        VideoList = (RecyclerView) findViewById(R.id.VideoList);
        thumbnailViews = new ArrayList<>();
        adapter=new VideoListAdapter();

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        VideoList.setLayoutManager(layoutManager);
        VideoList.setAdapter(adapter);
        VideoId = new ArrayList<>();
        thumbnailView = new YouTubeThumbnailView(this);
        thumbnailView.initialize(YoutubeConfig.getApiKey(), this);
 //       playerView.initialize(YoutubeConfig.getApiKey(),this);
//        playerFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.VideoFragment);
//        playerFragment.initialize(YoutubeConfig.getApiKey(), this);
    }


    @Override
    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
        thumbnailLoader = youTubeThumbnailLoader;
        youTubeThumbnailLoader.setOnThumbnailLoadedListener(MainActivity.this);
        thumbnailLoader.setPlaylist("PLsyeobzWxl7oGCz4k9VyxhfmQpSU1dV9b");
    }

    @Override
    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

    }


    public void add() {
        adapter.notifyDataSetChanged();
        if (thumbnailLoader.hasNext())
            thumbnailLoader.next();
    }

    @Override
    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {

        thumbnailViews.add(youTubeThumbnailView.getDrawable());
        VideoId.add(s);
        add();
    }

    @Override
    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        Player=youTubePlayer;
        Player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
            @Override
            public void onFullscreen(boolean b) {
                VideoList.setVisibility(b?View.GONE:View.VISIBLE);
            }
        });
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.MyView>{

        public class MyView extends RecyclerView.ViewHolder{

            ImageView imageView;
            TextView mTitle;
            public MyView(View itemView) {
                super(itemView);
                imageView= (ImageView) itemView.findViewById(R.id.thumbnailView);
                mTitle=itemView.findViewById(R.id.video_title);
            }

        }

        @Override
        public VideoListAdapter.MyView onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_row, parent, false);
            return new MyView(itemView);
        }

        @Override
        public void onBindViewHolder(VideoListAdapter.MyView holder, final int position) {
            holder.imageView.setImageDrawable(thumbnailViews.get(position));
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   String link=VideoId.get(position);
                    Intent intent=new Intent(MainActivity.this,PLayActivity.class);
                    intent.putExtra("link",link);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return thumbnailViews.size();
        }
    }
}

