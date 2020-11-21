package tk.mihou.amatsuki.api.connection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import tk.mihou.amatsuki.entities.story.Story;
import tk.mihou.amatsuki.entities.story.StoryBuilder;
import tk.mihou.amatsuki.entities.story.lower.StoryResultBuilder;
import tk.mihou.amatsuki.entities.story.lower.StoryResults;
import tk.mihou.amatsuki.entities.user.lower.UserResults;
import tk.mihou.amatsuki.entities.user.lower.UserResultBuilder;
import tk.mihou.amatsuki.entities.user.User;
import tk.mihou.amatsuki.entities.user.UserBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AmatsukiConnector {

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

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

    public CompletableFuture<Optional<List<UserResults>>> searchUser(String query, int timeout){
        return CompletableFuture.supplyAsync(() -> {
            try {
                List<UserResults> collection = new ArrayList<>();
                Document doc = Jsoup.connect(String.format("https://www.scribblehub.com/?s=%s&post_type=fictionposts", query))
                        .referrer("https://scribblehub.com").timeout(timeout).get();
                doc.getElementsByClass("sb_box search").forEach(element -> element.getElementsByClass("s_user_link").forEach(resultA -> resultA.getElementsByClass("s_user_results").forEach(results -> {
                    UserResultBuilder builder = new UserResultBuilder();
                    Element e = results.getElementsByClass("sur_image").first().getElementsByTag("img").first();
                    builder.setAvatar(e.attr("src"));
                    builder.setUser(e.attr("alt"));
                    builder.setLink(resultA.attr("href"));
                    collection.add(builder.build());
                })));
                return collection.isEmpty() ? Optional.empty() : Optional.of(collection);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Optional.empty();
        });
    }

    public CompletableFuture<Optional<List<StoryResults>>> searchStory(String query, int timeout){
        return CompletableFuture.supplyAsync(() -> {
            try {
                List<StoryResults> stories = new ArrayList<>();
                ArrayList<String> thumbnails = new ArrayList<>();
                Document doc = Jsoup.connect(String.format("https://www.scribblehub.com/?s=%s&post_type=fictionposts", query))
                        .referrer("https://scribblehub.com").timeout(timeout).get();
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
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Optional.empty();
        }, executorService);
    }

    public CompletableFuture<Optional<Story>> getStoryByUrl(String url, int timeout){
        return CompletableFuture.supplyAsync(() -> {
            try {
                StoryBuilder entity = new StoryBuilder();
                Document doc = Jsoup.connect(url)
                        .referrer("https://scribblehub.com").timeout(timeout).get();
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
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return Optional.empty();
        }, executorService);
    }

    public CompletableFuture<Optional<User>> getUserFromUrl(String url, int timeout){
        return CompletableFuture.supplyAsync(() -> {
            UserBuilder builder = new UserBuilder();
            try {
                Document doc = Jsoup.connect(url)
                        .referrer("https://scribblehub.com").timeout(timeout).get();
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
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Optional.empty();
        }, executorService);
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
