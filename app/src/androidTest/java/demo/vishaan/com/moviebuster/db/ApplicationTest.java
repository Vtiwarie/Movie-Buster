package demo.vishaan.com.moviebuster.db;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.ApplicationTestCase;

import demo.vishaan.com.moviebuster.classes.Helper;
import demo.vishaan.com.moviebuster.classes.MovieClient;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    private MovieClient mClient;
    private MovieDbHelper mMovieHelper;
    private SQLiteDatabase mMovieDb;

    public ApplicationTest() {
        super(Application.class);
    }

    void deleteDatabases() {
        mContext.deleteDatabase(MovieDbHelper.DATABASE_NAME);
    }

    @Override
    protected void setUp() throws Exception {
        deleteDatabases();
        mClient = new MovieClient(getContext());
    }

    /**
     * Test the Movies database to ensure it has been set up properly.
     */
    public void testDB() {
        mMovieHelper = new MovieDbHelper(mContext);
        mMovieDb = mMovieHelper.getWritableDatabase();
        assertEquals(true, mMovieDb.isOpen());
        ContentValues cv = new ContentValues();
        cv.put(MovieContracts.MovieContract.Columns.MOVIE_ID, 4);
        cv.put(MovieContracts.MovieContract.Columns.MOVIE_TITLE, "movie title");
        cv.put(MovieContracts.MovieContract.Columns.MOVIE_OVERVIEW, "movie overview");
        cv.put(MovieContracts.MovieContract.Columns.MOVIE_POSTER_PATH, "movie poster path");
        cv.put(MovieContracts.MovieContract.Columns.MOVIE_RELEASE_DATE, "movie release date");
        cv.put(MovieContracts.MovieContract.Columns.MOVIE_POPULARITY, 1.0f);
        long _id = mMovieDb.insert(MovieContracts.MovieContract.TABLE_NAME, null, cv);
Helper.l("Movie created with ID " + _id);
        assertTrue(_id != -1);

        Cursor selectCursor = mMovieDb.query(
                MovieContracts.MovieContract.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        assertTrue("Data has been found.", selectCursor.moveToFirst());
        selectCursor.close();
        mMovieDb.close();
    }

    /**
     * Test the REST API network client to ensure data is received.
     */
    public void testNetworkClient() {
        String str = mClient.connect(mClient.getNowPlayingUri());
        assertNotNull(str);
        Helper.l(str);
    }
}
