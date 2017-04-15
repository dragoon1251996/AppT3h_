package com.t3h.whiyew.appt3h.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Whiyew on 31/03/2017.
 */

public class Warn {
    LatLng latLng;
    String check ;

    public Warn() {
    }

    public LatLng getLatLng() {

        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public Warn(LatLng latLng, String check) {

        this.latLng = latLng;
        this.check = check;
    }
}
