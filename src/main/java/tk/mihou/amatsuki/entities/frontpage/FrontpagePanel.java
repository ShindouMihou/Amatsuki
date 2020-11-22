package tk.mihou.amatsuki.entities.frontpage;

import java.util.Objects;

public class FrontpagePanel {

    private String storyName;
    private String thumbnail;
    private String storyURL;

    public FrontpagePanel(String storyName, String thumbnail, String storyURL) {
        this.storyName = storyName;
        this.thumbnail = thumbnail;
        this.storyURL = storyURL;
    }

    /**
     * Retrieves the story name.
     * @return the story name.
     */
    public String getStoryName() {
        return storyName;
    }

    /**
     * Retrieves the story's thumbnail.
     * @return the story's thumbnail URL.
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * Retrieves the story's link.
     * @return the story's URL.
     */
    public String getStoryURL() {
        return storyURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FrontpagePanel that = (FrontpagePanel) o;
        return storyName.equals(that.storyName) &&
                thumbnail.equals(that.thumbnail) &&
                storyURL.equals(that.storyURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storyName, thumbnail, storyURL);
    }
}
