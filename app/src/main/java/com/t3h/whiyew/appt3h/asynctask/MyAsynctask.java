package com.t3h.whiyew.appt3h.asynctask;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.google.android.gms.maps.model.LatLng;
import com.t3h.whiyew.appt3h.fragments.Map;
import com.t3h.whiyew.appt3h.models.Direction;

import org.w3c.dom.Document;

import java.util.ArrayList;

/**
 * Created by Whiyew on 27/03/2017.
 */

public class MyAsynctask extends AsyncTask<Void,Void,ArrayList<LatLng>>{
    private LatLng start;
    private LatLng end;
    private Handler handler;
    public MyAsynctask(LatLng start, LatLng end,Handler handler) {
        this.start = start;
        this.end = end;
        this.handler = handler;
    }

    @Override
    protected ArrayList<LatLng> doInBackground(Void... voids) {
        Direction direction = new Direction();
        Document document = direction.getDocument(start,end, Direction.MODE_DRIVING);
        ArrayList<LatLng> latLngs = direction.getDirection(document);
        return latLngs;
    }

    @Override
    protected void onPostExecute(ArrayList<LatLng> latLngs) {
        super.onPostExecute(latLngs);
        Message message = new Message();
        message.what = Map.WHAT_WAY;
        message.obj = latLngs;
        handler.sendMessage(message);
    }
}