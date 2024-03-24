package algonquin.cst2335.androidfinalproject.song;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.androidfinalproject.R;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private List<Song> songList;

    public void setSongList(List<Song> songList) {
        this.songList = songList;
        notifyDataSetChanged();
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
        holder.textViewAlbum.setText(song.getAlbum());
        holder.textViewDuration.setText(song.getDuration());
    }

    @Override
    public int getItemCount() {
        return songList != null ? songList.size() : 0;
    }

    static class SongViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewAlbum, textViewDuration;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewAlbum = itemView.findViewById(R.id.textViewAlbum);
            textViewDuration = itemView.findViewById(R.id.textViewDuration);
        }
    }
}

