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
import my.tech.mysubmission4.DetailActivity;
import my.tech.mysubmission4.R;
import my.tech.mysubmission4.entity.Movie;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvViewHolder> {

    private ArrayList<Movie> dataTv;
    private Context context;

    public TvShowAdapter(ArrayList<Movie> dataTv, Context context) {
        this.dataTv = dataTv;
        this.context = context;
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new TvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvViewHolder holder, final int position) {
        holder.bind(dataTv.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToDetail = new Intent(holder.itemView.getContext(), DetailActivity.class);
                moveToDetail.putExtra(DetailActivity.EXTRA_DATA, dataTv.get(position));
                holder.itemView.getContext().startActivity(moveToDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataTv.size();
    }

    public class TvViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPoster;
        private TextView tvTitle, tvDesc;
        private RatingBar rbTv;

        public TvViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.postermovie);
            tvTitle = itemView.findViewById(R.id.titlemovie);
            tvDesc = itemView.findViewById(R.id.descmovie);
            rbTv = itemView.findViewById(R.id.rbMovie);
        }

        public void bind(Movie movie) {

            Glide.with(itemView.getContext())
                    .load(ApiClient.IMAGE_URL + movie.getPosterPath())
                    .into(imgPoster);

            tvTitle.setText(movie.getOriginalName());
            tvDesc.setText(movie.getOverview());
            rbTv.setRating((float) (movie.getVoteAverage()/2));
        }
    }
}
