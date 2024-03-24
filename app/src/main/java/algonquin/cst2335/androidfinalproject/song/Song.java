package algonquin.cst2335.androidfinalproject.song;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public  class Song {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name ="title")
    private String title;

    @ColumnInfo(name ="album")
    private String album;

    @ColumnInfo(name ="duration")
    private String duration;

    public Song(String title, String album, String duration) {
        this.title = title;
        this.album = album;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
