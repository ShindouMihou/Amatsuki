package tk.mihou.amatsuki.entities.user;

public class UserBuilder {

    private long totalWords = 0L;
    private int totalSeries = 0;
    private long totalViews = 0L;
    private int totalReviews = 0;
    private int totalReaders = 0;
    private int totalFollowers = 0;
    private String name = "No context.";
    private String bio = "No context.";
    private String url;
    private String avatar;

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setTotalWords(long totalWords) {
        this.totalWords = totalWords;
    }

    public void setTotalSeries(int totalSeries) {
        this.totalSeries = totalSeries;
    }

    public void setTotalViews(long totalViews) {
        this.totalViews = totalViews;
    }

    public void setTotalReviews(int totalReviews) {
        this.totalReviews = totalReviews;
    }

    public void setTotalReaders(int totalReaders) {
        this.totalReaders = totalReaders;
    }

    public void setTotalFollowers(int totalFollowers) {
        this.totalFollowers = totalFollowers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public User build(){
        return new User(totalWords, totalSeries, totalViews, totalReviews, totalReaders, totalFollowers, name, bio, url, avatar);
    }

}
