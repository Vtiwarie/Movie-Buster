package demo.vishaan.com.moviebuster.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import demo.vishaan.com.moviebuster.classes.Helper;
import demo.vishaan.com.moviebuster.classes.Movie;

/**
 * Created by Vishaan on 12/26/2015.
 */
public class MovieDataSource {
    SQLiteOpenHelper dbHelper;
    private SQLiteDatabase db;

    public MovieDataSource(Context context) {
        dbHelper = new MovieDbHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * Add a movie to the database
     * @param  movie to add to the database
     * @return movie with newly created ID
     */
    public Movie create(Movie movie) {
        ContentValues cv = new ContentValues();
        cv.put(MovieContracts.MovieContract.Columns.MOVIE_ID, movie.getMovieId());
        cv.put(MovieContracts.MovieContract.Columns.MOVIE_TITLE, movie.getTitle());
        cv.put(MovieContracts.MovieContract.Columns.MOVIE_OVERVIEW, movie.getOverView());
        cv.put(MovieContracts.MovieContract.Columns.MOVIE_POSTER_PATH, movie.getPosterPath());
        cv.put(MovieContracts.MovieContract.Columns.MOVIE_RELEASE_DATE, movie.getRelaseDate());
        cv.put(MovieContracts.MovieContract.Columns.MOVIE_POPULARITY, movie.getPopularity());
        long _id = db.insertOrThrow(MovieContracts.MovieContract.TABLE_NAME, null, cv);
        movie.setId(_id);
Helper.l("Movie created with ID " + _id);
        return movie;
    }
}
