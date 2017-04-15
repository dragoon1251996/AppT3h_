package com.t3h.whiyew.appt3h.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.t3h.whiyew.appt3h.asynctask.ThreadUpdateMarker;

public class UpdateMarker extends Service {
    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ThreadUpdateMarker  threadUpdateMarker=new ThreadUpdateMarker(this);
        threadUpdateMarker.execute();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }

}