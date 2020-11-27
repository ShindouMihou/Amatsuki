package tk.mihou.amatsuki.api.connection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import tk.mihou.amatsuki.api.enums.OrderBy;
import tk.mihou.amatsuki.api.enums.Rankings;
import tk.mihou.amatsuki.entities.latest.LatestUpdatesBuilder;
import tk.mihou.amatsuki.entities.latest.LatestUpdatesResult;
import tk.mihou.amatsuki.entities.story.Story;
import tk.mihou.amatsuki.entities.story.StoryBuilder;
import tk.mihou.amatsuki.entities.story.lower.StoryResultBuilder;
import tk.mihou.amatsuki.entities.story.lower.StoryResults;
import tk.mihou.amatsuki.entities.user.lower.UserResults;
import tk.mihou.amatsuki.entities.user.lower.UserResultBuilder;
import tk.mihou.amatsuki.entities.user.User;
import tk.mihou.amatsuki.entities.user.UserBuilder;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AmatsukiConnector {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private String userAgent = "Amatsuki-library/1.2.4r3 (Language=Java/1.8)";

    /*
    - Amatsuki Connector, the base connector for all.
    v 1.0.0-SNAPSHOT.

    The methods used here are not at its best, but they function as expected which is what I want.
    If you wish to improve upon this, please do so.

    Search Story
    - The method here, searches first for the thumbnails then saves them on an ArrayList (not static) for collecting later
    as the thumbnails are stored on a different divider from the other stuff.
    - After storing the thumbnails, the short information of the stories will then be collected and stored on a class
    called ShortStory which is a summary version of Story, it has a method to transform itself into a Story class.

    Search User.
    - The method here collects all the results from the user results (on the side of when you search for a story/user on SH).
    - It is limited as one would expect, but it does the job right.
     */

    /**
     * Modifies the user-agent of the client, can be anything but I recommend not abusing, as well as using the right
     * practices.
     * @param userAgent the user agent.
     */
    public void setUserAgent(String userAgent){
        this.userAgent = userAgent;
    }

    public CompletableFuture<List<UserResults>> searchUser(String query, int timeout){
        return CompletableFuture.supplyAsync(() -> {
            try {
                List<UserResults> collection = new ArrayList<>();
                Document doc = Jsoup.connect(String.format("https://www.scribblehub.com/?s=%s&post_type=fictionposts", encodeValue(query)))
                        .referrer("https://scribblehub.com")
                        .userAgent(userAgent)
                        .timeout(timeout).get();
                doc.getElementsByClass("sb_box search").forEach(element -> element.getElementsByClass("s_user_link").forEach(resultA -> resultA.getElementsByClass("s_user_results").forEach(results -> {
                    UserResultBuilder builder = new UserResultBuilder();
                    Element e = results.getElementsByClass("sur_image").first().getElementsByTag("img").first();
                    builder.setAvatar(e.attr("src"));
                    builder.setUser(e.attr("alt"));
                    builder.setLink(resultA.attr("href"));
                    collection.add(builder.build());
                })));
                return collection;
            } catch (IOException ignore) {
            }
            return null;
        });
    }

    public CompletableFuture<List<StoryResults>> getRanking(Rankings ranking, OrderBy order, int timeout){
        return CompletableFuture.supplyAsync(() -> {
            try {
                Document doc = Jsoup.connect(String.format("https://www.scribblehub.com/series-ranking/?sort=%d&order=%d", ranking.getLocation(), order.getLocation()))
                        .userAgent(userAgent)
                        .timeout(timeout).get();
                List<StoryResults> panels = new ArrayList<>();
                doc.getElementsByClass("search_main_box").forEach(element -> {
                    StoryResultBuilder builder = new StoryResultBuilder();

                    // Gets the thumbnail and rating.
                    builder.setThumbnail(element.getElementsByClass("search_img").select("img").attr("src"));
                    builder.setRating(Double.parseDouble(element.getElementsByClass("search_img").first().getElementsByClass("search_ratings").first().ownText().replaceAll("[^\\d.]", "")));

                    // Get the extra details.
                    Element body = element.getElementsByClass("search_body").first();
                    builder.setName(body.getElementsByClass("search_title").select("a").first().text());
                    builder.setUrl(body.getElementsByClass("search_title").select("a").attr("href"));

                    // Retrieve both synopsis.
                    StringBuilder str = new StringBuilder();
                    builder.setShortSynopsis(body.ownText());
                    str.append(body.ownText());
                    body.select("span.testhide").eachText().forEach(s -> str.append("\n").append(s));
                    builder.setFullSynopsis(str.toString().replaceAll("<<less", ""));

                    // Retrieve all the genres.
                    List<String> genres = new ArrayList<>();
                    body.getElementsByClass("search_genre").first().getElementsByTag("a").forEach(element1 -> genres.add(element1.ownText()));
                    builder.setGenres(genres);

                    // Retrieve all statistics.
                    Element stats = body.getElementsByClass("search_stats").first();
                    builder.setViews(stats.getElementsByTag("span").first().ownText().replaceAll("[^\\d.km]", ""));
                    builder.setFavorites(Long.parseLong(stats.getElementsByTag("span").first().nextElementSibling().ownText().replaceAll("[^\\d]", "")));
                    builder.setChapters(Integer.parseInt(stats.getElementsByTag("span").first().nextElementSibling().nextElementSibling().ownText().replaceAll("[^\\d]", "")));
                    builder.setChw(Integer.parseInt(stats.getElementsByTag("span").first().nextElementSibling().nextElementSibling().nextElementSibling().ownText().replaceAll("[^\\d]", "")));
                    builder.setReaders(Integer.parseInt(stats.getElementsByTag("span").first().nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling().ownText().replaceAll("[^\\d]", "")));
                    builder.setReviews(Integer.parseInt(stats.getElementsByTag("span").first().nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling()
                            .nextElementSibling().ownText().replaceAll("[^\\d]", "")));
                    builder.setWord(stats.getElementsByTag("span").first().nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling()
                            .nextElementSibling().nextElementSibling().ownText().replaceAll("[^\\d.km]", ""));
                    builder.setLastUpdated(stats.getElementsByTag("span").first().nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling()
                            .nextElementSibling().nextElementSibling().nextElementSibling().ownText());
                    builder.setCreator(stats.getElementsByTag("span").last().getElementsByTag("span").first().getElementsByTag("a").first().ownText());
                    builder.setAuthorURL(stats.getElementsByTag("span").last().getElementsByTag("span").first().getElementsByTag("a").first().attr("href"));
                    panels.add(builder.build());
                });
                return panels;
            } catch (IOException e) {
                Logger.getLogger("Amatsuki").log(Level.SEVERE, "Amatsuki: https://scribblehub.com returned: " + e.getMessage());
            }
            return null;
        }, executorService);
    }

    public CompletableFuture<List<StoryResults>> getLatestSeries(int timeout){
        return CompletableFuture.supplyAsync(() -> {
            try {
                Document doc = Jsoup.connect("https://www.scribblehub.com/latest-series/")
                        .userAgent(userAgent)
                        .timeout(timeout).get();
                List<StoryResults> panels = new ArrayList<>();
                doc.getElementsByClass("search_main_box").forEach(element -> {
                    StoryResultBuilder builder = new StoryResultBuilder();

                    // Gets the thumbnail and rating.
                    builder.setThumbnail(element.getElementsByClass("search_img").select("img").attr("src"));
                    builder.setRating(Double.parseDouble(element.getElementsByClass("search_img").first().getElementsByClass("search_ratings").first().ownText().replaceAll("[^\\d.]", "")));

                    // Get the extra details.
                    Element body = element.getElementsByClass("search_body").first();
                    builder.setName(body.getElementsByClass("search_title").select("a").first().text());
                    builder.setUrl(body.getElementsByClass("search_title").select("a").attr("href"));

                    // Retrieve both synopsis.
                    StringBuilder str = new StringBuilder();
                    builder.setShortSynopsis(body.ownText());
                    str.append(body.ownText());
                    body.select("span.testhide").eachText().forEach(s -> str.append("\n").append(s));
                    builder.setFullSynopsis(str.toString().replaceAll("<<less", ""));

                    // Retrieve all the genres.
                    List<String> genres = new ArrayList<>();
                    body.getElementsByClass("search_genre").first().getElementsByTag("a").forEach(element1 -> genres.add(element1.ownText()));
                    builder.setGenres(genres);

                    // Retrieve all statistics.
                    Element stats = body.getElementsByClass("search_stats").first();
                    builder.setViews(stats.getElementsByTag("span").first().ownText().replaceAll("[^\\d.km]", ""));
                    builder.setFavorites(Long.parseLong(stats.getElementsByTag("span").first().nextElementSibling().ownText().replaceAll("[^\\d]", "")));
                    builder.setChapters(Integer.parseInt(stats.getElementsByTag("span").first().nextElementSibling().nextElementSibling().ownText().replaceAll("[^\\d]", "")));
                    builder.setChw(Integer.parseInt(stats.getElementsByTag("span").first().nextElementSibling().nextElementSibling().nextElementSibling().ownText().replaceAll("[^\\d]", "")));
                    builder.setReaders(Integer.parseInt(stats.getElementsByTag("span").first().nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling().ownText().replaceAll("[^\\d]", "")));
                    builder.setReviews(Integer.parseInt(stats.getElementsByTag("span").first().nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling()
                            .nextElementSibling().ownText().replaceAll("[^\\d]", "")));
                    builder.setWord(stats.getElementsByTag("span").first().nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling()
                            .nextElementSibling().nextElementSibling().ownText().replaceAll("[^\\d.km]", ""));
                    builder.setLastUpdated(stats.getElementsByTag("span").first().nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling()
                            .nextElementSibling().nextElementSibling().nextElementSibling().ownText());
                    builder.setCreator(stats.getElementsByTag("span").last().getElementsByTag("span").first().getElementsByTag("a").first().ownText());
                    builder.setAuthorURL(stats.getElementsByTag("span").last().getElementsByTag("span").first().getElementsByTag("a").first().attr("href"));
                    panels.add(builder.build());
                });
                return panels;
            } catch (IOException e) {
                Logger.getLogger("Amatsuki").log(Level.SEVERE, "Amatsuki: https://scribblehub.com returned: " + e.getMessage());
            }
            return null;
        }, executorService);
    }

    /**
     * Returns the 10 results from latest updates, does not use Optional as this is guranteed to have a value.
     * @param timeout The timeout before the connection closes (in millis).
     * @return List<LatestUpdateResults>
     */
    public CompletableFuture<List<LatestUpdatesResult>> getLatestUpdates(int timeout){
        return CompletableFuture.supplyAsync(() -> {
            List<LatestUpdatesResult> results = new ArrayList<>();
            try {
                Document doc = Jsoup.connect("https://www.scribblehub.com/")
                        .userAgent(userAgent)
                        .timeout(timeout).get();
                doc.getElementsByClass("wi-editfic_l-content_main").first().getElementsByClass("latest_releases_main")
                .first().getElementsByClass("mr_fictable").first().getElementsByTag("tbody").first().getElementsByTag("tr").forEach(element -> {
                    Element td = element.getElementsByTag("td").first();
                    LatestUpdatesBuilder builder = new LatestUpdatesBuilder();
                    // Sets the thumbnail.
                    builder.setThumbnail(td.getElementsByClass("m_img_fic").first().getElementsByTag("img").first().attr("src"));
                    Element body = element.getElementsByClass("search_body ficmain").first();
                    // Sets the story details.
                    builder.setStoryURL(body.getElementsByTag("span").first().getElementsByClass("fp_title main").first().attr("href"));
                    builder.setStoryName(body.getElementsByTag("span").first().getElementsByClass("fp_title main").first().attr("title"));
                    // Sets the genres.
                    List<String> genres = new ArrayList<>();
                    body.getElementsByTag("div").first().getElementsByClass("fic_genre search ahmain").forEach(element1 -> genres.add(element1.ownText()));
                    builder.setGenres(genres);
                    // Set the chapter details.
                    builder.setChapterURL(body.getElementsByTag("div").next().first().getElementsByTag("a").attr("href"));
                    builder.setChapterTitle(body.getElementsByTag("div").next().first().getElementsByTag("a").first().ownText());
                    // Sets the author details.
                    builder.setAuthorName(body.getElementsByTag("div").last().getElementsByClass("fp_authorname").first().ownText());
                    builder.setAuthorURL(body.getElementsByTag("div").last().getElementsByTag("a").attr("href"));
                    // Last update.
                    builder.setLastUpdate(body.getElementsByTag("div").last().ownText().replaceFirst(", ", ""));
                    results.add(builder.build());
                });
                return results;
            } catch (IOException e) {
                Logger.getLogger("Amatsuki").log(Level.SEVERE, "Amatsuki: https://scribblehub.com returned: " + e.getMessage());
            }
            return null;
        }, executorService);
    }

    public CompletableFuture<List<StoryResults>> searchStory(String query, int timeout){
        return CompletableFuture.supplyAsync(() -> {
            try {
                List<StoryResults> stories = new ArrayList<>();
                Document doc = Jsoup.connect(String.format("https://www.scribblehub.com/?s=%s&post_type=fictionposts", encodeValue(query)))
                        .referrer("https://scribblehub.com")
                        .userAgent(userAgent)
                        .timeout(timeout).get();
                doc.getElementsByClass("search_main_box").forEach(element -> {
                    StoryResultBuilder builder = new StoryResultBuilder();

                    // Gets the thumbnail and rating.
                    builder.setThumbnail(element.getElementsByClass("search_img").select("img").attr("src"));
                    builder.setRating(Double.parseDouble(element.getElementsByClass("search_img").first().getElementsByClass("search_ratings").first().ownText().replaceAll("[^\\d.]", "")));

                    // Get the extra details.
                    Element body = element.getElementsByClass("search_body").first();
                    builder.setName(body.getElementsByClass("search_title").select("a").first().text());
                    builder.setUrl(body.getElementsByClass("search_title").select("a").attr("href"));

                    // Retrieve both synopsis.
                    StringBuilder str = new StringBuilder();
                    builder.setShortSynopsis(body.ownText());
                    str.append(body.ownText());
                    body.select("span.testhide").eachText().forEach(s -> str.append("\n").append(s));
                    builder.setFullSynopsis(str.toString().replaceAll("<<less", ""));

                    // Retrieve all the genres.
                    List<String> genres = new ArrayList<>();
                    body.getElementsByClass("search_genre").first().getElementsByTag("a").forEach(element1 -> genres.add(element1.ownText()));
                    builder.setGenres(genres);

                    // Retrieve all statistics.
                    Element stats = body.getElementsByClass("search_stats").first();
                    builder.setViews(stats.getElementsByTag("span").first().ownText().replaceAll("[^\\d.km]", ""));
                    builder.setFavorites(Long.parseLong(stats.getElementsByTag("span").first().nextElementSibling().ownText().replaceAll("[^\\d]", "")));
                    builder.setChapters(Integer.parseInt(stats.getElementsByTag("span").first().nextElementSibling().nextElementSibling().ownText().replaceAll("[^\\d]", "")));
                    builder.setChw(Integer.parseInt(stats.getElementsByTag("span").first().nextElementSibling().nextElementSibling().nextElementSibling().ownText().replaceAll("[^\\d]", "")));
                    builder.setReaders(Integer.parseInt(stats.getElementsByTag("span").first().nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling().ownText().replaceAll("[^\\d]", "")));
                    builder.setReviews(Integer.parseInt(stats.getElementsByTag("span").first().nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling()
                            .nextElementSibling().ownText().replaceAll("[^\\d]", "")));
                    builder.setWord(stats.getElementsByTag("span").first().nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling()
                            .nextElementSibling().nextElementSibling().ownText().replaceAll("[^\\d.km]", ""));
                    builder.setLastUpdated(stats.getElementsByTag("span").first().nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling()
                            .nextElementSibling().nextElementSibling().nextElementSibling().ownText());
                    builder.setCreator(stats.getElementsByTag("span").last().getElementsByTag("span").first().getElementsByTag("a").first().ownText());
                    builder.setAuthorURL(stats.getElementsByTag("span").last().getElementsByTag("span").first().getElementsByTag("a").first().attr("href"));
                    System.out.println(stats.getElementsByTag("span").last().getElementsByTag("span").first().getElementsByTag("a").first().attr("href"));
                    stories.add(builder.build());
                });
                return stories;
            } catch (IOException ignore) {
            }
            return null;
        }, executorService);
    }

    public CompletableFuture<Story> getStoryByUrl(String url, int timeout){
        return CompletableFuture.supplyAsync(() -> {
            try {
                StoryBuilder entity = new StoryBuilder();
                // Connects to the URL.
                Document doc = Jsoup.connect(url)
                        .referrer("https://scribblehub.com")
                        .userAgent(userAgent)
                        .timeout(timeout).get();

                // Retrieve the synopsis.
                Element details = doc.getElementsByClass("wi_fic_wrap bottom").first().getElementsByClass("wi-fic_l-content fic")
                        .first().getElementsByClass("box_fictionpage details").first().getElementsByClass("fic_row details").first();
                StringBuilder perfectDescription = new StringBuilder();
                details.getElementsByClass("wi_fic_desc").first().getElementsByTag("p").forEach(element -> perfectDescription.append(element.ownText()).append("\n"));
                entity.setSynopsis(perfectDescription.toString());

                // Retrieving the genres.
                List<String> genres = new ArrayList<>();
                details.getElementsByClass("wi_fic_genre").first().getElementsByTag("span").forEach(element -> genres.add(element.getElementsByClass("fic_genre").first().ownText()));

                // Retrieving the tags.
                List<String> tags = new ArrayList<>();
                details.getElementsByClass("wi_fic_showtags").first().getElementsByTag("span").forEach(element -> element.getElementsByTag("a").forEach(element1 -> tags.add(element1.ownText())));

                // Deploying all the data onto the entity.
                entity.setGenres(genres);
                entity.setTags(tags);

                Elements views = doc.getElementsByClass("fic_stats");

                // Transformed all of these to a one-liner, collects the rating statistics.
                entity.setRating(Double.parseDouble(doc.getElementsByClass("fic_rate").select("span").first().getElementsByTag("span").first().text().split(" ")[0]));
                entity.setRatings(Integer.parseInt(doc.getElementsByClass("fic_rate").select("span").first().getElementsByTag("span").first().text().split(" ")[1].replaceAll("[^\\d.]", "")));

                // Transformed all these to a one to two-liner, this part collects all the statistics.
                entity.setViews(views.select("span.st_item").before("span.mb_stat").first().text().replaceAll("[^\\d.km]", ""));
                entity.setFavorites(views.select("span.st_item").before("span.mb_stat").first().nextElementSibling().text().replaceAll("[^\\d]", ""));
                entity.setChapters(Integer.parseInt(views.select("span.st_item").before("span.mb_stat").first().nextElementSibling().nextElementSibling().text().replaceAll("[^\\d]", "")));
                entity.setChapterPerWeek(Integer.parseInt(views.select("span.st_item").before("span.mb_stat").first().nextElementSibling().nextElementSibling()
                        .nextElementSibling().text().replaceAll("[^\\d]", "")));
                entity.setReaders(Integer.parseInt(views.select("span.st_item").before("span.mb_stat").last().text().replaceAll("[^\\d]", "")));

                // Retrieving basic information from meta tags.
                entity.setTitle(doc.select("meta[name='twitter:title']").attr("content"));
                entity.setImage(doc.select("meta[name='twitter:image']").attr("content"));
                entity.setCreator(doc.select("meta[name='twitter:creator']").attr("content"));

                // Retrieve SID (UID but for series).
                entity.setSID(Integer.parseInt(doc.select("div.site").first().getElementsByClass("site-content-contain").first().select("input[id='mypostid']").attr("value")));

                // I wonder why I was getting the URL when there is already a URL provided?
                entity.setUrl(url);

                return entity.build();
            } catch (IOException ignore) {
            }
            return null;
        }, executorService);
    }

    public CompletableFuture<User> getUserFromUrl(String url, int timeout){
        return CompletableFuture.supplyAsync(() -> {
            UserBuilder builder = new UserBuilder();
            try {
                Document doc = Jsoup.connect(url)
                        .referrer("https://scribblehub.com")
                        .userAgent(userAgent)
                        .timeout(timeout).get();
                // Collects the perfect bio, since whitespace is killed by Jsoup, we simply add '\n' after every <p>.
                StringBuilder bio = new StringBuilder();
                doc.getElementsByClass("user_bio_profile").first().getElementsByTag("p").forEach(element ->
                        bio.append(element.text()).append("\n"));
                builder.setBio(bio.toString());

                // Transformed into a one-liner, collects the meta tags information.
                Element metad = doc.getElementsByClass("site-content-contain profile").first();
                builder.setAvatar(metad.select("meta[property='og:image']").attr("content"));
                builder.setName(metad.select("meta[property='og:description']").attr("content").split("'s")[0]);

                // Collects the basic information of the author, date, birthday and etc.
                Element tableOne = doc.getElementsByClass("table_pro_overview").first();
                builder.setLastActive(tableOne.select("tr").first().select("td").text());
                builder.setBirthday(tableOne.select("tr").first().nextElementSibling().select("td").text());
                builder.setGender(tableOne.select("tr").first().nextElementSibling().nextElementSibling().select("td").text());
                builder.setLocation(tableOne.select("tr").first().nextElementSibling().nextElementSibling().nextElementSibling().select("td").text());
                builder.setHomepage(tableOne.select("tr").last().select("td").select("a").text());

                // Collects the author statistics, improved as of v1.1.5
                Element table = doc.getElementsByClass("table_pro_overview").last();
                // Sets the total series.
                builder.setTotalSeries(Integer.parseInt(table.select("tr").first().select("td").text()));
                // Sets the total words.
                builder.setTotalWords(Long.parseLong(table.select("tr").first().nextElementSibling().select("td").text().replaceAll("[^\\d]", "")));
                // Sets the total views.
                builder.setTotalViews(Long.parseLong(table.select("tr").first().nextElementSibling().nextElementSibling().select("td").text().replaceAll("[^\\d]", "")));
                // Sets the total reviews received.
                builder.setTotalReviews(Integer.parseInt(table.select("tr").first().nextElementSibling().nextElementSibling()
                        .nextElementSibling().select("td").text().replaceAll("[^\\d]", "")));
                // Sets the total readers.
                builder.setTotalReaders(Integer.parseInt(table.select("tr").first().nextElementSibling().nextElementSibling()
                        .nextElementSibling().nextElementSibling().select("td").text().replaceAll("[^\\d]", "")));
                // Sets the total followers.
                builder.setTotalFollowers(Integer.parseInt(table.select("tr").last().select("td").text().replaceAll("[^\\d]", "")));

                // Retrieve UID.
                builder.setUID(Integer.parseInt(metad.select("input[name='authorid']").first().attr("value")));
                // Sets the URL.
                builder.setUrl(url);
                return builder.build();
            } catch (IOException ignore) {
            }
            return null;
        }, executorService);
    }

    private static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }
}
