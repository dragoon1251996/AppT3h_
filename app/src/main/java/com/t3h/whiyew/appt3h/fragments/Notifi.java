package com.t3h.whiyew.appt3h.fragments;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.t3h.whiyew.appt3h.R;
import com.t3h.whiyew.appt3h.adapters.AdapterNotificaion;
import com.t3h.whiyew.appt3h.models.FromGroup;

import java.util.ArrayList;

/**
 * Created by Whiyew on 30/03/2017.
 */

public class Notifi extends Fragment {

    ListView listView;
    AdapterNotificaion adapterNotificaion;
    ArrayList<FromGroup> arrayList;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.nocafition, container, false);

        listView=(ListView)v.findViewById(R.id.lv_group);
        arrayList=new ArrayList<>();
        adapterNotificaion=new AdapterNotificaion(getContext(),R.layout.itemnotification,arrayList);
        listView.setAdapter(adapterNotificaion);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        DatabaseReference myRef = database.getReference(android_id);
        myRef.child("Notification").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue() != null) {
                    FromGroup group = dataSnapshot.getValue(FromGroup.class);


//                    String[] split = group.getNamegroup().split(" ");
                    arrayList.add(new FromGroup(group.getNamefrom(),group.getNamegroup()));
                    adapterNotificaion.notifyDataSetChanged();

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return v;
    }
}
