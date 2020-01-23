package my.tech.mysubmission4.database;

import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String TABLE_NAME = "movie";

    public static final class FavoriteColumns implements BaseColumns {

        public static final String TITLE = "title";
        public static final String POSTER = "poster_path";
        public static final String BACKDROP = "backdrop_path";
        public static final String DESCRIPTION = "overview";
        public static final String RATING = "vote_average";
    }
}
