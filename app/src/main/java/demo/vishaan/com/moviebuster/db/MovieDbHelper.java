package demo.vishaan.com.moviebuster.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Vishaan on 12/21/2015.
 */
public class MovieDbHelper extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION= 1;
    static final String DATABASE_NAME = "moviebuster.db";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createMovieTable = "CREATE TABLE " + MovieContracts.MovieContract.TABLE_NAME + " (" +
                MovieContracts.MovieContract.Columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieContracts.MovieContract.Columns.MOVIE_ID + " INTEGER UNIQUE NOT NULL, " +
                MovieContracts.MovieContract.Columns.MOVIE_TITLE + " TEXT NOT NULL, " +
                MovieContracts.MovieContract.Columns.MOVIE_OVERVIEW + " TEXT NOT NULL, " +
                MovieContracts.MovieContract.Columns.MOVIE_POSTER_PATH + " TEXT NOT NULL, " +
                MovieContracts.MovieContract.Columns.MOVIE_RELEASE_DATE + " TEXT NOT NULL, " +
                MovieContracts.MovieContract.Columns.MOVIE_POPULARITY + " REAL NOT NULL " +
                ");"
                ;

        db.execSQL(createMovieTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContracts.MovieContract.TABLE_NAME);
        onCreate(db);
    }
}
