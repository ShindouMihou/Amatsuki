package tk.mihou.amatsuki.api.enums;

public enum Rankings {

    RISING(5), READERS(4), POPULARITY(1), FAVORITES(2), ACTIVITY(3);

    private int location;

    Rankings(int location){
        this.location = location;
    }

    public int getLocation(){
        return location;
    }

}
