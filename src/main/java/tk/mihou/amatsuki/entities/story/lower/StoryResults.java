package tk.mihou.amatsuki.entities.story.lower;

import tk.mihou.amatsuki.api.Amatsuki;
import tk.mihou.amatsuki.entities.story.Story;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class StoryResults {

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
    private String lastUpdated;
    private String word;

    public StoryResults(String name, String thumbnail, String url, String shortSynopsis, String fullSynopsis, List<String> genres, String creator, String views, Double rating, long favorites, int chapters, int chw, int readers, int reviews, String lastUpdated, String word) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.url = url;
        this.shortSynopsis = shortSynopsis;
        this.fullSynopsis = fullSynopsis;
        this.genres = genres;
        this.creator = creator;
        this.views = views;
        this.rating = rating;
        this.favorites = favorites;
        this.chapters = chapters;
        this.chw = chw;
        this.readers = readers;
        this.reviews = reviews;
        this.lastUpdated = lastUpdated;
        this.word = word;
    }

    /**
     * Returns the word count. (e.g: 7.7k)
     * @return the word count.
     */
    public String getWordCount(){
        return word;
    }

    /**
     * Retrieves the short synopsis.
     * @return the short synopsis.
     */
    public String getShortSynopsis() {
        return shortSynopsis;
    }

    /**
     * Retrieves the long synopsis.
     * @return the long synopsis/full synopsis.
     */
    public String getFullSynopsis() {
        return fullSynopsis;
    }

    /**
     * Retrieves the list of genres.
     * @return the list of genres.
     */
    public List<String> getGenres() {
        return genres;
    }

    /**
     * Retrieves the creator's name.
     * @return the creator.
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Retrieves the view count of the story.
     * @return the view count.
     */
    public String getViews() {
        return views;
    }

    /**
     * Retrieves the rating of the story.
     * @return the rating.
     */
    public Double getRating() {
        return rating;
    }

    /**
     * Retrieves the favorite count of the story.
     * @return the favorite count.
     */
    public long getFavorites() {
        return favorites;
    }

    /**
     * Retrieves the chapter count of the story.
     * @return the chapter count.
     */
    public int getChapters() {
        return chapters;
    }

    /**
     * Retrieves the chapter per week of the story.
     * @return the chapter per week.
     */
    public int getChapterPerWeek() {
        return chw;
    }

    /**
     * Retrieves the reader count of the story.
     * @return the reader count.
     */
    public int getReaders() {
        return readers;
    }

    /**
     * Retrieves the amount of reviews the story has.
     * @return the review count.
     */
    public int getReviews() {
        return reviews;
    }

    /**
     * Retrieves when the story was last updated.
     * @return the last update.
     */
    public String getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Gets the URL of the result.
     * @return the story's url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Transforms the result into a story, returns a CompletableFuture<Optional> to prevent mishaps.
     * @return Story.
     */
    public CompletableFuture<Optional<Story>> transformToStory(){
        return new Amatsuki().getStoryFromUrl(url);
    }

    /**
     * Gets the story's name.
     * @return the story.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the story's thumbnail.
     * @return the story's thumbnail.
     */
    public String getThumbnail() {
        return thumbnail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoryResults that = (StoryResults) o;
        return favorites == that.favorites &&
                chapters == that.chapters &&
                chw == that.chw &&
                readers == that.readers &&
                reviews == that.reviews &&
                name.equals(that.name) &&
                thumbnail.equals(that.thumbnail) &&
                url.equals(that.url) &&
                shortSynopsis.equals(that.shortSynopsis) &&
                fullSynopsis.equals(that.fullSynopsis) &&
                Objects.equals(genres, that.genres) &&
                creator.equals(that.creator) &&
                Objects.equals(views, that.views) &&
                Objects.equals(rating, that.rating) &&
                Objects.equals(lastUpdated, that.lastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, thumbnail, url, shortSynopsis, fullSynopsis, genres, creator, views, rating, favorites, chapters, chw, readers, reviews, lastUpdated);
    }
}
