package tk.mihou.amatsuki.entities.story.lower;

public class StoryResultBuilder {

    private String name = "No context.";
    private String thumbnail = "No context.";
    private String url = "No Context.";
    private String synopsis = "No Context";


    public void setName(String name) {
        this.name = name;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public StoryResults build(){
        return new StoryResults(name, thumbnail, synopsis, url);
    }
}
