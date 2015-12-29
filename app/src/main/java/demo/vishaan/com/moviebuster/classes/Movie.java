package demo.vishaan.com.moviebuster.classes;

import org.json.JSONObject;

/**
 * Created by Vishaan on 12/13/2015.
 */
public class Movie {

    private long id;
    private long movie_id;
    private String title;
    private String overView;
    private double popularity;
    private String relaseDate;
    private String posterPath;

    /**
     * Converts a JSON object to a movie object
     *
     * @param jsonObject
     * @return
     */
    public static Movie fromJSON(JSONObject jsonObject) {

        try {
            Movie movie = new Movie();
            movie.setMovieId(jsonObject.getLong("id"));
            movie.setTitle(jsonObject.getString("title"));
            movie.setOverView(jsonObject.getString("overview"));
            movie.setPopularity(jsonObject.getDouble("popularity"));
            movie.setRelaseDate(jsonObject.getString("release_date"));
            movie.setPosterPath(jsonObject.getString("poster_path"));
            return movie;
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /****************************************
     * GETTERS AND SETTERS
     ****************************************/
    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getMovieId() {
        return movie_id;
    }

    public void setMovieId(long movie_id) {
        this.movie_id = movie_id;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getRelaseDate() {
        return relaseDate;
    }

    public void setRelaseDate(String relaseDate) {
        this.relaseDate = relaseDate;
    }
}
