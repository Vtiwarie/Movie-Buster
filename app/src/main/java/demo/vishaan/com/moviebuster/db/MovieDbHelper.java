package demo.vishaan.com.moviebuster.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Vishaan on 12/21/2015.
 */
public class MovieDbHelper extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION= 1;
    public static final String DATABASE_NAME = "moviebuster.db";
    private static MovieDbHelper instance;

    public static MovieDbHelper getInstance(Context context) {
        if(instance == null) {
            instance = new MovieDbHelper(context);
        }
        return instance;
    }

    private MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createMovieTable = "CREATE TABLE IF NOT EXISTS " + MovieContracts.MovieContract.TABLE_NAME + " (" +
                MovieContracts.MovieContract.Columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieContracts.MovieContract.Columns.MOVIE_ID + " INTEGER UNIQUE ON CONFLICT REPLACE NOT NULL, " +
                MovieContracts.MovieContract.Columns.MOVIE_TITLE + " TEXT NOT NULL, " +
                MovieContracts.MovieContract.Columns.MOVIE_OVERVIEW + " TEXT NOT NULL, " +
                MovieContracts.MovieContract.Columns.MOVIE_POSTER_PATH + " TEXT NOT NULL, " +
                MovieContracts.MovieContract.Columns.MOVIE_RELEASE_DATE + " DATE NOT NULL, " +
                MovieContracts.MovieContract.Columns.MOVIE_POPULARITY + " REAL NOT NULL " +
                ");"
                ;

        String createNowPlayingTable = "CREATE TABLE IF NOT EXISTS " + MovieContracts.NowPlayingContract.TABLE_NAME + " (" +
                MovieContracts.NowPlayingContract.Columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieContracts.NowPlayingContract.Columns.REF_MOVIE + " INTEGER UNIQUE ON CONFLICT REPLACE NOT NULL, " +

                " FOREIGN KEY (" + MovieContracts.NowPlayingContract.Columns.REF_MOVIE + ") REFERENCES " +
                MovieContracts.MovieContract.TABLE_NAME + " (" + MovieContracts.MovieContract._ID + ") " +

                ");"
                ;

        db.execSQL(createMovieTable);
        db.execSQL(createNowPlayingTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContracts.MovieContract.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieContracts.NowPlayingContract.TABLE_NAME);
        onCreate(db);
    }

}
