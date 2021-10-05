package com.app.dht.database;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class Movie {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "movie_title")
    public String movieTitle;

    @ColumnInfo(name = "movie_year")
    public String movieYear;

    @ColumnInfo(name = "movie_type")
    public String movieType;

    @ColumnInfo(name = "movie_poster")
    public String moviePoster;
}
