package tk.mihou.amatsuki.api.enums;

public enum ContentWarning {

    GORE(48), SEXUAL_CONTENT(50), STRONG_LANGUAGE(49);

    private int ctid;

    ContentWarning(int ctid){
        this.ctid = ctid;
    }

    /**
     * Returns the content id for the warning.
     * This may be useless to the ordinary user.
     * @return Content ID to be used in search finder.
     */
    public int getCtid(){
        return ctid;
    }

}
