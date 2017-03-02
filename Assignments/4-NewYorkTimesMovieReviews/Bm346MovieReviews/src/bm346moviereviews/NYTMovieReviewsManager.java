package bm346moviereviews;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by bidyut on 2/25/17.
 */
public class NYTMovieReviewsManager {

    private String urlString = "";
    private final String baseUrlString = "http://api.nytimes.com/svc/movies/v2/reviews/search.json";
    private final String apiKey = "c59d556df0134c0fa4314e80b2979417";
    private String searchString = "";

    private URL url;
    private NYTMovieReview movieReviews;

    public NYTMovieReviewsManager() {
        movieReviews = new NYTMovieReview();
    }

    public void load(String searchString) throws Exception {

        this.searchString = searchString;

        String encodedSearchString;
        try {
            encodedSearchString = URLEncoder.encode(searchString, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw ex;
        }

        urlString = baseUrlString + "?api-key=" + apiKey + "&query=" + encodedSearchString;

        try {
            url = new URL(urlString);
        } catch (MalformedURLException muEx) {
            throw muEx;
        }

        String jsonString = "";
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                jsonString += inputLine;
            }
            in.close();
        } catch (IOException ioEx) {
            throw ioEx;
        }

        try {
            parseJsonMovieReviews(jsonString);
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void parseJsonMovieReviews(String jsonString) throws Exception {

        if (jsonString == null) return;

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        movieReviews = gson.fromJson(jsonString, NYTMovieReview.class);

        reformatMovieReviews();
    }

    private void reformatMovieReviews() {
        for (NYTMovieReview.Results results : movieReviews.results) {
            if (results.byline != null)
                results.byline = StringEscapeUtils.unescapeHtml4(results.byline);
            if (results.displayTitle != null)
                results.displayTitle = StringEscapeUtils.unescapeHtml4(results.displayTitle);
            if (results.headline != null)
                results.headline = StringEscapeUtils.unescapeHtml4(results.headline);
            if (results.summaryShort != null)
                results.summaryShort = StringEscapeUtils.unescapeHtml4(results.summaryShort);
            if (results.link.suggestedLinkTest != null)
                results.link.suggestedLinkTest = StringEscapeUtils.unescapeHtml4(results.link.suggestedLinkTest);
        }
    }

    public NYTMovieReview getMovieReviews() {
        return movieReviews;
    }
}
