package my.tech.mysubmission4.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import my.tech.mysubmission4.CustomOnItemClickListener;
import my.tech.mysubmission4.DetailActivity;
import my.tech.mysubmission4.R;
import my.tech.mysubmission4.adapter.MovieAdapter;
import my.tech.mysubmission4.database.FavoriteHelper;
import my.tech.mysubmission4.entity.Movie;
import my.tech.mysubmission4.helper.MappingHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements LoadMovieCallback {

    private ProgressBar progressBar;
    private RecyclerView rvList;
    private MovieAdapter adapter;
    private FavoriteHelper favoriteHelper;
    private ArrayList<Movie> dataMovie = new ArrayList<>();

    private final static String LIST_STATE_KEY = "STATE";

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_favorite, container, false);

        progressBar = view.findViewById(R.id.pbmovie);
        rvList = view.findViewById(R.id.rvlist);

        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setHasFixedSize(true);
        rvList.setAdapter(adapter);

        favoriteHelper = FavoriteHelper.getInstance(getContext());
        favoriteHelper.open();

        if (savedInstanceState == null) {
            //proses ambil data
            new LoadMovieAsync(favoriteHelper, this).execute();
        } else {
            ArrayList<Movie> list = savedInstanceState.getParcelableArrayList(LIST_STATE_KEY);
            assert list != null;
            dataMovie.addAll(list);
            adapter.setData(list);
            rvList.setAdapter(adapter);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LIST_STATE_KEY, dataMovie);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        favoriteHelper.close();
    }

    @Override
    public void preExecute() {
    }

    @Override
    public void postExecute(ArrayList<Movie> movies) {
        progressBar.setVisibility(View.INVISIBLE);
        rvList.setAdapter(adapter);
        dataMovie.addAll(movies);
    }

    private static class LoadMovieAsync extends AsyncTask<Void, Void, ArrayList<Movie>> {
        private final WeakReference<FavoriteHelper> weakFavHelper;
        private final WeakReference<LoadMovieCallback> weakCallback;


        private LoadMovieAsync(FavoriteHelper favoriteHelper, LoadMovieCallback callback) {
            weakFavHelper = new WeakReference<>(favoriteHelper);
            weakCallback = new WeakReference<>(callback);
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }
        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            Cursor dataCursor = weakFavHelper.get().queryAll();
            return MappingHelper.mapCursorToArrayList(dataCursor);
        }
        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            weakCallback.get().postExecute(movies);
        }
    }
}

interface LoadMovieCallback {
    void preExecute();
    void postExecute(ArrayList<Movie> movies);
}
