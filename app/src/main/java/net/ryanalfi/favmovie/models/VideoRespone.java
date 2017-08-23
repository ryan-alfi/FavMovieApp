package net.ryanalfi.favmovie.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by imac on 8/23/17.
 */

public class VideoRespone {
    @SerializedName("id")
    private int id;
    @SerializedName("results")
    private List<Video> results;

    public VideoRespone(int id, List<Video> results) {
        this.id = id;
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }
}
