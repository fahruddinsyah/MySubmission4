package my.tech.mysubmission4.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import my.tech.mysubmission4.entity.Movie;

import static android.provider.BaseColumns._ID;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static my.tech.mysubmission4.database.DatabaseContract.FavoriteColumns.BACKDROP;
import static my.tech.mysubmission4.database.DatabaseContract.FavoriteColumns.DESCRIPTION;
import static my.tech.mysubmission4.database.DatabaseContract.FavoriteColumns.POSTER;
import static my.tech.mysubmission4.database.DatabaseContract.FavoriteColumns.RATING;
import static my.tech.mysubmission4.database.DatabaseContract.FavoriteColumns.TITLE;
import static my.tech.mysubmission4.database.DatabaseContract.TABLE_NAME;

public class FavoriteHelper {

    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelper databaseHelper;
    private static FavoriteHelper INSTANCE;

    private static SQLiteDatabase database;

    private FavoriteHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteHelper(context);
                }
            }
        }

        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public Cursor queryAll() {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC");
    }

    public Cursor queryById(String id) {
        return database.query(
                DATABASE_TABLE,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null
        );
    }

    public long insert(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(_ID, movie.getId());
        values.put(TITLE, movie.getTitle());
        values.put(TITLE, movie.getOriginalName());
        values.put(DESCRIPTION, movie.getOverview());
        values.put(POSTER, movie.getPosterPath());
        values.put(BACKDROP, movie.getBackdropPath());
        values.put(RATING, movie.getVoteAverage());

        return database.insert(DATABASE_TABLE, null, values);
    }

    public int update(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteById(int id) {
        database = databaseHelper.getWritableDatabase();
        return database.delete(DATABASE_TABLE, _ID + " = " + id, null);
    }

    public boolean checkMovie(String id) {
        database = databaseHelper.getWritableDatabase();
        String selectString = "SELECT * FROM " + TABLE_NAME + " WHERE " + _ID + " =?";
        Cursor cursor = database.rawQuery(selectString, new String[]{id});
        boolean checkMovie = false;
        if (cursor.moveToFirst()) {
            checkMovie = true;
            int count = 0;
            while (cursor.moveToNext()) {
                count++;
            }
            Log.d(TAG, String.format("%d records found", count));
        }
        cursor.close();
        return checkMovie;
    }
}
