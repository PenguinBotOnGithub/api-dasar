package com.example.apidasar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import com.example.apidasar.databinding.ActivityMainBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.rvMovie.setLayoutManager(new GridLayoutManager(this,2));

        getPopularMovie();
    }

    private void getPopularMovie(){
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        //TODO
        //Get Data From API
        /*Create handle for the RetrofitInstance interface*/
        ApiService service = ApiClient.getRetrofitInstance().create(ApiService.class);
        String apiKey = getResources().getString(R.string.api_key);
        Call<ResponsePopularMovie> call = service.getPopularMovies(apiKey);
        call.enqueue(new Callback<ResponsePopularMovie>() {
            @Override
            public void onResponse(Call<ResponsePopularMovie> call, Response<ResponsePopularMovie> response) {
                progressDialog.dismiss();
                ArrayList<Movie> movies = response.body().getResults();
                MovieAdapter adapter = new MovieAdapter(movies);
                binding.rvMovie.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponsePopularMovie> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}