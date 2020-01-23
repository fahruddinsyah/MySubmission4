package my.tech.mysubmission4.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import my.tech.mysubmission4.API.ApiClient;
import my.tech.mysubmission4.CustomOnItemClickListener;
import my.tech.mysubmission4.DetailActivity;
import my.tech.mysubmission4.R;
import my.tech.mysubmission4.entity.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<Movie> dataMovie = new ArrayList<>();
    private Context context;

    public MovieAdapter(ArrayList<Movie> dataMovie, Context context) {
        this.context = context;
        this.dataMovie = dataMovie;
    }

    public void setData(ArrayList<Movie> dataMovie) {

        if (dataMovie.size() > 0) {
            this.dataMovie.clear();
        }
        this.dataMovie.addAll(dataMovie);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, final int position) {
        holder.bind(dataMovie.get(position));

        holder.itemView.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View v, int position) {
                Intent moveToDetail = new Intent(holder.itemView.getContext(), DetailActivity.class);
                moveToDetail.putExtra(DetailActivity.EXTRA_POSITION, position);
                moveToDetail.putExtra(DetailActivity.EXTRA_DATA, dataMovie.get(position));
                holder.itemView.getContext().startActivity(moveToDetail);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return dataMovie.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPoster;
        private TextView tvTitle, tvDesc;
        private RatingBar rbMovie;

        public MovieViewHolder(View view) {
            super(view);
            imgPoster = view.findViewById(R.id.postermovie);
            tvTitle = view.findViewById(R.id.titlemovie);
            tvDesc = view.findViewById(R.id.descmovie);
            rbMovie = view.findViewById(R.id.rbMovie);
        }

        public void bind(Movie movie) {

            Glide.with(itemView.getContext())
                    .load( ApiClient.IMAGE_URL + movie.getPosterPath())
                    .into(imgPoster);

            tvTitle.setText(movie.getTitle());
            tvDesc.setText(movie.getOverview());
            rbMovie.setRating((float) (movie.getVoteAverage()/2));
        }
    }

    public void addItem(Movie movie) {
        this.dataMovie.add(movie);
        notifyItemInserted(dataMovie.size() - 1);
    }

    public void removeItem(int position) {
        this.dataMovie.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, dataMovie.size());
    }
}
