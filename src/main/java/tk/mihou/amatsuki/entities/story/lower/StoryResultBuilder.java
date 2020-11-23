package tk.mihou.amatsuki.entities.story.lower;

import java.util.List;

public class StoryResultBuilder {

    private String name;
    private String thumbnail;
    private String url;
    private String shortSynopsis;
    private String fullSynopsis;
    private List<String> genres;
    private String creator;
    private String views;
    private Double rating;
    private long favorites;
    private int chapters;
    private int chw;
    private int readers;
    private int reviews;
    private String word;
    private String authorURL;
    private String lastUpdated;


    public void setName(String name) {
        this.name = name;
    }

    public void setWord(String word){
        this.word = word;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setShortSynopsis(String shortSynopsis) {
        this.shortSynopsis = shortSynopsis;
    }

    public void setFullSynopsis(String fullSynopsis) {
        this.fullSynopsis = fullSynopsis;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setFavorites(long favorites) {
        this.favorites = favorites;
    }

    public void setChapters(int chapters) {
        this.chapters = chapters;
    }

    public void setChw(int chw) {
        this.chw = chw;
    }

    public void setReaders(int readers) {
        this.readers = readers;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setAuthorURL(String authorURL){
        this.authorURL = authorURL;
    }

    public StoryResults build(){
        return new StoryResults(name, thumbnail, url, shortSynopsis, fullSynopsis, genres, creator, views, rating, favorites, chapters, chw, readers, reviews, lastUpdated, word, authorURL);
    }
}
