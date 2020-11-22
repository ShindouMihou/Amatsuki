package tk.mihou.amatsuki.api.enums;

public enum OrderBy {

    DAILY(1), WEEKLY(2), MONTHLY(3), ALLTIME(4);

    private int location;

    OrderBy(int location){
        this.location = location;
    }

    public int getLocation(){
        return location;
    }

}
