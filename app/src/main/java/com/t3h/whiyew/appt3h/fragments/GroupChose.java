package com.t3h.whiyew.appt3h.fragments;

import android.Manifest;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.t3h.whiyew.appt3h.R;
import com.t3h.whiyew.appt3h.models.MyLatLng;

import static android.os.Build.VERSION_CODES.M;
import static com.google.android.gms.R.color.common_google_signin_btn_text_light_disabled;

/**
 * Created by Whiyew on 31/03/2017.
 */

public class GroupChose extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {
    GoogleMap map;
    MapView mapView;
    View view;
    Marker warnningMarker;
    boolean check4 = true;
    LatLng myLatlng;
    int temp;
    Circle circle, circle1;
    ValueAnimator vAnimator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        temp = 0;
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
        mapView.setClickable(true);
        map.animateCamera(CameraUpdateFactory.zoomTo(11));
        final String android_id = Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(500);
//                Toast.makeText(getContext(),ManagerGroup.nameOfGroupClick,Toast.LENGTH_LONG).show();
                FirebaseDatabase data = FirebaseDatabase.getInstance();
                //  map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                if (map.getCameraPosition().zoom != 17) {
                    map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    map.animateCamera(CameraUpdateFactory
                            .zoomTo(17));
                } else {
                    map.animateCamera(CameraUpdateFactory
                            .zoomTo(17));
                }
                if (circle != null) circle.remove();
                if (circle1 != null) circle1.remove();
                if (vAnimator != null) vAnimator.cancel();
                circle1 = map.addCircle(new CircleOptions().center(latLng)
                        .strokeColor(Color.RED)
                        .radius(5)
                        .fillColor(Color.RED)
                );
                circle = map.addCircle(new CircleOptions().center(latLng)
                        .strokeColor(Color.RED)
                        .radius(10000)
                        .strokeWidth(5).fillColor(common_google_signin_btn_text_light_disabled)
                );
                vAnimator = new ValueAnimator();
                vAnimator.setRepeatCount(ValueAnimator.INFINITE);
                vAnimator.setRepeatMode(ValueAnimator.RESTART);  /* PULSE */
                vAnimator.setIntValues(0, 100);
                vAnimator.setDuration(2000);
                vAnimator.setEvaluator(new IntEvaluator());
                vAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float animatedFraction = valueAnimator.getAnimatedFraction();
                        // Log.e("", "" + animatedFraction);
                        circle.setRadius(animatedFraction * 100);

                    }
                });
                vAnimator.start();
                for (int i = 0; i < ManagerGroup.arrayId.size(); i++) {
                    if (ManagerGroup.arrayId.get(i).equals(android_id) == false) {
                        DatabaseReference warn = data.getReference(ManagerGroup.arrayId.get(i));
                        warn.child("Warn").child("latlng").setValue(latLng);
                        warn.child("Warn").child("check").setValue("true");
                    }
                }

            }
        });
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        for (int i = 0; i < ManagerGroup.arrayId.size(); i++) {
            if (ManagerGroup.arrayId.get(i).equals(android_id) == false) {
                DatabaseReference myRef = database.getReference(ManagerGroup.arrayId.get(i));
                {
                    myRef.child("LatLng").addValueEventListener(new ValueEventListener() {
                        Marker markerName;

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            LatLng latLng = new LatLng(dataSnapshot.getValue(MyLatLng.class).getLatitude(),
                                    dataSnapshot.getValue(MyLatLng.class).getLongitude());

                            if (markerName != null) {
                                markerName.remove();
                            }
                            markerName = map.addMarker(new MarkerOptions().position(latLng).title("Vương"));

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            } else {
                DatabaseReference myRef = database.getReference(ManagerGroup.arrayId.get(i));
                {
                    myRef.child("LatLng").addValueEventListener(new ValueEventListener() {
                        Marker markerName;

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            LatLng latLng = new LatLng(dataSnapshot.getValue(MyLatLng.class).getLatitude(),
                                    dataSnapshot.getValue(MyLatLng.class).getLongitude());

                            if (markerName != null) {
                                markerName.remove();
                            }
                            markerName = map.addMarker(new MarkerOptions().position(latLng).title("Dũng").icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                            if (temp == 0) {
                                map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                temp = 1;
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }
        }
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

        marker.showInfoWindow();
        return true;
    }


}
