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

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class FetchMovieService extends IntentService {

    private MovieClient mMovieClient;

    public FetchMovieService() {
        super("NetworkService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
        }

        //retrieve the JSON string
        mMovieClient = new MovieClient(getApplicationContext());
        List<Movie> j = getMovieListJSON();

        //Insert movies into database


//        //send local broadcast back to UI
//        Intent movieIntent = new Intent(MovieListFragment.MovieListReceiver.ACTION_UPDATE);
//        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(movieIntent);

    }

    public List<Movie> getMovieListJSON() {
        String data = mMovieClient.connect(mMovieClient.getNowPlaingUri());
        final String ARRAY_RESULTS = "results";
Helper.l(data);
        List<Movie> moviesNowPlaying = new ArrayList<>();
        try{
Helper.l("entering movie");
            JSONObject root = new JSONObject(data);
            if(root != null) {
                JSONArray resultsArray = root.getJSONArray(ARRAY_RESULTS);
                for(int i=0; i<resultsArray.length(); i++) {
                    Movie movie = Movie.fromJSON(resultsArray.getJSONObject(i));
                    Helper.l(movie.getTitle());
                    moviesNowPlaying.add(movie);
                }
            }
        } catch(Exception e) {
Helper.l(e.getMessage());
        }

        return moviesNowPlaying;
    }
}
