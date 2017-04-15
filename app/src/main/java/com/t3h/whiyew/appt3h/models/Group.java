package com.t3h.whiyew.appt3h.models;

/**
 * Created by Whiyew on 27/03/2017.
 */

public class Group {
    private String Name;
    private String Language;
    private String Access;
    public Group() {
    }

    public Group(String name, String language, String access) {
        Name = name;
        Language = language;
        Access = access;
    }

    public String getName() {
        return Name;
    }

    public String getLanguage() {
        return Language;
    }

    public String getAccess() {
        return Access;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public void setAccess(String access) {
        Access = access;
    }
}
