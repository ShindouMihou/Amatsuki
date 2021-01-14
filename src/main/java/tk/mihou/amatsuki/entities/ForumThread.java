package tk.mihou.amatsuki.entities;

public class ForumThread {

    private final String title;
    private final String forum;
    private final String url;

    public ForumThread(String title, String forum, String url) {
        this.title = title;
        this.forum = forum;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getForum() {
        return forum;
    }

    public String getUrl() {
        return url;
    }
}
