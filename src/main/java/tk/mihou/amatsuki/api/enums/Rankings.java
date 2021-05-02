package tk.mihou.amatsuki.api.enums;

public enum Rankings {

    RISING(5, "RISING-AMATSUKI"), READERS(4, "READERS-AMATSUKI"),
    POPULARITY(1, "POPULARITY-AMATSUKI"), FAVORITES(2, "FAVORITES-AMATSUKI"), ACTIVITY(3, "ACTIVITY-AMATSUKI");

    private final int location;
    private final String identifier;

    Rankings(int location, String identifier){
        this.location = location;
        this.identifier = identifier;
    }

    public int getLocation(){
        return location;
    }

    public String getIdentifier(){
        return identifier;
    }

}
