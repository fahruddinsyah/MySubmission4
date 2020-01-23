package my.tech.mysubmission4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import my.tech.mysubmission4.API.ApiClient;
import my.tech.mysubmission4.database.FavoriteHelper;
import my.tech.mysubmission4.entity.Movie;

import static my.tech.mysubmission4.database.DatabaseContract.FavoriteColumns.TITLE;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgPoster, imgBackdrop;
    private TextView tvTitleMovie, tvTitleTv, tvDesc;
    private Button btnFavorite;

    private FavoriteHelper favoriteHelper;
    private Movie movie;
    private int position;
    private boolean isFavorite;

    public static final String EXTRA_DATA = "extra_data";
    public static final String EXTRA_POSITION = "extra_position";
    public static final int RESULT_ADD = 101;
    public static final int RESULT_DELETE = 301;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imgPoster = findViewById(R.id.imgPosterDetail);
        imgBackdrop = findViewById(R.id.imgBackdropDetail);
        tvTitleMovie = findViewById(R.id.titleMovieDetail);
        tvTitleTv = findViewById(R.id.titleTvDetail);
        tvDesc = findViewById(R.id.descDetail);
        btnFavorite = findViewById(R.id.btnFavorite);

        movie = getIntent().getParcelableExtra(EXTRA_DATA);
        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());
        String idMovie = Integer.toString(movie.getId());

        String btnTitle = null;

        if (isFavorite) {
            if (favoriteHelper.checkMovie(idMovie)) {
                btnTitle = getString(R.string.title_unfavorite);
            }
        } else {
            btnTitle = getString(R.string.title_favorite);
        }

        setMovie();
        btnFavorite.setText(btnTitle);
        btnFavorite.setOnClickListener(this);

    }

    private void setMovie() {
        movie = getIntent().getParcelableExtra(EXTRA_DATA);
        if (movie != null) {
            position = movie.getId();
            Glide.with(this)
                    .load(ApiClient.IMAGE_URL + movie.getPosterPath())
                    .into(imgPoster);

            Glide.with(this)
                    .load(ApiClient.IMAGE_URL + movie.getBackdropPath())
                    .into(imgBackdrop);

            tvTitleMovie.setText(movie.getTitle());
            tvTitleTv.setText(movie.getOriginalName());
            tvDesc.setText(movie.getOverview());
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnFavorite) {
            Movie movie = getIntent().getParcelableExtra(EXTRA_DATA);

            setMovie();

            Intent intent = new Intent();
            intent.putExtra(EXTRA_DATA, movie);
            intent.putExtra(EXTRA_POSITION, position);

            if (isFavorite) {
                //add
                assert movie != null;
                long result = favoriteHelper.insert(movie);
                if (result > 0);
                movie.setId((int) result);
                setResult(RESULT_ADD, intent);
                finish();
            } else {
                //delete
                favoriteHelper.deleteById(movie.getId());
            }
        }
    }
}
