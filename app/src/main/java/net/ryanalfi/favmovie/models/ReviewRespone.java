package net.ryanalfi.favmovie.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by imac on 9/4/17.
 */

public class ReviewRespone {
    @SerializedName("id")
    private int id;
    @SerializedName("results")
    private List<Review> results;

    public ReviewRespone(int id, List<Review> results) {
        this.id = id;
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Review> getResults() {
        return results;
    }

    public void setResults(List<Review> results) {
        this.results = results;
    }
}
