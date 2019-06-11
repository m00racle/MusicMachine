package com.ideproject.mooracle.musicmachine.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ideproject.mooracle.musicmachine.DetailActivity;
import com.ideproject.mooracle.musicmachine.MainActivity;
import com.ideproject.mooracle.musicmachine.R;
import com.ideproject.mooracle.musicmachine.models.Song;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.SongViewHolder> {
    private Song[] songs;
    private Context context;
    private SongViewHolder viewHolder;

    public PlaylistAdapter(Song[] songs, Context context) {
        this.songs = songs;
        this.context = context;
    }

    @NonNull
    @Override
    public PlaylistAdapter.SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_song, parent, false);
        viewHolder = new SongViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistAdapter.SongViewHolder holder, int position) {
        holder.bindSong(songs[position]);
    }

    @Override
    public int getItemCount() {
        return songs.length;
    }

    public class SongViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{
        public TextView titleLabel;
        public ImageView favIcon;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            titleLabel = itemView.findViewById(R.id.songTitleLabel);
            favIcon = itemView.findViewById(R.id.favIcon);
            itemView.setOnClickListener(this);
        }

        public void bindSong(Song song){
            titleLabel.setText(song.getTitle());
            if (song.isFavorite()){
                favIcon.setVisibility(View.VISIBLE);
            }
            else {
                favIcon.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            // Start an activity
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(MainActivity.EXTRA_SONG, songs[getAdapterPosition()]);
            intent.putExtra(MainActivity.EXTRA_LIST_POSITION, getAdapterPosition());

            ((Activity)context).startActivityForResult(intent, MainActivity.REQUEST_FAVORITE);
        }
    }
}
