package tk.mihou.amatsuki.entities.user;

import java.util.Objects;

public class User {

    private long totalWords;
    private int totalSeries;
    private long totalViews;
    private int totalReviews;
    private int totalReaders;
    private int totalFollowers;
    private String url;
    private String name;
    private String bio;
    private String avatar;
    private String birthday;
    private String gender;
    private String location;
    private String homepage;
    private String lastActive;

    public User(long totalWords, int totalSeries, long totalViews, int totalReviews, int totalReaders, int totalFollowers, String url, String name, String bio, String avatar, String birthday, String gender, String location, String homepage, String lastActive) {
        this.totalWords = totalWords;
        this.totalSeries = totalSeries;
        this.totalViews = totalViews;
        this.totalReviews = totalReviews;
        this.totalReaders = totalReaders;
        this.totalFollowers = totalFollowers;
        this.url = url;
        this.name = name;
        this.bio = bio;
        this.avatar = avatar;
        this.birthday = birthday;
        this.gender = gender;
        this.location = location;
        this.homepage = homepage;
        this.lastActive = lastActive;
    }

    /**
     * Gets the user's birthday if specified.
     * @return the birthday.
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * Gets the user's gender if specified.
     * @return gender.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Gets the user's location if specified.
     * @return location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets the user's homepage if specified.
     * @return homepage.
     */
    public String getHomepage() {
        return homepage;
    }

    /**
     * Retrieves when the user was last active.
     * @return last active.
     */
    public String getLastActive() {
        return lastActive;
    }

    /**
     * Gets the total words published by the user from SH.
     * @return total word count.
     */
    public long getTotalWords() {
        return totalWords;
    }

    /**
     * Gets the total series the user has publisehd on SH.
     * @return the total series count.
     */
    public int getTotalSeries() {
        return totalSeries;
    }

    /**
     * Gets the total views the user has received on all their stories.
     * @return the total view count.
     */
    public long getTotalViews() {
        return totalViews;
    }

    /**
     * Gets the total amount of reviews the user has received.
     * @return the total reviews.
     */
    public int getTotalReviews() {
        return totalReviews;
    }

    /**
     * Gets the overall reader count of the user from all their stories.
     * @return the total reader count.
     */
    public int getTotalReaders() {
        return totalReaders;
    }

    /**
     * Gets the total follower count of the user.
     * @return the total follow count.
     */
    public int getTotalFollowers() {
        return totalFollowers;
    }

    /**
     * Gets the user's name.
     * @return the user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the user's bio.
     * @return the user's bio.
     */
    public String getBio() {
        return bio;
    }

    /**
     * Gets the URL of the user.
     * @return the full url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets the avatar of the user.
     * @return the avatar.
     */
    public String getAvatar() {
        return avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return totalWords == user.totalWords &&
                totalSeries == user.totalSeries &&
                totalViews == user.totalViews &&
                totalReviews == user.totalReviews &&
                totalReaders == user.totalReaders &&
                totalFollowers == user.totalFollowers &&
                name.equals(user.name) &&
                bio.equals(user.bio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalWords, totalSeries, totalViews, totalReviews, totalReaders, totalFollowers, name, bio);
    }
}
