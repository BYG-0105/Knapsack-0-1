package com.example.knapsack.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knapsack.Bean.Goods;
import com.example.potplayer.Bean.Videomes;
import com.example.knapsack.R;

import java.util.List;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder>{

    private LayoutInflater layoutInflater;
    private List<Goods> list;
    private MediaController controller;
    private Context main;

    public GoodsAdapter(Context context, List<Goods> list)
    {
        this.layoutInflater = LayoutInflater.from(context);
        this.list=list;
        this.main= context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= layoutInflater.inflate(R.layout.select_item_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Goods goods = list.get(position);
        viewHolder.song.setText(videomes.getSongname());
        viewHolder.singer.setText(videomes.getSinger());
        String url = "android.resource://" + main.getPackageName() + "/" +videomes.getPath();
        //字符串解析成Uri
        Uri uri = Uri.parse(url);
        viewHolder.video.setVideoURI(uri);
        controller = new MediaController(main);
        viewHolder.video.setMediaController(controller);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView singer,song;
        ImageView video;
        public ViewHolder(@NonNull View view)
        {
            super(view);
            singer = (TextView) view.findViewById(R.id.tvSinger);
            song = (TextView) view.findViewById(R.id.tvSong);
            video = (VideoView) view.findViewById(R.id.video);
        }
    }
}

