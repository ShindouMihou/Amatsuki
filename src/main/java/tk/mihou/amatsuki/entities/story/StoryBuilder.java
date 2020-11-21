package tk.mihou.amatsuki.entities.story;

public class StoryBuilder {

    private String title, synopsis, url, image, creator, views;
    private Double rating;
    private long favorites;
    private int chapters, chapterPerWeek, ratings, readers;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public void setFavorites(String favorites) {
        this.favorites = Long.parseLong(favorites);
    }

    public void setChapters(int chapters) {
        this.chapters = chapters;
    }

    public void setChapterPerWeek(int chapterPerWeek) {
        this.chapterPerWeek = chapterPerWeek;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public void setReaders(int readers) {
        this.readers = readers;
    }

    public Story build(){
        return new Story(title, synopsis, url, image, creator, rating, views, favorites, chapters, chapterPerWeek, ratings, readers);
    }

}
