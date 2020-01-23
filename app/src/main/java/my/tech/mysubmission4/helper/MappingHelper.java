package my.tech.mysubmission4.helper;

import android.database.Cursor;

import java.util.ArrayList;

import my.tech.mysubmission4.entity.Movie;

import static android.provider.BaseColumns._ID;
import static my.tech.mysubmission4.database.DatabaseContract.FavoriteColumns.BACKDROP;
import static my.tech.mysubmission4.database.DatabaseContract.FavoriteColumns.DESCRIPTION;
import static my.tech.mysubmission4.database.DatabaseContract.FavoriteColumns.POSTER;
import static my.tech.mysubmission4.database.DatabaseContract.FavoriteColumns.RATING;
import static my.tech.mysubmission4.database.DatabaseContract.FavoriteColumns.TITLE;

public class MappingHelper {

    public static ArrayList<Movie> mapCursorToArrayList (Cursor favCursor) {
        ArrayList<Movie> favoriteList = new ArrayList<>();
        Movie movie;

        while (favCursor.moveToNext()) {
            movie = new Movie();
            movie.setId(Integer.parseInt(favCursor.getString(favCursor.getColumnIndexOrThrow(_ID))));
            movie.setTitle(favCursor.getString(favCursor.getColumnIndex(TITLE)));
            movie.setVoteAverage(Double.parseDouble(favCursor.getString(favCursor.getColumnIndex(RATING))));
            movie.setPosterPath(favCursor.getString(favCursor.getColumnIndex(POSTER)));
            movie.setBackdropPath(favCursor.getString(favCursor.getColumnIndex(BACKDROP)));
            movie.setOverview(favCursor.getString(favCursor.getColumnIndex(DESCRIPTION)));

            favoriteList.add(movie);
        }

        return favoriteList;
    }
}
