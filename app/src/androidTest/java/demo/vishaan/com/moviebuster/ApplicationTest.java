package demo.vishaan.com.moviebuster;

import android.app.Application;
import android.test.ApplicationTestCase;

import demo.vishaan.com.moviebuster.classes.Helper;
import demo.vishaan.com.moviebuster.classes.MovieClient;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    private MovieClient mClient;

    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mClient = new MovieClient(getContext());
    }

    public void testNetworkClient() {
        String str = mClient.connect(MovieClient.URLS.MOVIES_NOW_PLAYING);
        assertNotNull(str);
        Helper.l(str);
    }
}