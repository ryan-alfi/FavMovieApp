package net.ryanalfi.favmovie;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import net.ryanalfi.favmovie.REST.ApiClient;
import net.ryanalfi.favmovie.REST.ApiInterface;
import net.ryanalfi.favmovie.adapters.DataAdapter;
import net.ryanalfi.favmovie.database.DatabaseHandler;
import net.ryanalfi.favmovie.models.Movie;
import net.ryanalfi.favmovie.models.MovieRespone;

import java.net.InetAddress;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements DataAdapter.ListItemClickListener {

    private final static String API_KEY = "1c96b66dba8d2788bc3760b972f76331";
    private DatabaseHandler db = new DatabaseHandler(MainActivity.this);
    private ProgressBar progressBar;
    private Toast mToast;
    private ApiInterface apiService;
    private boolean isPopular = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isConnected(MainActivity.this)) buildDialog(MainActivity.this).show();
        else {
            setContentView(R.layout.activity_main);
            progressBar = (ProgressBar) findViewById(R.id.pb_main);
            progressBar.setVisibility(View.VISIBLE);
        }

        ButterKnife.bind(this);


        apiService = ApiClient.getClient().create(ApiInterface.class);

        if (isPopular)
            setupRemoteTopRated();
        else
            setupRemotePopular();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPopular)
            setupRemoteTopRated();
        else
            setupRemotePopular();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemTopRated:
                if (!isPopular)
                    Toast.makeText(getApplicationContext(),"This is top rated movies.", Toast.LENGTH_LONG).show();
                isPopular = false;
                setupRemoteTopRated();
                return true;
            case R.id.itemPopular:
                if (isPopular)
                    Toast.makeText(getApplicationContext(),"This is popular movies.", Toast.LENGTH_LONG).show();
                isPopular = true;
                setupRemotePopular();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("idMovie", clickedItemIndex);
        startActivity(intent);

    }

    private void setupRemoteTopRated(){
        Call<MovieRespone> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieRespone>() {
            @Override
            public void onResponse(Call<MovieRespone> call, Response<MovieRespone> response) {
                List<Movie> movies = response.body().getResults();

                if (movies.size()>0){
                    progressBar.setVisibility(View.GONE);
                    Log.d("Debug: ", "Movie Top Rated " + movies.size());
                }

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_main);
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
                recyclerView.setLayoutManager(layoutManager);

                int[] myFavoriteMovie=  db.getAllFavMovieId();
                List<Movie> arrMovie = response.body().getResults();
                DataAdapter adapter = new DataAdapter(arrMovie, myFavoriteMovie, getApplicationContext(), MainActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MovieRespone> call, Throwable t) {

            }
        });
    }

    private void setupRemotePopular(){
        Call<MovieRespone> call = apiService.getPopularMovies(API_KEY);
        call.enqueue(new Callback<MovieRespone>() {
            @Override
            public void onResponse(Call<MovieRespone> call, Response<MovieRespone> response) {
                List<Movie> movies = response.body().getResults();

                if (movies.size()>0){
                    progressBar.setVisibility(View.GONE);
                    Log.d("Debug: ", "Movie Popular " + movies.size());
                }

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_main);
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
                recyclerView.setLayoutManager(layoutManager);

                int[] myFavoriteMovie=  db.getAllFavMovieId();
                List<Movie> arrMovie = response.body().getResults();
                DataAdapter adapter = new DataAdapter(arrMovie, myFavoriteMovie, getApplicationContext(), MainActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MovieRespone> call, Throwable t) {

            }
        });
    }

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
        else return false;
        } else
        return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }
}
