package demo.vishaan.com.moviebuster.services;

import android.app.IntentService;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import demo.vishaan.com.moviebuster.classes.Helper;
import demo.vishaan.com.moviebuster.classes.Movie;
import demo.vishaan.com.moviebuster.classes.MovieClient;
import demo.vishaan.com.moviebuster.db.MovieDataSource;
import demo.vishaan.com.moviebuster.db.MovieDbHelper;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class FetchMovieService extends IntentService {

    private MovieClient mMovieClient;
    private MovieDataSource mMovieDataSource;

    public FetchMovieService() {
        super("NetworkService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
        }

        //retrieve the JSON string
        mMovieClient = new MovieClient(getApplicationContext());
        List<Movie> j = getMoviesNowPlaying(true);
    }

    /**
     * Strt a service to retrieve movie data
     *
     * @param saveToDB boolean to save data to database
     * @return List<Movie>
     */
    public List<Movie> getMoviesNowPlaying(boolean saveToDB) {
        getApplicationContext().deleteDatabase(MovieDbHelper.DATABASE_NAME);
        mMovieDataSource = new MovieDataSource(getApplicationContext());
        mMovieDataSource.open();

        String data = mMovieClient.connect(mMovieClient.getNowPlayingUri());
        final String ARRAY_RESULTS = "results";
        List<Movie> moviesNowPlaying = new ArrayList<>();
        try{
            JSONObject root = new JSONObject(data);
            if(root != null) {
                mMovieDataSource.resetMoviesNowPlaying();
                JSONArray resultsArray = root.getJSONArray(ARRAY_RESULTS);
                Movie movie;
                for(int i=0; i<resultsArray.length(); i++) {
                    movie = Movie.fromJSON(resultsArray.getJSONObject(i));
                    if(saveToDB) {
                        try{
                            movie = mMovieDataSource.create(movie);
                            mMovieDataSource.addMovieNowPlaying(movie);
                            moviesNowPlaying.add(movie);
                        } catch(Exception e) {
                            Helper.l(e.getMessage());
                        }
                    }
                }
            }
        } catch(Exception e) {
            Helper.l(e.getMessage());
        } finally {
            mMovieDataSource.close();
        }

        return moviesNowPlaying;
    }

}
