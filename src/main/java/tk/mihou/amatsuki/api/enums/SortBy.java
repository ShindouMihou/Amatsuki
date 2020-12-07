package tk.mihou.amatsuki.api.enums;

public enum SortBy {

    PAGEVIEWS("pageviews"), RATINGS("ratings"), CHAPTERS_PER_WEEK("frequency"), CHAPTERS("chapters"),
    DATE_ADDED("dateadded"), FAVORITES("favorites"), LAST_UPDATE("lastchpdate"), NUMBER_OF_RATINGS("numofrate"),
    PAGES("pages"), READERS("readers"), REVIEWS("reviews"), TOTAL_WORDS("totalwords");

    private String value;

    SortBy(String value){
        this.value = value;
    }

    /**
     * Returns the value of the Sorting.
     * May be useless to the ordinary user.
     * @return the value used for Search Finder.
     */
    public String getValue(){
        return value;
    }

}
