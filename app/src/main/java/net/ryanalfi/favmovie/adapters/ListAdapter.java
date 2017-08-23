package net.ryanalfi.favmovie.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.ryanalfi.favmovie.R;
import net.ryanalfi.favmovie.models.Video;

import java.util.List;

/**
 * Created by imac on 8/23/17.
 */

public class ListAdapter extends BaseAdapter {
    List<Video> mVideos;
    Context context;

    public ListAdapter(List<Video> videos, Context context) {
        this.mVideos = videos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mVideos.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewRow = layoutInflater.inflate(R.layout.item_movie_video, null);

        TextView mTitle = (TextView) viewRow.findViewById(R.id.tv_name_video);
        mTitle.setText(mVideos.get(i).getVid_name());
        Log.d("VUE: ", String.valueOf(mVideos.size()));
        return viewRow;
    }
}
