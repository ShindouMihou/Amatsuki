package tk.mihou.amatsuki.entities.story;

import java.util.List;
import java.util.Objects;

public class Story {

    private final String title;
    private final String synopsis;
    private final String url;
    private final String image;
    private final String creator;
    private final String views;
    private final Double rating;
    private final long favorites;
    private final int chapters;
    private final int chw;
    private final int readers;
    private final int ratings;
    private final List<String> genres;
    private final List<String> tags;

    public Story(String title, String synopsis, String url, String image, String creator, Double rating, String views,
                 long favorites, int chapters, int chw, int ratings, int readers, List<String> genres, List<String> tags) {
        this.title = title;
        this.synopsis = synopsis;
        this.url = url;
        this.image = image;
        this.creator = creator;
        this.rating = rating;
        this.views = views;
        this.favorites = favorites;
        this.chapters = chapters;
        this.chw = chw;
        this.readers = readers;
        this.genres = genres;
        this.tags = tags;
        this.ratings = ratings;
    }

    /**
     * Gets the title of the story.
     * @return the title of the story.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the tags of the story.
     * @return the tags of the story.
     */
    public List<String> getTags(){
        return tags;
    }

    /**
     * Gets the genres of the story.
     * @return genres of the story.
     */
    public List<String> getGenres(){
        return genres;
    }

    /**
     * Gets the synopsis of the story.
     * @return synopsis.
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * Retrieves the URL of the story as string form.
     * @return url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Returns the thumbnail of the story.
     * @return image url.
     */
    public String getThumbnail() {
        return image;
    }

    /**
     * Returns the story creator.
     * @return story creator's name.
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Get's the story rating.
     * @return story rating.
     */
    public Double getRating() {
        return rating;
    }

    /**
     * Gets the total view count of the story.
     * @return the total view count of the story.
     */
    public String getViews() {
        return views;
    }

    /**
     * Gets the total favorites of the story.
     * @return the total favorites of the story.
     */
    public long getFavorites() {
        return favorites;
    }

    /**
     * Gets the total chapter count of the story.
     * @return the total chapter count.
     */
    public int getChapters() {
        return chapters;
    }

    /**
     * Gets the chapter per week count of the story.
     * @return the chapter per week.
     */
    public int getChapterPerWeek() {
        return chw;
    }

    /**
     * Gets the reader count of the story.
     * @return the reader count of the story.
     */
    public int getReaders() {
        return readers;
    }

    /**
     * Gets the total rating of the story.
     * @return the total rating.
     */
    public int getRatings() {
        return ratings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Story story = (Story) o;
        return chapters == story.chapters &&
                chw == story.chw &&
                readers == story.readers &&
                ratings == story.ratings &&
                title.equals(story.title) &&
                Objects.equals(synopsis, story.synopsis) &&
                Objects.equals(url, story.url) &&
                Objects.equals(image, story.image) &&
                creator.equals(story.creator) &&
                rating.equals(story.rating) &&
                Objects.equals(views, story.views) &&
                Objects.equals(favorites, story.favorites);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, synopsis, url, image, creator, rating, views, favorites, chapters, chw, readers, ratings);
    }
}
