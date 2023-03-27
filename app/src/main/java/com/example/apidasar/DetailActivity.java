package com.example.apidasar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.apidasar.databinding.ActivityDetailBinding;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private ProgressDialog progressDialog;
    boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        String movieId = intent.getStringExtra("movie_id");

        getDetailMovie(movieId);
    }

    private void getDetailMovie(String movieId){
        progressDialog = new ProgressDialog(DetailActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = ApiClient.getRetrofitInstance().create(ApiService.class);
        String apiKey = getResources().getString(R.string.api_key);
        Call<Movie> call = service.getDetailMovie(movieId, apiKey);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                String releaseDate = response.body().getReleaseDate();
                double voteAverage = response.body().getVoteAverage();
                String title = response.body().getTitle();
                String baseUrlImage = getString(R.string.base_url_image_w500);
                String backdropPath = baseUrlImage + response.body().getBackdropPath();
                int voteCount = response.body().getVoteCount();
                ArrayList<Movie.Genre> genres = response.body().getGenres();
                String overview = response.body().getOverview();

                setDataUI(releaseDate, voteAverage, title, backdropPath, voteCount, genres, overview);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DetailActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDataUI(String releaseDate,
                           double voteAverage,
                           String title,
                           String backdropPath,
                           int voteCount,
                           ArrayList<Movie.Genre> genres,
                           String overview) {
        binding.movieName.setText(title);
        binding.movieRating.setText(String.format("%.2f", voteAverage));
        binding.movieRatingCount.setText(String.valueOf(voteCount));
        binding.movieDescription.setText(overview);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date date;
        try {
            date = simpleDateFormat.parse(releaseDate);
            simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
            releaseDate = simpleDateFormat.format(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        binding.movieReleaseDate.setText(releaseDate);

        Picasso.get().load(backdropPath)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder_error)
                .into(binding.moviePoster);

        String genresString = genres.get(0).getName();
        for (int i = 1; i < genres.size(); i++) {
            genresString = String.format("%s, %s", genresString, genres.get(i).getName());
        }
        binding.movieGenre.setText(genresString);

        progressDialog.dismiss();
    }

    private void setFabFavorite(){
        binding.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFavorite)
                    binding.btnFavorite.setImageResource(R.drawable.ic_unfavorite);
                else
                    binding.btnFavorite.setImageResource(R.drawable.ic_favorite);
                isFavorite = !isFavorite;
            }
        });
    }

    private void setBtnShare(String value){
        binding.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(); intent2.setAction(Intent.ACTION_SEND);
                intent2.setType("text/plain");
                intent2.putExtra(Intent.EXTRA_TEXT, value );
                startActivity(Intent.createChooser(intent2, "Share via"));
            }
        });
    }

    private void setBtnBrowser(String url){
        binding.btnBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }


}