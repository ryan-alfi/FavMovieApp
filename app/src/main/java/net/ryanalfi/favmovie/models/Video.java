package net.ryanalfi.favmovie.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by imac on 8/23/17.
 */

public class Video {
    @SerializedName("key")
    private String vid_key;
    @SerializedName("name")
    private String vid_name;

    public Video(String vid_key, String vid_name) {
        this.vid_key = vid_key;
        this.vid_name = vid_name;
    }

    public String getVid_key() {
        return vid_key;
    }

    public void setVid_key(String vid_key) {
        this.vid_key = vid_key;
    }

    public String getVid_name() {
        return vid_name;
    }

    public void setVid_name(String vid_name) {
        this.vid_name = vid_name;
    }
}
