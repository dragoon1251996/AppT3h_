package com.t3h.whiyew.appt3h.activity;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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
import com.t3h.whiyew.appt3h.abstractclass.WakeLocker;
import com.t3h.whiyew.appt3h.models.MyLatLng;

import java.io.IOException;
import java.util.List;

import static com.t3h.whiyew.appt3h.R.color.common_google_signin_btn_text_light_disabled;
import static com.t3h.whiyew.appt3h.R.id.map;

/**
 * Created by Whiyew on 31/03/2017.
 */

public class Warning extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LatLng latLng;
    Vibrator vibrator;
    private PowerManager.WakeLock wl;
    Circle circle,circle1;
    private Geocoder geocoder;

    ValueAnimator vAnimator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.erro);
        WakeLocker.release();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
        vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, 1000, 1000};
        vibrator.vibrate(pattern,0);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                vibrator.cancel();
                return false;
            }
        });
        geocoder = new Geocoder(this);
        // Add a marker in Sydney and move the camera
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String android_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        DatabaseReference myRef = database.getReference(android_id);
        myRef.child("Warn").child("latlng").addValueEventListener(new ValueEventListener() {
            Marker markerName;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                latLng = new LatLng(dataSnapshot.getValue(MyLatLng.class).getLatitude(),
                        dataSnapshot.getValue(MyLatLng.class).getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory
                        .zoomTo(17));
                if(circle!=null) circle.remove();
                if(circle1!=null)circle1.remove();
                if( vAnimator!=null)vAnimator.cancel();
                circle1=mMap.addCircle(new CircleOptions().center(latLng)
                        .strokeColor(Color.RED)
                        .radius(5)
                        .fillColor(Color.RED)
                );
                circle = mMap.addCircle(new CircleOptions().center(latLng)
                        .strokeColor(Color.RED)
                        .radius(10000)
                        .strokeWidth(5).fillColor(common_google_signin_btn_text_light_disabled)
                );
                if (markerName != null) {
                    markerName.remove();
                }
                markerName = mMap.addMarker(new MarkerOptions().position(latLng).title("family")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.warning))

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



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        vibrator.cancel();
        finish();
    }
    private LatLng getLocationFromName(String name) {
        LatLng latLng = null;
        try {
            List<Address> addresses = geocoder.getFromLocationName(name, 1);
            if (addresses.size() > 0) {
                latLng = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return latLng;
    }
}
