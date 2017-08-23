package net.ryanalfi.favmovie;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.ryanalfi.favmovie.REST.ApiClient;
import net.ryanalfi.favmovie.REST.ApiInterface;
import net.ryanalfi.favmovie.adapters.ListAdapter;
import net.ryanalfi.favmovie.models.MovieDetailRespone;
import net.ryanalfi.favmovie.models.MovieRespone;
import net.ryanalfi.favmovie.models.Video;
import net.ryanalfi.favmovie.models.VideoRespone;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by imac on 8/23/17.
 */

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.v_title)
    View vTitle;
    @BindView(R.id.tv_title_movie)
    TextView tvTitleMovie;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.iv_poster_movie)
    ImageView ivPosterMovie;
    @BindView(R.id.tv_year_movie)
    TextView tvYearMovie;
    @BindView(R.id.tv_durasi_movie)
    TextView tvDurasiMovie;
    @BindView(R.id.btn_favorite)
    Button btnFavorite;
    @BindView(R.id.tv_vote_movie)
    TextView tvVoteMovie;
    @BindView(R.id.tv_sinopsis_movie)
    TextView tvSinopsisMovie;
    @BindView(R.id.div_line)
    View divLine;
    @BindView(R.id.lv_video)
    ListView lvVideo;

    private final static String API_KEY = "1c96b66dba8d2788bc3760b972f76331";

    private String mTitle, mPosterUrl, mYear, mSinopsis;
    private int idMovie, mDurasi;
    private double mVoteAverage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        idMovie = intent.getIntExtra("idMovie",0);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieDetailRespone> call = apiService.getMovieDetails(idMovie, API_KEY);
        call.enqueue(new Callback<MovieDetailRespone>() {
            @Override
            public void onResponse(Call<MovieDetailRespone> call, Response<MovieDetailRespone> response) {
                mTitle = response.body().getTitle();
                mPosterUrl = response.body().getPosterUrl();
                mYear = response.body().getYear();
                mDurasi = response.body().getDurasi();
                mVoteAverage = response.body().getVote();
                mSinopsis = response.body().getSinopsis();

                setupView();
            }

            @Override
            public void onFailure(Call<MovieDetailRespone> call, Throwable t) {

            }
        });

        Call<VideoRespone> callVideo = apiService.getListVideos(idMovie,API_KEY);
        callVideo.enqueue(new Callback<VideoRespone>() {
            @Override
            public void onResponse(Call<VideoRespone> call, Response<VideoRespone> response) {
                final List<Video> videos = response.body().getResults();

                ListAdapter list = new ListAdapter(videos, DetailActivity.this);
                lvVideo.setOnTouchListener(new ListView.OnTouchListener(){
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int action = event.getAction();
                        switch (action) {
                            case MotionEvent.ACTION_DOWN:
                                // Disallow ScrollView to intercept touch events.
                                v.getParent().requestDisallowInterceptTouchEvent(true);
                                break;

                            case MotionEvent.ACTION_UP:
                                // Allow ScrollView to intercept touch events.
                                v.getParent().requestDisallowInterceptTouchEvent(false);
                                break;
                        }

                        // Handle ListView touch events.
                        v.onTouchEvent(event);
                        return true;
                    }
                });
                lvVideo.setAdapter(list);

                lvVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videos.get(i).getVid_key()));
                        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://www.youtube.com/watch?v=" +  videos.get(i).getVid_key()));
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

    }

    public void setupView() {
        Glide.with(DetailActivity.this).load("http://image.tmdb.org/t/p/w185/" + mPosterUrl).override(200,300).centerCrop().into(ivPosterMovie);
        tvTitleMovie.setText(mTitle);
        tvYearMovie.setText(mYear);
        tvDurasiMovie.setText(String.valueOf(mDurasi));
        tvVoteMovie.setText(String.valueOf(mVoteAverage));
        tvSinopsisMovie.setText(mSinopsis);

    }

    @OnClick(R.id.btn_favorite)
    public void onViewClicked() {
    }
}
