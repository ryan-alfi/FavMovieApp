package net.ryanalfi.favmovie;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import net.ryanalfi.favmovie.database.DatabaseHandler;
import net.ryanalfi.favmovie.database.Myfavmovie;
import net.ryanalfi.favmovie.fragment.ReviewsFragment;
import net.ryanalfi.favmovie.fragment.VideosFragment;
import net.ryanalfi.favmovie.models.MovieDetailRespone;
import net.ryanalfi.favmovie.models.Video;
import net.ryanalfi.favmovie.models.VideoRespone;

import java.util.ArrayList;
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
    @BindView(R.id.pager_header)
    TabLayout pagerHeader;
    @BindView(R.id.pager)
    ViewPager pager;
    private DatabaseHandler db = new DatabaseHandler(DetailActivity.this);
    @BindView(R.id.btn_unfavorite)
    Button btnUnfavorite;

    private String mTitle, mPosterUrl, mYear, mSinopsis;
    private int idMovie, mDurasi;
    private double mVoteAverage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        idMovie = intent.getIntExtra("idMovie", 0);

        final ProgressDialog dialog = ProgressDialog.show(DetailActivity.this, "",
                "Loading. Please wait...", true);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        Bundle bundle = new Bundle();
        bundle.putInt("id_movie", idMovie);
        bundle.putString("APIKEY", API_KEY);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        VideosFragment videosF = new VideosFragment();
        videosF.setArguments(bundle);
        ReviewsFragment reviewsF = new ReviewsFragment();
        reviewsF.setArguments(bundle);

        adapter.addFragment(videosF, "Videos");
        adapter.addFragment(reviewsF, "Reviews");

        pager.setAdapter(adapter);

        pagerHeader.setupWithViewPager(pager);

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

                dialog.dismiss();
                setupView();
            }

            @Override
            public void onFailure(Call<MovieDetailRespone> call, Throwable t) {

            }
        });

        Call<VideoRespone> callVideo = apiService.getListVideos(idMovie, API_KEY);
        callVideo.enqueue(new Callback<VideoRespone>() {
            @Override
            public void onResponse(Call<VideoRespone> call, Response<VideoRespone> response) {
                final List<Video> videos = response.body().getResults();

                ListAdapter list = new ListAdapter(videos, DetailActivity.this);
//                lvVideo.setAdapter(list);
//
//                lvVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videos.get(i).getVid_key()));
//                        Intent webIntent = new Intent(Intent.ACTION_VIEW,
//                                Uri.parse("http://www.youtube.com/watch?v=" + videos.get(i).getVid_key()));
//                        try {
//                            startActivity(appIntent);
//                        } catch (ActivityNotFoundException ex) {
//                            startActivity(webIntent);
//                        }
//                    }
//                });
            }

            @Override
            public void onFailure(Call<VideoRespone> call, Throwable t) {

            }
        });

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Myfavmovie> contacts = db.getAllFavMovie();

        for (Myfavmovie cn : contacts) {
            String log = "MovieId: " + cn.getIdmovie() + " ,State: " + cn.getStatemovie();
            // Writing Contacts to log
            Log.d("thisisit: ", log);
        }
    }

    public void setupView() {
        Glide.with(DetailActivity.this).load("http://image.tmdb.org/t/p/w185/" + mPosterUrl).override(200, 300).centerCrop().into(ivPosterMovie);
        tvTitleMovie.setText(mTitle);

        String[] justYear = mYear.split("-");
        tvYearMovie.setText(justYear[0]);

        tvDurasiMovie.setText(String.valueOf(mDurasi) + "min");
        tvVoteMovie.setText(String.valueOf(mVoteAverage));
        tvSinopsisMovie.setText(mSinopsis);

        if (isFavMovie(idMovie)) {
            btnFavorite.setVisibility(View.GONE);
            btnUnfavorite.setVisibility(View.VISIBLE);
        } else {
            btnUnfavorite.setVisibility(View.GONE);
            btnFavorite.setVisibility(View.VISIBLE);
        }

    }

    public boolean isFavMovie(int id) {
        Myfavmovie favorite = db.getFavMovie(id);
        if (favorite != null) {

            String state = favorite.getStatemovie();
            if (state.equalsIgnoreCase("true")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @OnClick(R.id.btn_favorite)
    public void onViewClicked() {
        db.addFavoriteToDB(idMovie);
        btnFavorite.setVisibility(View.GONE);
        btnUnfavorite.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_unfavorite)
    public void onViewUnClicked() {
        db.deleteFavMovie(idMovie);
        btnUnfavorite.setVisibility(View.GONE);
        btnFavorite.setVisibility(View.VISIBLE);
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter{
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
