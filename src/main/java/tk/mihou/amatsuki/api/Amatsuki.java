package tk.mihou.amatsuki.api;

import tk.mihou.amatsuki.api.connection.AmatsukiConnector;
import tk.mihou.amatsuki.api.enums.OrderBy;
import tk.mihou.amatsuki.api.enums.Rankings;
import tk.mihou.amatsuki.entities.ForumThread;
import tk.mihou.amatsuki.entities.latest.LatestUpdatesResult;
import tk.mihou.amatsuki.entities.story.Story;
import tk.mihou.amatsuki.entities.story.lower.StoryResults;
import tk.mihou.amatsuki.entities.user.User;
import tk.mihou.amatsuki.entities.user.lower.UserResults;
import tk.mihou.amatsuki.impl.cache.CacheManager;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Amatsuki {

    private final AmatsukiConnector connector = new AmatsukiConnector();
    private final int defTimeout = 5000;

    /**
     * Sets the user agent for the library, please do not abuse.
     * @param userAgent the user agent to use.
     * @return Amatsuki.
     */
    public Amatsuki setUserAgent(String userAgent){
        connector.setUserAgent(userAgent);
        return this;
    }

    /**
     * Sets the status of the CacheManager whether to enable
     * or to disable the CacheManager (recommended enabled since
     * it is only for stories and users, excluding search).
     *
     * By default, the CacheManager is already enabled to reduce
     * the load on ScribbleHub's side.
     *
     * @param enabled do you want to enable/disable CacheManager?
     * @return Amatsuki.
     */
    public Amatsuki setCache(boolean enabled){
        CacheManager.switchStatus(enabled);
        return this;
    }

    /**
     * Sets the cache lifespan of an entity, in other words, changes
     * the time before the cache is considered invalid and be removed
     * from the cache.
     *
     * The default value is 1 hour because stories don't really update that
     * much unless the creator themselves force update SH's cache which happens
     * a lot which is why we went with one hour.
     *
     * @param lifespan the lifepsan.
     * @param unit the time unit to use.
     * @return Amatsuki
     */
    public Amatsuki setLifespan(int lifespan, TimeUnit unit){
        CacheManager.setLifespan(lifespan, unit);
        return this;
    }

    /**
     * Searches and retrieves a list of users.
     * @param query the keyword to query.
     * @return User.
     */
    public CompletableFuture<List<UserResults>> searchUser(String query) {
        return connector.searchUser(query, defTimeout);
    }

    /**
     * Retrieves the latest topics from ScribbleHub frontpage.
     * @return the latest topics from the frontpage.
     */
    public CompletableFuture<List<ForumThread>> getLatestTopics(){
        return connector.getLatestTopics(defTimeout);
    }

    /**
     * Retrieves the latest topics from ScribbleHub frontpage.
     * @param timeout the timeout in millis.
     * @return the latest topics from the frontpage.
     */
    public CompletableFuture<List<ForumThread>> getLatestTopics(int timeout){
        return connector.getLatestTopics(timeout);
    }

    /**
     * Searches and retrieves a list of stories.
     * @param query the keyword to query.
     * @return Story.
     */
    public CompletableFuture<List<StoryResults>> searchStory(String query) {
        return connector.searchStory(query, defTimeout);
    }

    /**
     * Retrieves the current latest updates on ScribbleHub frontpage.
     * @return List of LatestUpdatesResults
     */
    public CompletableFuture<List<LatestUpdatesResult>> getLatestUpdates(){
        return connector.getLatestUpdates(defTimeout);
    }

    /**
     * Retrieves the current latest updates on ScribbleHub frontpage.
     * @param timeout The time limit (millis).
     * @return List of LatestUpdatesResults
     */
    public CompletableFuture<List<LatestUpdatesResult>> getLatestUpdates(int timeout){
        return connector.getLatestUpdates(timeout);
    }

    /**
     * Retrieves the current latest series on ScribbleHub frontpage.
     * @return List<StoryResults>
     */
    public CompletableFuture<List<StoryResults>> getLatestSeries(){
        return connector.getLatestSeries(defTimeout);
    }

    /**
     * Retrieves the current latest series on ScribbleHub frontpage.
     * @param timeout The time limit (millis).
     * @return List<StoryResults>
     */
    public CompletableFuture<List<StoryResults>> getLatestSeries(int timeout){
        return connector.getLatestSeries(timeout);
    }

    /**
     * Retrieves the current trending on ScribbleHub.
     * @return List<StoryResults>
     */
    public CompletableFuture<List<StoryResults>> getTrending(){
        return connector.getRanking(Rankings.RISING, OrderBy.DAILY, defTimeout);
    }

    /**
     * Retrieves the current trending on ScribbleHub.
     * @param order The time order of the ranking.
     * @return List<StoryResults>
     */
    public CompletableFuture<List<StoryResults>> getTrending(OrderBy order){
        return connector.getRanking(Rankings.RISING, order, defTimeout);
    }


    /**
     * Retrieves the current trending on ScribbleHub.
     * @param order The time order of the ranking.
     * @param timeout The time limit (millis).
     * @return List<StoryResults>
     */
    public CompletableFuture<List<StoryResults>> getTrending(OrderBy order, int timeout){
        return connector.getRanking(Rankings.RISING, order, timeout);
    }

    /**
     * Retrieves the current trending on ScribbleHub.
     * @param timeout The time limit (millis).
     * @return List<StoryResults>
     */
    public CompletableFuture<List<StoryResults>> getTrending(int timeout){
        return connector.getRanking(Rankings.RISING, OrderBy.DAILY, timeout);
    }

    /**
     * Retrieves a certain specified ranking from ScribbleHub.
     * @return List<StoryResults>
     */
    public CompletableFuture<List<StoryResults>> getCertainRankings(Rankings ranking){
        return connector.getRanking(ranking, OrderBy.DAILY, defTimeout);
    }

    /**
     * Retrieves a certain specified ranking from ScribbleHub in a specific time order.
     * @param order The time order of the ranking.
     * @return List<StoryResults>
     */
    public CompletableFuture<List<StoryResults>> getCertainRankings(Rankings ranking, OrderBy order){
        return connector.getRanking(ranking, order, defTimeout);
    }

    /**
     * Retrieves a certain specified ranking from ScribbleHub.
     * @param timeout The time limit (millis).
     * @return List<StoryResults>
     */
    public CompletableFuture<List<StoryResults>> getCertainRankings(Rankings ranking, int timeout){
        return connector.getRanking(ranking, OrderBy.DAILY, timeout);
    }

    /**
     * Retrieves a certain specified ranking from ScribbleHub in a specific time order.
     * @param timeout The time limit (millis).
     * @param order The time order of the ranking.
     * @return List<StoryResults>
     */
    public CompletableFuture<List<StoryResults>> getCertainRankings(Rankings ranking, OrderBy order, int timeout){
        return connector.getRanking(ranking, order, timeout);
    }

    /**
     * Gets a story using the URL.
     * @param url the url to collect from.
     * @return Story.
     */
    public CompletableFuture<Story> getStoryFromUrl(String url) {
        if(CacheManager.enabled.get()){
            if(CacheManager.isCached(url)){
                    Story result = CacheManager.getCache(Story.class, url);
                    if(result != null) {
                        return CompletableFuture.supplyAsync(() -> result);
                    }
            }
        }

        return connector.getStoryByUrl(url, defTimeout);
    }

    /**
     * Gets a user using the URL.
     * @param url the URL to collect from.
     * @return User.
     */
    public CompletableFuture<User> getUserFromUrl(String url) {
        if(CacheManager.enabled.get()){
            if(CacheManager.isCached(url)){
                User result = CacheManager.getCache(User.class, url);
                if(result != null){
                    return CompletableFuture.supplyAsync(() -> result);
                }
            }
        }

        return connector.getUserFromUrl(url, defTimeout);
    }

    /**
     * Searches and retrieves a list of stories. timeouts at a specific time.
     * @param query the keyword to query.
     * @param timeout the time limit. (millis)
     * @return Story.
     */
    public CompletableFuture<List<StoryResults>> searchStory(String query, int timeout) {
        return connector.searchStory(query, timeout);
    }

    /**
     * Searches and retrieves a list of users. timeouts at a specific time.
     * @param query the keyword to query.
     * @param timeout the time limit. (millis)
     * @return User
     */
    public CompletableFuture<List<UserResults>> searchUser(String query, int timeout) {
        return connector.searchUser(query, timeout);
    }

    /**
     * Gets a story using the URL with a specified timeout (millis).
     * @param url the url to collect from.
     * @param timeout the time limit. (millis)
     * @return Story.
     */
    public CompletableFuture<Story> getStoryFromUrl(String url, int timeout) {
        if(CacheManager.enabled.get()){
            if(CacheManager.isCached(url)){
                Story result = CacheManager.getCache(Story.class, url);
                if(result != null) {
                    return CompletableFuture.supplyAsync(() -> result);
                }
            }
        }

        return connector.getStoryByUrl(url, timeout);
    }

    /**
     * Gets a user using the URL with a specified timeout (millis).
     * @param url the URL to collect from.
     * @param timeout the time limit. (millis)
     * @return User.
     */
    public CompletableFuture<User> getUserFromUrl(String url, int timeout) {
        if(CacheManager.enabled.get()){
            if(CacheManager.isCached(url)){
                User result = CacheManager.getCache(User.class, url);
                if(result != null){
                    return CompletableFuture.supplyAsync(() -> result);
                }
            }
        }

        return connector.getUserFromUrl(url, timeout);
    }
}
