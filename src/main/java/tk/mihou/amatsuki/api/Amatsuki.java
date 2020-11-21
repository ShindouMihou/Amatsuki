package tk.mihou.amatsuki.api;

import tk.mihou.amatsuki.api.connection.AmatsukiConnector;
import tk.mihou.amatsuki.entities.story.Story;
import tk.mihou.amatsuki.entities.story.lower.StoryResults;
import tk.mihou.amatsuki.entities.user.User;
import tk.mihou.amatsuki.entities.user.lower.UserResults;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class Amatsuki {

    private AmatsukiConnector connector = new AmatsukiConnector();
    private int defTimeout = 30000;

    /**
     * Searches and retrieves a list of users.
     * @param query the keyword to query.
     * @return User.
     */
    public CompletableFuture<Optional<List<UserResults>>> searchUser(String query) {
        return connector.searchUser(query, defTimeout);
    }

    /**
     * Searches and retrieves a list of stories.
     * @param query the keyword to query.
     * @return Story.
     */
    public CompletableFuture<Optional<List<StoryResults>>> searchStory(String query) {
        return connector.searchStory(query, defTimeout);
    }

    /**
     * Gets a story using the URL.
     * @param url the url to collect from.
     * @return Story.
     */
    public CompletableFuture<Optional<Story>> getStoryFromUrl(String url) {
        return connector.getStoryByUrl(url, defTimeout);
    }

    /**
     * Gets a user using the URL.
     * @param url the URL to collect from.
     * @return User.
     */
    public CompletableFuture<Optional<User>> getUserFromUrl(String url) {
        return connector.getUserFromUrl(url, defTimeout);
    }

    /**
     * Searches and retrieves a list of stories. timeouts at a specific time.
     * @param query the keyword to query.
     * @param timeout the time limit. (millis)
     * @return Story.
     */
    public CompletableFuture<Optional<List<StoryResults>>> searchStory(String query, int timeout) {
        return connector.searchStory(query, timeout);
    }

    /**
     * Searches and retrieves a list of users. timeouts at a specific time.
     * @param query the keyword to query.
     * @param timeout the time limit. (millis)
     * @return User
     */
    public CompletableFuture<Optional<List<UserResults>>> searchUser(String query, int timeout) {
        return connector.searchUser(query, timeout);
    }

    /**
     * Gets a story using the URL with a specified timeout (millis).
     * @param url the url to collect from.
     * @param timeout the time limit. (millis)
     * @return Story.
     */
    public CompletableFuture<Optional<Story>> getStoryFromUrl(String url, int timeout) {
        return connector.getStoryByUrl(url, timeout);
    }

    /**
     * Gets a user using the URL with a specified timeout (millis).
     * @param url the URL to collect from.
     * @param timeout the time limit. (millis)
     * @return User.
     */
    public CompletableFuture<Optional<User>> getUserFromUrl(String url, int timeout) {
        return connector.getUserFromUrl(url, timeout);
    }
}
