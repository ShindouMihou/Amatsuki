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
    private String birthday;
    private String gender;
    private String location;
    private String homepage;
    private int uid;
    private String lastActive;

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setLastActive(String lastActive) {
        this.lastActive = lastActive;
    }

    public void setUID(int uid){
        this.uid = uid;
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
        return new User(uid, totalWords, totalSeries, totalViews, totalReviews, totalReaders, totalFollowers, url, name, bio, avatar,
                birthday, gender, location, homepage, lastActive);
    }

}
