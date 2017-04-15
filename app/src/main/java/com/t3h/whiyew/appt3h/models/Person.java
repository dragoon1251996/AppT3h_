package com.t3h.whiyew.appt3h.models;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Whiyew on 22/03/2017.
 */

public class Person {
    private String android_id ;
    private String image;
    private ArrayList<String> group;
    private LatLng latLng;
    private int age;
    private String gmail;
    private String adress;
    private String language;

    public Person(String android_id, String image, ArrayList<String> group, LatLng latLng, int age, String gmail, String adress, String language) {
        this.android_id = android_id;
        this.image = image;
        this.group = group;
        this.latLng = latLng;
        this.age = age;
        this.gmail = gmail;
        this.adress = adress;
        this.language = language;
    }

    public void setAndroid_id(String android_id) {
        this.android_id = android_id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setGroup(ArrayList<String> group) {
        this.group = group;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
