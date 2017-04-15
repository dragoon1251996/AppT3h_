package com.t3h.whiyew.appt3h.models;

/**
 * Created by Whiyew on 30/03/2017.
 */

public class FromGroup {
    String namefrom;
    String namegroup;

    public String getNamefrom() {
        return namefrom;
    }

    public FromGroup() {
    }

    public void setNamefrom(String namefrom) {
        this.namefrom = namefrom;
    }

    public String getNamegroup() {
        return namegroup;
    }

    public void setNamegroup(String namegroup) {
        this.namegroup = namegroup;
    }

    public FromGroup(String namefrom, String namegroup) {

        this.namefrom = namefrom;
        this.namegroup = namegroup;
    }
}
