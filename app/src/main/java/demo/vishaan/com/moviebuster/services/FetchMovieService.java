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
        List<Movie> j = getMovieListJSON(true);
    }

    public List<Movie> getMovieListJSON(boolean saveToDB) {
        mMovieDataSource = new MovieDataSource(getApplicationContext());
        mMovieDataSource.open();
        String data = mMovieClient.connect(mMovieClient.getNowPlayingUri());
        final String ARRAY_RESULTS = "results";
        List<Movie> moviesNowPlaying = new ArrayList<>();
        try{
            JSONObject root = new JSONObject(data);
            if(root != null) {
                JSONArray resultsArray = root.getJSONArray(ARRAY_RESULTS);
                for(int i=0; i<resultsArray.length(); i++) {
                    Movie movie = Movie.fromJSON(resultsArray.getJSONObject(i));
                    moviesNowPlaying.add(movie);
                    if(saveToDB) {
                        try{
                            mMovieDataSource.create(movie);
                        } catch(Exception e) {
Helper.l(e.getMessage());
                            continue;
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
