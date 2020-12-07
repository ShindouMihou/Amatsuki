package tk.mihou.amatsuki.api.enums;

public enum StoryStatus {

    ALL("all"), COMPLETED("completed"), ONGOING("ongoing"), HIATUS("hiatus");

    private String value;

    StoryStatus(String value){
        this.value = value;
    }

    /**
     * Used to retrieve the value for Search Finder.
     * May be useless to the ordinary user.
     * @return the value used in search finder.
     */
    public String getValue(){
        return value;
    }

}
