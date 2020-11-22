package tk.mihou.amatsuki.entities.frontpage;

public class FrontpagePanelBuilder {

    private String storyName;
    private String thumbnail;
    private String storyURL;

    public void setStoryName(String storyName) {
        this.storyName = storyName;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setStoryURL(String storyURL) {
        this.storyURL = storyURL;
    }

    public FrontpagePanel build(){
        return new FrontpagePanel(storyName, thumbnail, storyURL);
    }

}
