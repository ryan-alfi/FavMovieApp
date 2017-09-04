package net.ryanalfi.favmovie.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import net.ryanalfi.favmovie.R;
import net.ryanalfi.favmovie.REST.ApiClient;
import net.ryanalfi.favmovie.REST.ApiInterface;
import net.ryanalfi.favmovie.adapters.ListAdapter;
import net.ryanalfi.favmovie.adapters.NonScrollListView;
import net.ryanalfi.favmovie.models.Video;
import net.ryanalfi.favmovie.models.VideoRespone;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by imac on 8/28/17.
 */

public class VideosFragment extends Fragment {
    @BindView(R.id.lv_videoList)
    NonScrollListView lvVideoList;
    Unbinder unbinder;

    public VideosFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        int id = getArguments().getInt("id_movie");
        String apikey = getArguments().getString("APIKEY");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<VideoRespone> callVideo = apiService.getListVideos(id, apikey);
        callVideo.enqueue(new Callback<VideoRespone>() {
            @Override
            public void onResponse(Call<VideoRespone> call, Response<VideoRespone> response) {
                final List<Video> videos = response.body().getResults();
                ListAdapter list = new ListAdapter(videos, getContext());
                lvVideoList.setAdapter(list);
                lvVideoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videos.get(i).getVid_key()));
                        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://www.youtube.com/watch?v=" + videos.get(i).getVid_key()));
                        try {
                            startActivity(appIntent);
                        } catch (ActivityNotFoundException ex) {
                            startActivity(webIntent);
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<VideoRespone> call, Throwable t) {

            }
        });

        View view = inflater.inflate(R.layout.fragment_video, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
