package com.t3h.whiyew.appt3h.models;

/**
 * Created by Whiyew on 26/03/2017.
 */

public class Member {
    String name;
    String gmail;
    String age;
    String language;
    String address;
    public Member() {
    }
    public Member(String name, String gmail, String age, String language, String address) {
        this.name = name;
        this.gmail = gmail;
        this.age = age;
        this.language = language;
        this.address = address;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
