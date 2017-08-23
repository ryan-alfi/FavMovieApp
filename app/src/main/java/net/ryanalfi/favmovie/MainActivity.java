package net.ryanalfi.favmovie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import net.ryanalfi.favmovie.REST.ApiClient;
import net.ryanalfi.favmovie.REST.ApiInterface;
import net.ryanalfi.favmovie.adapters.DataAdapter;
import net.ryanalfi.favmovie.models.Movie;
import net.ryanalfi.favmovie.models.MovieRespone;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements DataAdapter.ListItemClickListener {

    private final static String API_KEY = "1c96b66dba8d2788bc3760b972f76331";
    private ProgressBar progressBar;
    private Toast mToast;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        progressBar = (ProgressBar) findViewById(R.id.pb_main);
        progressBar.setVisibility(View.VISIBLE);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<MovieRespone> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieRespone>() {
            @Override
            public void onResponse(Call<MovieRespone> call, Response<MovieRespone> response) {
                List<Movie> movies = response.body().getResults();

                if (movies.size()>0){
                    progressBar.setVisibility(View.GONE);
                    Log.d("Debug: ", "Much Movie " + movies.size());
                }

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_main);
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
                recyclerView.setLayoutManager(layoutManager);

                List<Movie> arrMovie = response.body().getResults();
                DataAdapter adapter = new DataAdapter(arrMovie, getApplicationContext(), MainActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MovieRespone> call, Throwable t) {

            }
        });
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        if (mToast != null) {
            mToast.cancel();
        }

        String toastMessage = "Item #" + clickedItemIndex + " clicked.";
        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);

        mToast.show();

        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("idMovie", clickedItemIndex);
        startActivity(intent);

    }
}
