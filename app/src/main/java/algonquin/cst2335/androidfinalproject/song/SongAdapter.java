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


public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private List<Song> songList;
    private SongDao songDao;
    private boolean showDeleteButton = false;
    private boolean showAddButton = true;
    private OnFavoriteClickListener onFavoriteClickListener; // nterface variable

    public void setSongList(List<Song> songList) {
        this.songList = songList;
        notifyDataSetChanged();
    }
    public void setSongDao(SongDao songDao)
    {
        this.songDao = songDao;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_song_details, parent, false);
        return new SongViewHolder(view);
    }

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
                // 获取当前位置的歌曲并插入到数据库中
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Song selectedSong = songList.get(adapterPosition);
                    insertSongToDatabase(selectedSong);
                }
                if (onFavoriteClickListener!= null) {
                    onFavoriteClickListener.onFavoriteClick(song);
                }
            }
        });

        // Set click listener for the delete button
        holder.buttonDeleteFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the position of the song in the list
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    // Get the selected song
                    Song selectedSong = songList.get(adapterPosition);
                    // Call the deleteFavoriteSong method in the activity
                    ((MainSongActivity) v.getContext()).deleteFavoriteSong(selectedSong);
                }
            }
        });

        // 设置删除按钮的可见性
        if (showDeleteButton) {
            holder.buttonDeleteFavorite.setVisibility(View.VISIBLE);
        } else {
            holder.buttonDeleteFavorite.setVisibility(View.GONE);
        }
        // 设置删除按钮的可见性
        if (showAddButton) {
            holder.buttonAddToFavorites.setVisibility(View.VISIBLE);
        } else {
            holder.buttonAddToFavorites.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return songList != null ? songList.size() : 0;
    }

    static class SongViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewAlbum, textViewDuration;
        ImageView imageViewAlbumCover;
        Button buttonAddToFavorites;
        Button buttonDeleteFavorite;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewAlbum = itemView.findViewById(R.id.textViewAlbum);
            textViewDuration = itemView.findViewById(R.id.textViewDuration);
            imageViewAlbumCover = itemView.findViewById(R.id.imageViewAlbumCover);
            buttonAddToFavorites = itemView.findViewById(R.id.buttonAddToFavorites); // Add this line to initialize buttonAddToFavorites
            buttonDeleteFavorite = itemView.findViewById(R.id.buttonDeleteFavorite);
        }
    }
    public interface OnFavoriteClickListener {
        void onFavoriteClick(Song song);
    }
    // Method to set the listener
    public void setOnFavoriteClickListener(OnFavoriteClickListener listener) {
        this.onFavoriteClickListener= listener;
    }

    private void insertSongToDatabase(Song song) {
        // spcifying data insert to database
        if (songDao != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    songDao.insert(song);
                }
            }).start();
        }
    }

    // Add a method to set the list of favorite songs
    public void setFavoriteSongs(List<Song> favoriteSongs) {
        this.songList = favoriteSongs;
        notifyDataSetChanged();
    }

    public void setShowDeleteButton(boolean show) {
        this.showDeleteButton = show;
        notifyDataSetChanged();
    }
    public void setShowAddButton(boolean show) {
        this.showAddButton = show;
        notifyDataSetChanged();
    }
}