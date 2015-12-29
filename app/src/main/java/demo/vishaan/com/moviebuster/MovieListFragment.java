package demo.vishaan.com.moviebuster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import demo.vishaan.com.moviebuster.classes.Helper;
import demo.vishaan.com.moviebuster.services.FetchMovieService;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieListFragment extends Fragment {
    private static final String ARG_MOVIE_ID = "crime_id";

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param movieID The ID of the movie in the database
     * @return A new instance of fragment MovieList.
     */
    public static MovieListFragment newInstance(long movieID) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_MOVIE_ID, movieID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MovieListReceiver mr = new MovieListReceiver();
        IntentFilter iff = new IntentFilter(MovieListReceiver.ACTION_UPDATE);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mr, iff);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovieList();
    }

    /**
     * Start a service to retrieve the movie data and save to the database
     */
    private void updateMovieList() {
        getActivity().startService(new Intent(getContext(), FetchMovieService.class));
    }

    public static class MovieListReceiver extends BroadcastReceiver {
        public static final String ACTION_UPDATE = MovieListReceiver.class.getPackage().getName() + "movie_list_update";

        @Override
        public void onReceive(Context context, Intent intent) {
Helper.l(MovieListReceiver.class.getCanonicalName() + " received!!");
        }
    }


}
