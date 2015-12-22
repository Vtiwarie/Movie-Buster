package demo.vishaan.com.moviebuster.classes;

import org.json.JSONObject;

/**
 * Created by Vishaan on 12/13/2015.
 */
public class Movie {

    private int id;
    private String title;
    private String posterPath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static Movie fromJSON(JSONObject jsonObject) {

        try {
            Movie movie = new Movie();
            movie.setId(jsonObject.getInt("id"));
            movie.setTitle(jsonObject.getString("title"));
            movie.setPosterPath(jsonObject.getString("poster_path"));
            return movie;
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
