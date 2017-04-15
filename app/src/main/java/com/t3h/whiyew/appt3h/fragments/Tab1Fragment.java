package com.t3h.whiyew.appt3h.fragments;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.t3h.whiyew.appt3h.R;
import com.t3h.whiyew.appt3h.models.MyLatLng;

import java.util.ArrayList;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by Whiyew on 27/03/2017.
 */
public class Tab1Fragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {
    GoogleMap map;

    MapView mapView;
    View view;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    ArrayList<String> idOfmember;
    boolean check4 = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.tab1fragment, container, false);


        // Toast.makeText(getContext(), MainActivity.indexGroup+"",Toast.LENGTH_LONG).show();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) view.findViewById(R.id.map);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        map = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setOnMarkerClickListener(this);
        idOfmember = new ArrayList<>();
        new My1(getActivity()).execute();
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        String android_id = Settings.Secure.getString(getContext().getContentResolver(),
//                Settings.Secure.ANDROID_ID);
//        DatabaseReference myRef = database.getReference(android_id);
//        myRef.child("LatLng").addValueEventListener(new ValueEventListener() {
//            Marker markerName;
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                LatLng latLng = new LatLng(dataSnapshot.getValue(MyLatLng.class).getLatitude(),
//                        dataSnapshot.getValue(MyLatLng.class).getLongitude());
//
//                if (markerName != null) {
//                    markerName.remove();
//                }
//                markerName = map.addMarker(new MarkerOptions().position(latLng).title("Title"));
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= M) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                map.setMyLocationEnabled(true);
            }
        } else {
            map.setMyLocationEnabled(true);
        }

    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(mCurrLocationMarker)) {

        }
        return true;
    }

    class My1 extends AsyncTask<Void, Integer, Void> {
        GridView mGridView;
        Activity mContex;
        int temp;

        public My1(Activity contex) {

            this.mContex = contex;
        }

        @Override
        protected Void doInBackground(Void... params) {
            boolean cher = true;

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            String android_id = Settings.Secure.getString(getActivity().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            DatabaseReference myRef = database.getReference("Groups");

            myRef.child(Load.allgroupOfMe.get(MyHome.indexGroup)).child("NameOfGroup").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    idOfmember.add(dataSnapshot.getValue().toString());
                    check4 = false;
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


            while (cher == true) {
                try {
                    Thread.sleep(500);
                    if (check4 == false) {
                        break;
                    }
                } catch (InterruptedException e) {
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            for (int i = 0; i < idOfmember.size(); i++) {
                DatabaseReference myRef = database.getReference(idOfmember.get(i));
                myRef.child("LatLng").addValueEventListener(new ValueEventListener() {
                    Marker markerName;

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        LatLng latLng = new LatLng(dataSnapshot.getValue(MyLatLng.class).getLatitude(),
                                dataSnapshot.getValue(MyLatLng.class).getLongitude());

                        if (markerName != null) {
                            markerName.remove();
                        }
                        markerName = map.addMarker(new MarkerOptions().position(latLng).title("Title"));

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
              //  onDestroy();

            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
     //   map.clear();
    }
}
