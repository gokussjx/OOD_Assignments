package bm346moviereviews;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by bidyut on 2/25/17.
 */
public class NYTMovieReview {
    public String status;
    public String copyright;
    @SerializedName("has_more")
    public Boolean hasMore;
    @SerializedName("num_results")
    public Integer numResults;
    public List<Results> results;

    public NYTMovieReview() {

    }

    public NYTMovieReview(String status, String copyright, Boolean hasMore,
                          Integer numResults, List<Results> results) {
        this.status = status;
        this.copyright = copyright;
        this.hasMore = hasMore;
        this.numResults = numResults;
        this.results = results;
    }

    public static class Results {
        @SerializedName("display_title")
        public String displayTitle;
        @SerializedName("mpaa_rating")
        public String mpaaRating;
        @SerializedName("critics_pick")
        public Integer criticsPick;
        public String byline;
        public String headline;
        @SerializedName("summary_short")
        public String summaryShort;
        @SerializedName("publication_date")
        public String publicationDate;
        @SerializedName("opening_date")
        public String openingDate;
        @SerializedName("date_updated")
        public String dateUpdated;
        public Link link;
        public Multimedia multimedia;

        public Results(String displayTitle, String mpaaRating, Integer criticsPick,
                       String byline, String headline, String summaryShort,
                       String publicationDate, String openingDate, String dateUpdated,
                       Link link, Multimedia multimedia) {

            this.displayTitle = displayTitle;
            this.mpaaRating = mpaaRating;
            this.criticsPick = criticsPick;
            this.byline = byline;
            this.headline = headline;
            this.summaryShort = summaryShort;
            this.publicationDate = publicationDate;
            this.openingDate = openingDate;
            this.dateUpdated = dateUpdated;
            this.link = link;
            this.multimedia = multimedia;
        }

        @Override
        public String toString() {
            return this.headline;
        }

        public static class Link {
            public String type;
            public String url;
            @SerializedName("suggested_link_test")
            public String suggestedLinkTest;

            public Link(String type, String url, String suggestedLinkTest) {
                this.type = type;
                this.url = url;
                this.suggestedLinkTest = suggestedLinkTest;
            }
        }

        public class Multimedia {
            public String type;
            public String src;
            public Integer width;
            public Integer height;

            public Multimedia(String type, String src, Integer width, Integer height) {
                this.type = type;
                this.src = src;
                this.width = width;
                this.height = height;
            }
        }
    }
}