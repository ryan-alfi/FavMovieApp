package net.ryanalfi.favmovie.REST;

import net.ryanalfi.favmovie.models.MovieDetailRespone;
import net.ryanalfi.favmovie.models.MovieRespone;
import net.ryanalfi.favmovie.models.VideoRespone;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by imac on 8/23/17.
 */

public interface ApiInterface {
    @GET("movie/top_rated")
    Call<MovieRespone> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MovieDetailRespone> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<VideoRespone> getListVideos(@Path("id") int id, @Query("api_key") String apiKey);
}
