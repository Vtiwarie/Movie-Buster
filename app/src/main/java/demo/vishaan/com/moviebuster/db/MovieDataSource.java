package demo.vishaan.com.moviebuster.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import demo.vishaan.com.moviebuster.classes.Helper;
import demo.vishaan.com.moviebuster.classes.Movie;

/**
 * Created by Vishaan on 12/26/2015.
 */
public class MovieDataSource {
    SQLiteOpenHelper dbHelper;
    private SQLiteDatabase db;

    public MovieDataSource(Context context) {
        dbHelper = MovieDbHelper.getInstance(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * Helper function to create a generic query
     *
     * @param columns
     * @param selection
     * @param selectionArgs
     * @param groupBy
     * @param having
     * @param orderBy
     * @param limit
     * @return Cursor
     */
    private Cursor query(String tableName, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        Cursor cursor = db.query(
                tableName,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy,
                limit
        );
        return cursor;
    }

    /**
     * Create a list of movies from a cursor object
     *
     * @param cursor
     * @return
     */
    private List<Movie> createMoviesFromCursor(Cursor cursor) {
        List<Movie> movies = new ArrayList<>();
        Movie movie = new Movie();
        while (cursor.moveToNext()) {
            movie.setId(cursor.getLong(cursor.getColumnIndex(MovieContracts.MovieContract.Columns._ID)));
            movie.setMovieId(cursor.getLong(cursor.getColumnIndex(MovieContracts.MovieContract.Columns.MOVIE_ID)));
            movie.setPopularity(cursor.getDouble(cursor.getColumnIndex(MovieContracts.MovieContract.Columns.MOVIE_POPULARITY)));
            movie.setOverView(cursor.getString(cursor.getColumnIndex(MovieContracts.MovieContract.Columns.MOVIE_OVERVIEW)));
            movie.setPosterPath(cursor.getString(cursor.getColumnIndex(MovieContracts.MovieContract.Columns.MOVIE_POSTER_PATH)));
            movie.setRelaseDate(cursor.getString(cursor.getColumnIndex(MovieContracts.MovieContract.Columns.MOVIE_RELEASE_DATE)));
            movie.setTitle(cursor.getString(cursor.getColumnIndex(MovieContracts.MovieContract.Columns.MOVIE_TITLE)));
            movies.add(movie);
        }
        cursor.close();
        return movies;
    }

    /**
     * Add a movie to the database
     *
     * @param movie to add to the database
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
        long movie_id = db.insert(MovieContracts.MovieContract.TABLE_NAME, null, cv);
        movie.setId(movie_id);
        Helper.l("Movie created with MOVIE ID " + movie.getMovieId());
        return movie;
    }

    /**
     * Add a link to the database that logs movies that are now playing
     *
     * @param movie to add a link to
     * @return ID of the row entry
     */
    public Movie addMovieNowPlaying(Movie movie) {
        ContentValues cv = new ContentValues();
        cv.put(MovieContracts.NowPlayingContract.Columns.REF_MOVIE, movie.getId());
        long _id = db.insert(MovieContracts.NowPlayingContract.TABLE_NAME, null, cv);
        Helper.l("Movie 'Now Playing' relationship added with MOVIE ID  " + movie.getMovieId());
        return movie;
    }

    /**
     * Truncate the list of movies now playing
     */
    public void resetMoviesNowPlaying() {
        db.delete(MovieContracts.NowPlayingContract.TABLE_NAME, null, null);
    }

    /**
     * Retrive list of all movies stored in database
     *
     * @return List<Movie>
     */
    public List<Movie> getAllMovies() {
        Cursor cursor = query(MovieContracts.MovieContract.TABLE_NAME, null, null, null, null, null, null, null);
        Helper.l(String.valueOf(cursor.getCount()) + " entries found");
        return createMoviesFromCursor(cursor);
    }

    /**
     * Retrive list of all movies that are now playing stored in database
     *
     * @return List<Movie> get all movies now playing
     */
    public List<Movie> getAllMoviesNowPlaying() {
        String sql = "SELECT * FROM " + MovieContracts.MovieContract.TABLE_NAME
                + " INNER JOIN " + MovieContracts.NowPlayingContract.TABLE_NAME
                + " ON " + MovieContracts.MovieContract.TABLE_NAME + "." + MovieContracts.MovieContract._ID + " = " + MovieContracts.NowPlayingContract.TABLE_NAME + "." + MovieContracts.NowPlayingContract.Columns.REF_MOVIE;
        Cursor cursor = db.rawQuery(sql, null);
        Helper.l(String.valueOf(cursor.getCount()) + " entries found");
        return createMoviesFromCursor(cursor);
    }
}
