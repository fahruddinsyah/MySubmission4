package my.tech.mysubmission4.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import my.tech.mysubmission4.API.ApiClient;
import my.tech.mysubmission4.API.GetService;
import my.tech.mysubmission4.R;
import my.tech.mysubmission4.adapter.MovieAdapter;
import my.tech.mysubmission4.entity.Movie;
import my.tech.mysubmission4.entity.MovieResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        recyclerView = view.findViewById(R.id.rvlist);
        progressBar = view.findViewById(R.id.pbmovie);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        GetService service = ApiClient.getClient().create(GetService.class);

        Call<MovieResponse> call = service.getAllMovies(ApiClient.API_KEY, ApiClient.LANGUAGE);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                ArrayList<Movie> movies = response.body().getResults();
                recyclerView.setAdapter(new MovieAdapter(movies, getContext()));
                showLoading(false);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to Connecr Internet!", Toast.LENGTH_SHORT).show();
            }
        });

        showLoading(true);

        return view;
    }

    private void showLoading(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
