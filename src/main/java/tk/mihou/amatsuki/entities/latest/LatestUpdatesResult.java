package tk.mihou.amatsuki.entities.latest;

import java.util.List;
import java.util.Objects;

public class LatestUpdatesResult {

    private String storyURL;
    private String authorURL;
    private String authorName;
    private List<String> genres;
    private String chapterTitle;
    private String lastUpdate;
    private String storyName;
    private String thumbnail;
    private String chapterURL;

    public LatestUpdatesResult(String storyURL, String authorURL, String authorName, List<String> genres, String chapterTitle, String lastUpdate, String storyName, String thumbnail, String chapterURL) {
        this.storyURL = storyURL;
        this.authorURL = authorURL;
        this.authorName = authorName;
        this.genres = genres;
        this.chapterTitle = chapterTitle;
        this.lastUpdate = lastUpdate;
        this.storyName = storyName;
        this.chapterURL = chapterURL;
        this.thumbnail = thumbnail;
    }

    /**
     * The URL for the chapter.
     * @return chapter url.
     */
    public String getChapterURL(){
        return chapterURL;
    }

    /**
     * The story's URL.
     * @return story URL.
     */
    public String getStoryURL() {
        return storyURL;
    }

    /**
     * The author's URL.
     * @return author url.
     */
    public String getAuthorURL() {
        return authorURL;
    }

    /**
     * The author's name.
     * @return author name.
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * The genres of the story.
     * @return the genres.
     */
    public List<String> getGenres() {
        return genres;
    }

    /**
     * The title of the chapter.
     * @return the chapter title.
     */
    public String getChapterTitle() {
        return chapterTitle;
    }

    /**
     * The time (minutes ago) when the update occurred.
     * @return time (x minutes ago).
     */
    public String getLastUpdate() {
        return lastUpdate;
    }

    /**
     * The story's name.
     * @return story's name.
     */
    public String getStoryName() {
        return storyName;
    }

    /**
     * The story's thumbnail.
     * @return story's thumbnail.
     */
    public String getThumbnail() {
        return thumbnail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LatestUpdatesResult that = (LatestUpdatesResult) o;
        return Objects.equals(storyURL, that.storyURL) &&
                Objects.equals(authorURL, that.authorURL) &&
                authorName.equals(that.authorName) &&
                Objects.equals(genres, that.genres) &&
                Objects.equals(chapterTitle, that.chapterTitle) &&
                Objects.equals(lastUpdate, that.lastUpdate) &&
                storyName.equals(that.storyName) &&
                Objects.equals(thumbnail, that.thumbnail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storyURL, authorURL, authorName, genres, chapterTitle, lastUpdate, storyName, thumbnail);
    }
}
