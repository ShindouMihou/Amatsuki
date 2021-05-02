package tk.mihou.amatsuki.api.enums;

public enum AmatsukiNames {

    LATEST_SERIES("LATEST-SERIES-1"), LATEST_UPDATES("LATEST-UPDATES-1"), STORY_SEARCH("%s-AMATSUKI-SEARCH-STORY"), USER_SEARCH("%s-AMATSUKI-SEARCH-USER"),
    RANKINGS("%s-%d"), SERIES_FINDER("SERIES-FINDER-%s");

    public String format;

    AmatsukiNames(String format){
        this.format = format;
    }

    public String getFormat(){
        return format;
    }

}
