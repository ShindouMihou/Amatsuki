package tk.mihou.amatsuki.entities.story.lower;

import tk.mihou.amatsuki.api.Amatsuki;
import tk.mihou.amatsuki.entities.story.Story;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class StoryResults {

    private String name;
    private String thumbnail;
    private String url;
    private String synopsis;

    public StoryResults(String name, String thumbnail, String synopsis, String url) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.synopsis = synopsis;
        this.url = url;
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

    /**
     * Get's the story's short synopsis.
     * @return short synopsis.
     */
    public String getSynopsis() {
        return synopsis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoryResults that = (StoryResults) o;
        return name.equals(that.name) &&
                Objects.equals(thumbnail, that.thumbnail) &&
                synopsis.equals(that.synopsis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, thumbnail, synopsis);
    }
}
