package tk.mihou.amatsuki.entities.user.lower;

import tk.mihou.amatsuki.api.Amatsuki;
import tk.mihou.amatsuki.entities.user.User;

import java.util.Objects;

public class UserResults {

    private String name;
    private String url;
    private String avatar;

    public UserResults(String name, String url, String avatar) {
        this.name = name;
        this.url = url;
        this.avatar = avatar;
    }

    /**
     * Gets the user's name.
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the link to the user.
     * @return the URL link.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets the link avatar of the user, may be super small resolution as SH search results hand such.
     * @return image link.
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * Transforms a result into a user, uses blind .get() as we already know the URL will give out results.
     * @return User.
     */
    public User transformToUser(){
        return new Amatsuki().getUserFromUrl(url).get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResults userResults = (UserResults) o;
        return name.equals(userResults.name) &&
                url.equals(userResults.url) &&
                Objects.equals(avatar, userResults.avatar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url, avatar);
    }
}
