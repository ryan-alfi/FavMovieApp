package net.ryanalfi.favmovie.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by imac on 8/22/17.
 */

public class Movie {
    @SerializedName("title")
    private String movie_title;
    @SerializedName("poster_path")
    private String movie_poster;
    @SerializedName("id")
    private int movie_id;

    public Movie(String movie_title, String movie_poster, int movie_id) {
        this.movie_title = movie_title;
        this.movie_poster = movie_poster;
        this.movie_id = movie_id;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getMovie_poster() {
        return "http://image.tmdb.org/t/p/w185/" + movie_poster;
    }

    public void setMovie_poster(String movie_poster) {
        this.movie_poster = movie_poster;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }
}
