package demo.vishaan.com.moviebuster.classes;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Vishaan on 12/13/2015.
 */
public class Helper {

    private static final String TAG = Helper.class.getSimpleName();
    public static void l(String msg) {
        Log.v(TAG, msg);
    }

    public static void t(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
