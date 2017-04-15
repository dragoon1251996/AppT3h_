package com.t3h.whiyew.appt3h.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.t3h.whiyew.appt3h.models.GPSTracker;
import com.t3h.whiyew.appt3h.models.MyLatLng;

/**
 * Created by Whiyew on 26/03/2017.
 */

public class ReService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        GPSTracker gpsTracker = new GPSTracker(context);
        MyLatLng latLng = new MyLatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        DatabaseReference myRef = database.getReference(android_id);
        myRef.child("LatLng").setValue(latLng);
//        Intent background = new Intent(context, UpdateMarker.class);
//        context.startService(background);
    }
}
