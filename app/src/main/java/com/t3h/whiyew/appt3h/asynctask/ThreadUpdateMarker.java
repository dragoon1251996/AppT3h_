package com.t3h.whiyew.appt3h.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.provider.Settings;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.t3h.whiyew.appt3h.models.GPSTracker;

/**
 * Created by Whiyew on 24/03/2017.
 */

public class ThreadUpdateMarker extends AsyncTask<Void, LatLng, Void> {



    Context contextCha;
    GPSTracker gpsTracker;
    FirebaseDatabase database;
    public ThreadUpdateMarker(Context ctx) {
        contextCha = ctx;
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        gpsTracker = new GPSTracker(contextCha);
        database = FirebaseDatabase.getInstance();
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        boolean aBoolean = true;
        gpsTracker = new GPSTracker(contextCha);
        LatLng latLng;
        while (aBoolean) {

            if (gpsTracker.canGetLocation()) {
                latLng = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
                SystemClock.sleep(5000);
                publishProgress(latLng);
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(LatLng... values) {
        gpsTracker = new GPSTracker(contextCha);

        String android_id = Settings.Secure.getString(contextCha.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        DatabaseReference myRef = database.getReference(android_id);
        myRef.push().setValue(values[0]);
    }


    @Override
    protected void onPostExecute(Void result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);

    }


}

