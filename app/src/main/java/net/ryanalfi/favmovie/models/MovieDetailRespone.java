package net.ryanalfi.favmovie.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by imac on 8/23/17.
 */

public class MovieDetailRespone {
    @SerializedName("release_date")
    private String year;
    @SerializedName("title")
    private String title;
    @SerializedName("poster_path")
    private String posterUrl;
    @SerializedName("runtime")
    private int durasi;
    @SerializedName("vote_average")
    private double vote;
    @SerializedName("id")
    private int id;
    @SerializedName("overview")
    private String sinopsis;

    public MovieDetailRespone(String year, String title, String posterUrl, int durasi, double vote, int id, String sinopsis) {
        this.year = year;
        this.title = title;
        this.posterUrl = posterUrl;
        this.durasi = durasi;
        this.vote = vote;
        this.id = id;
        this.sinopsis = sinopsis;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public int getDurasi() {
        return durasi;
    }

    public void setDurasi(int durasi) {
        this.durasi = durasi;
    }

    public double getVote() {
        return vote;
    }

    public void setVote(double vote) {
        this.vote = vote;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }
}
