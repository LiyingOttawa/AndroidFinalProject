/*
 * Filename: SongAdapter.java
 * Author: Zhaoguo Han
 * Lab Section: CST2355 011
 * Creation Date: March 31, 2024
 * Purpose: This class is an adapter for the RecyclerView in the MainSongActivity. It is responsible for displaying song data and handling user interactions with the song items.
 */
package algonquin.cst2335.androidfinalproject.song;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import algonquin.cst2335.androidfinalproject.R;

/**
 * Adapter class for RecyclerView to display songs.
 */
public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private List<Song> songList;
    private SongDao songDao;
    private boolean showDeleteButton = false;
    private boolean showAddButton = true;
    private OnFavoriteClickListener onFavoriteClickListener;

    /**
     * Setter method to set the list of songs.
     * @param songList The list of songs to be displayed.
     */
    public void setSongList(List<Song> songList) {
        this.songList = songList;
        notifyDataSetChanged();
    }

    /**
     * Setter method to set the DAO for accessing the database.
     * @param songDao The SongDao object.
     */
    public void setSongDao(SongDao songDao) {
        this.songDao = songDao;
    }

    /**
     * Create a SongViewHolder instance.
     */
    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_song_details, parent, false);
        return new SongViewHolder(view);
    }

    /**
     * Bind data to the ViewHolder.
     */
    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songList.get(position);
        holder.textViewTitle.setText(song.getTitle());
        holder.textViewAlbum.setText(song.getName());
        holder.textViewDuration.setText(String.valueOf(song.getDuration())); // Convert int to String
        Picasso.get().load(song.getCover()).into(holder.imageViewAlbumCover);

        holder.buttonAddToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Song selectedSong = songList.get(adapterPosition);
                    insertSongToDatabase(selectedSong);
                }
                if (onFavoriteClickListener != null) {
                    onFavoriteClickListener.onFavoriteClick(song);
                }
            }
        });

        holder.buttonDeleteFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Song selectedSong = songList.get(adapterPosition);
                    ((MainSongActivity) v.getContext()).deleteFavoriteSong(selectedSong);
                }
            }
        });

        if (showDeleteButton) {
            holder.buttonDeleteFavorite.setVisibility(View.VISIBLE);
        } else {
            holder.buttonDeleteFavorite.setVisibility(View.GONE);
        }

        if (showAddButton) {
            holder.buttonAddToFavorites.setVisibility(View.VISIBLE);
        } else {
            holder.buttonAddToFavorites.setVisibility(View.GONE);
        }
    }

    /**
     * Get the number of items in the RecyclerView.
     */
    @Override
    public int getItemCount() {
        return songList != null ? songList.size() : 0;
    }

    /**
     * ViewHolder class to hold the views for each item in the RecyclerView.
     */
    static class SongViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewAlbum, textViewDuration;
        ImageView imageViewAlbumCover;
        Button buttonAddToFavorites, buttonDeleteFavorite;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewAlbum = itemView.findViewById(R.id.textViewAlbum);
            textViewDuration = itemView.findViewById(R.id.textViewDuration);
            imageViewAlbumCover = itemView.findViewById(R.id.imageViewAlbumCover);
            buttonAddToFavorites = itemView.findViewById(R.id.buttonAddToFavorites);
            buttonDeleteFavorite = itemView.findViewById(R.id.buttonDeleteFavorite);
        }
    }

    /**
     * Interface definition for a callback to be invoked when a song is added to favorites.
     */
    public interface OnFavoriteClickListener {
        void onFavoriteClick(Song song);
    }

    /**
     * Method to set the listener for favorite button clicks.
     * @param listener The listener to be set.
     */
    public void setOnFavoriteClickListener(OnFavoriteClickListener listener) {
        this.onFavoriteClickListener = listener;
    }

    private void insertSongToDatabase(Song song) {
        if (songDao != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    songDao.insert(song);
                }
            }).start();
        }
    }

    /**
     * Method to set the list of favorite songs.
     * @param favoriteSongs The list of favorite songs.
     */
    public void setFavoriteSongs(List<Song> favoriteSongs) {
        this.songList = favoriteSongs;
        notifyDataSetChanged();
    }

    /**
     * Method to show or hide the delete button.
     * @param show true to show the delete button, false to hide.
     */
    public void setShowDeleteButton(boolean show) {
        this.showDeleteButton = show;
        notifyDataSetChanged();
    }

    /**
     * Method to show or hide the add button.
     * @param show true to show the add button, false to hide.
     */
    public void setShowAddButton(boolean show) {
        this.showAddButton = show;
        notifyDataSetChanged();
    }
}