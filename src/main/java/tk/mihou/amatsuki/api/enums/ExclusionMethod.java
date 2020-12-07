package tk.mihou.amatsuki.api.enums;

public enum ExclusionMethod {

    AND("and"), OR("or");

    private String value;

    /**
     * The method used for excluding or including stories, whether it'd be AND or OR.
     * @param value the value used in Search Finder.
     */
    ExclusionMethod(String value){
        this.value = value;
    }

    /**
     * Used to tip the website that we wish to use AND or OR to exclude/include several options.
     * This method may be useless to the ordinary user.
     * @return the value used in Search Finder.
     */
    public String getValue(){
        return value;
    }

}
