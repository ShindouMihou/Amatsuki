package tk.mihou.amatsuki.api.connection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import tk.mihou.amatsuki.entities.frontpage.FrontpagePanel;
import tk.mihou.amatsuki.entities.frontpage.FrontpagePanelBuilder;
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
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AmatsukiConnector {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private String userAgent = "Amatsuki-library/1.1 (Language=Java/1.8)";

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

    public CompletableFuture<Optional<List<UserResults>>> searchUser(String query, int timeout){
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
                return collection.isEmpty() ? Optional.empty() : Optional.of(collection);
            } catch (IOException ignore) {
            }
            return Optional.empty();
        });
    }

    public CompletableFuture<List<FrontpagePanel>> getTrending(int timeout){
        return CompletableFuture.supplyAsync(() -> {
            try {
                Document doc = Jsoup.connect("https://www.scribblehub.com/")
                        .userAgent(userAgent)
                        .timeout(timeout).get();
                List<FrontpagePanel> panels = new ArrayList<>();
                doc.getElementsByClass("wi_fic_wrap slider")
                        .next().first().getElementsByClass("new-novels-carousel_main")
                        .first().getElementsByClass("new-novels-carousel_sub").first().getElementsByClass("novel_carousel_img")
                        .forEach(element -> {
                            FrontpagePanelBuilder builder = new FrontpagePanelBuilder();
                            builder.setStoryURL(element.getElementsByTag("a").first().attr("href"));
                            builder.setThumbnail(element.getElementsByTag("a").first().getElementsByTag("img").attr("src"));
                            builder.setStoryName(element.getElementsByClass("centered_novel").attr("title"));
                            panels.add(builder.build());
                        });
                return panels;
            } catch (IOException e) {
                Logger.getLogger("Amatsuki").log(Level.SEVERE, "Amatsuki: https://scribblehub.com returned: " + e.getMessage());
            }
            return null;
        }, executorService);
    }

    public CompletableFuture<List<FrontpagePanel>> getLatestSeries(int timeout){
        return CompletableFuture.supplyAsync(() -> {
            try {
                Document doc = Jsoup.connect("https://www.scribblehub.com/")
                        .userAgent(userAgent)
                        .timeout(timeout).get();
                List<FrontpagePanel> panels = new ArrayList<>();
                doc.getElementsByClass("wi_fic_wrap slider")
                        .last().getElementsByClass("new-novels-carousel_main")
                        .first().getElementsByClass("new-novels-carousel_sub").first().getElementsByClass("novel_carousel_img")
                        .forEach(element -> {
                            FrontpagePanelBuilder builder = new FrontpagePanelBuilder();
                            builder.setStoryURL(element.getElementsByTag("a").first().attr("href"));
                            builder.setThumbnail(element.getElementsByTag("a").first().getElementsByTag("img").attr("src"));
                            builder.setStoryName(element.getElementsByClass("centered_novel").attr("title"));
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

    public CompletableFuture<Optional<List<StoryResults>>> searchStory(String query, int timeout){
        return CompletableFuture.supplyAsync(() -> {
            try {
                List<StoryResults> stories = new ArrayList<>();
                ArrayList<String> thumbnails = new ArrayList<>();
                Document doc = Jsoup.connect(String.format("https://www.scribblehub.com/?s=%s&post_type=fictionposts", encodeValue(query)))
                        .referrer("https://scribblehub.com")
                        .userAgent(userAgent)
                        .timeout(timeout).get();
                doc.getElementsByClass("search_main_box").forEach(element -> element.getElementsByClass("search_img").forEach(searchImg -> thumbnails.add(searchImg.getElementsByTag("img").attr("src"))));
                IntegerBuilder i = new IntegerBuilder(0);
                doc.getElementsByClass("search_body").forEach(elemental -> elemental.getElementsByClass("search_title").forEach(searchTitle -> {
                    StoryResultBuilder builder = new StoryResultBuilder();
                    builder.setName(searchTitle.getElementsByTag("a").first().ownText());
                    builder.setUrl(searchTitle.getElementsByTag("a").attr("href"));
                    builder.setSynopsis(elemental.ownText() + "..");
                    builder.setThumbnail(thumbnails.get(i.get()));
                    stories.add(builder.build());
                    i.add(1);
                }));
                return stories.isEmpty() ? Optional.empty() : Optional.of(stories);
            } catch (IOException ignore) {
            }
            return Optional.empty();
        }, executorService);
    }

    public CompletableFuture<Optional<Story>> getStoryByUrl(String url, int timeout){
        return CompletableFuture.supplyAsync(() -> {
            try {
                StoryBuilder entity = new StoryBuilder();
                Document doc = Jsoup.connect(url)
                        .referrer("https://scribblehub.com")
                        .userAgent(userAgent)
                        .timeout(timeout).get();
                Elements metaTags = doc.getElementsByTag("meta");
                Elements link = doc.getElementsByTag("link");
                Elements views = doc.getElementsByClass("fic_stats");
                Elements rating = doc.getElementsByClass("fic_rate");
                rating.forEach(element -> {
                    List<String> ratings = element.select("span").first().getElementById("ratefic_user").select("span").eachText();
                    entity.setRatings(Integer.parseInt(ratings.get(4).replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(" ratings", "")));
                    entity.setRating(Double.parseDouble(ratings.get(2)));
                });
                views.forEach(element -> views.select("span.st_item").before("span.mb_stat").eachText().forEach(s -> {
                    if (s.endsWith("Views"))
                        entity.setViews(s.replaceAll(" Views", ""));
                    if (s.endsWith("Favorites"))
                        entity.setFavorites(s.replaceAll(" Favorites", ""));
                    if (s.endsWith("Chapters"))
                        entity.setChapters(Integer.parseInt(s.replaceAll(" Chapters", "")));
                    if (s.endsWith("Chapters/Week"))
                        entity.setChapterPerWeek(Integer.parseInt(s.replaceAll(" Chapters/Week", "")));
                    if (s.endsWith("Readers"))
                        entity.setReaders(Integer.parseInt(s.replaceAll(" Readers", "")));
                }));
                metaTags.forEach((metaTag) -> {
                    String content = metaTag.attr("content");
                    String name = metaTag.attr("property");
                    switch (name) {
                        case "og:description":
                            entity.setSynopsis(Jsoup.parse(content).text());
                            break;
                        case "og:title":
                            entity.setTitle(Jsoup.parse(content).text());
                            break;
                        case "og:image":
                            entity.setImage(content);
                            break;
                    }
                    String twitter = metaTag.attr("name");
                    if ("twitter:creator".equals(twitter)) {
                        entity.setCreator(content);
                    }
                });
                link.forEach((metaTag) -> {
                    String content = metaTag.attr("href");
                    String name = metaTag.attr("rel");
                    if ("canonical".equals(name)) {
                        entity.setUrl(content);
                    }
                });
                return Optional.of(entity.build());
            } catch (IOException ignore) {
            }
            return Optional.empty();
        }, executorService);
    }

    public CompletableFuture<Optional<User>> getUserFromUrl(String url, int timeout){
        return CompletableFuture.supplyAsync(() -> {
            UserBuilder builder = new UserBuilder();
            try {
                Document doc = Jsoup.connect(url)
                        .referrer("https://scribblehub.com")
                        .userAgent(userAgent)
                        .timeout(timeout).get();
                builder.setBio(doc.getElementsByClass("user_bio_profile").text());
                doc.getElementsByClass("site-content-contain profile").forEach(element -> element.getElementsByTag("meta").forEach(meta -> {
                    switch (meta.attr("property")) {
                        case "og:image":
                            builder.setAvatar(meta.attr("content"));
                            break;
                        case "og:description":
                            builder.setName(meta.attr("content").replaceFirst("'s profile.", ""));
                            break;
                    }
                }));
                doc.getElementsByClass("table_pro_overview").forEach(table -> table.select("tr").forEach(row -> {
                    Elements th = row.select("th");
                    Elements td = row.select("td");
                    for (int i = 0; i < th.size(); i++) {
                        switch (th.get(i).ownText()) {
                            case "Series:":
                                builder.setTotalSeries(Integer.parseInt(td.get(i).ownText().replaceAll(",", "")));
                                break;
                            case "Total Words:":
                                builder.setTotalWords(Long.parseLong(td.get(i).ownText().replaceAll(",", "")));
                                break;
                            case "Reviews Received:":
                                builder.setTotalReviews(Integer.parseInt(td.get(i).ownText().replaceAll(",", "")));
                                break;
                            case "Readers:":
                                builder.setTotalReaders(Integer.parseInt(td.get(i).ownText().replaceAll(",", "")));
                                break;
                            case "Followers:":
                                builder.setTotalFollowers(Integer.parseInt(td.get(i).ownText().replaceAll(",", "")));
                                break;
                            case "Total Pageviews:":
                                builder.setTotalViews(Long.parseLong(td.get(i).ownText().replaceAll(",", "")));
                                break;
                        }
                    }
                }));
                builder.setUrl(url);
                return Optional.of(builder.build());
            } catch (IOException ignore) {
            }
            return Optional.empty();
        }, executorService);
    }

    private static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

    static class IntegerBuilder {

        /*
        I wonder what this is... don't ask.
         */

        private int i;
        public IntegerBuilder(int i){
            this.i = i;
        }

        public IntegerBuilder add(int x){
            i = i + x;
            return this;
        }

        public int get(){
            return i;
        }
    }

}
