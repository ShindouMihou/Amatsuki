package tk.mihou.amatsuki.entities.user.lower;

public class UserResultBuilder {

    private String user;
    private String avatar;
    private String link;

    public void setUser(String user) {
        this.user = user;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public UserResults build(){
        return new UserResults(user, link, avatar);
    }
}
