package demo.vishaan.com.moviebuster.classes;

import android.content.Context;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import demo.vishaan.com.moviebuster.R;

/**
 * Created by Vishaan on 12/13/2015.
 */
public class MovieClient {
    private static final int API_KEY_ID = R.string.API_KEY;
    public static final int API_BASE_URL_ID = R.string.API_BASE_URL;
    private String mAPIKey;
    public String  mAPIBaseURL;
    private static final String HTTP_REQUEST_METHOD = "GET";
    private Context mContext;

    public static class URLS {
        public static final String MOVIES_NOW_PLAYING = "movie/now_playing";
    }

    public static class PARAMS {
        public static final String API_KEY = "api_key";
    }

    public MovieClient(Context context) {
        mContext = context;
        mAPIKey = mContext.getResources().getString(API_KEY_ID);
        mAPIBaseURL = mContext.getResources().getString(API_BASE_URL_ID);
    }

    public Uri getNowPlaingUri() {
        Uri uri = Uri.parse(mAPIBaseURL).buildUpon()
                .appendEncodedPath(URLS.MOVIES_NOW_PLAYING)
                .appendQueryParameter(PARAMS.API_KEY, mAPIKey)
                .build();
        return uri;
    }

    public String connect(Uri uri) {
        try{

            URL url = new URL(uri.toString());
Helper.l(url.toString());
            HttpURLConnection client = (HttpURLConnection)url.openConnection();
            client.setRequestMethod(HTTP_REQUEST_METHOD);
            client.connect();

            InputStream is = client.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
Helper.l(sb.toString());

            return sb.toString();

        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
