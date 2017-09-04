package net.ryanalfi.favmovie.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ryanalfi.favmovie.R;
import net.ryanalfi.favmovie.REST.ApiClient;
import net.ryanalfi.favmovie.REST.ApiInterface;
import net.ryanalfi.favmovie.adapters.ListAdapter;
import net.ryanalfi.favmovie.adapters.NonScrollListView;
import net.ryanalfi.favmovie.adapters.ReviewAdapter;
import net.ryanalfi.favmovie.models.Review;
import net.ryanalfi.favmovie.models.ReviewRespone;

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

public class ReviewsFragment extends Fragment {
    @BindView(R.id.lv_reviewList)
    NonScrollListView lvReviewList;
    Unbinder unbinder;

    public ReviewsFragment() {
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
        Call<ReviewRespone> callReview = apiService.getMovieReviews(id,apikey);
        callReview.enqueue(new Callback<ReviewRespone>() {
            @Override
            public void onResponse(Call<ReviewRespone> call, Response<ReviewRespone> response) {
                final List<Review> reviews = response.body().getResults();
                ReviewAdapter list = new ReviewAdapter(reviews, getContext());
                lvReviewList.setAdapter(list);
            }

            @Override
            public void onFailure(Call<ReviewRespone> call, Throwable t) {

            }
        });

        View view = inflater.inflate(R.layout.fragment_review, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
