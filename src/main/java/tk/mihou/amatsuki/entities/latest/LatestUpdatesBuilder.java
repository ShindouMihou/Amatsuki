package tk.mihou.amatsuki.entities.latest;

import java.util.List;

public class LatestUpdatesBuilder {

    private String storyURL;
    private String authorURL;
    private String authorName;
    private List<String> genres;
    private String chapterTitle;
    private String lastUpdate;
    private String storyName;
    private String thumbnail;
    private String chapterURL;

    public void setChapterURL(String chapterURL){
        this.chapterURL = chapterURL;
    }

    public void setStoryURL(String storyURL) {
        this.storyURL = storyURL;
    }

    public void setAuthorURL(String authorURL) {
        this.authorURL = authorURL;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setStoryName(String storyName) {
        this.storyName = storyName;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public LatestUpdatesResult build(){
        return new LatestUpdatesResult(storyURL, authorURL, authorName,  genres, chapterTitle, lastUpdate, storyName, thumbnail, chapterURL);
    }
}
