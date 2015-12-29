package demo.vishaan.com.moviebuster.db;

import android.provider.BaseColumns;

/**
 * Created by Vishaan on 12/24/2015.
 */
public class MovieContracts {
    public static class MovieContract implements BaseColumns{
        public static final String TABLE_NAME = "movies";

        public static class Columns implements  BaseColumns{
            public static final String MOVIE_ID = "movie_id";
            public static final String MOVIE_TITLE = "movie_title";
            public static final String MOVIE_OVERVIEW = "movie_overview";
            public static final String MOVIE_POSTER_PATH = "movie_poster_path";
            public static final String MOVIE_RELEASE_DATE = "movie_release_date";
            public static final String MOVIE_POPULARITY = "movie_popularity";
        }
    }
}
