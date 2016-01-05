package demo.vishaan.com.moviebuster.db;

import android.provider.BaseColumns;

/**
 * Created by Vishaan on 12/24/2015.
 */
public class MovieContracts {
    public static class MovieContract implements BaseColumns{
        public static final String TABLE_NAME = "movies";

        public static class Columns implements  BaseColumns{
            public static final String MOVIE_ID = "movie_api_id";
            public static final String MOVIE_TITLE = "movie_title";
            public static final String MOVIE_OVERVIEW = "movie_overview";
            public static final String MOVIE_POSTER_PATH = "movie_poster_path";
            public static final String MOVIE_RELEASE_DATE = "movie_release_date";
            public static final String MOVIE_POPULARITY = "movie_popularity";
        }
    }

    public static class NowPlayingContract implements BaseColumns{
        public static final String TABLE_NAME = "movies_now_playing";

        public static class Columns implements  BaseColumns{
            public static final String REF_MOVIE = "mnp_movie_ref_id";
    }
    }
}
