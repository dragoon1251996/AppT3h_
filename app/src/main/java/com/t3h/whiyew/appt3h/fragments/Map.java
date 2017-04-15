package com.t3h.whiyew.appt3h.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.t3h.whiyew.appt3h.R;
import com.t3h.whiyew.appt3h.asynctask.MyAsynctask;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Map extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleMap.OnMarkerClickListener,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final int REQUEST_PERMISTION = 1;
    public static final int WHAT_WAY = 1;
    EditText to;
    Button go;
    private Polyline polyline;
    private Geocoder geocoder;
    private Marker markerStart;
    private Marker markerEnd;
    GoogleMap map;
    MapView mapView;
    View view;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    LatLng latLng;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.tab2fragment, container, false);
        go = (Button) view.findViewById(R.id.button1);
        to = (EditText) view.findViewById(R.id.editText1);


        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    String start = to.getText().toString();
                    LatLng lnStart = getLocationFromName(start);
                    if (lnStart == null) {
                        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        map.animateCamera(CameraUpdateFactory.zoomTo(11));
                        return;
                    }
                    map.moveCamera(CameraUpdateFactory.newLatLng(midPoint(lnStart, latLng)));
                    Double place = CalculationByDistance(lnStart, latLng);
                    if (place > 3000) {
                        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        map.animateCamera(CameraUpdateFactory.zoomTo(11));
                        try {
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                        Toast.makeText(getActivity(), "Don't Support!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (place > 2000) {
                        map.animateCamera(CameraUpdateFactory
                                .zoomTo(3));
                    } else {
                        if (place > 1000) {
                            map.animateCamera(CameraUpdateFactory
                                    .zoomTo(4));
                        } else {
                            if (place > 500) {
                                map.animateCamera(CameraUpdateFactory
                                        .zoomTo(5));
                            } else {
                                if (place > 100) {
                                    map.animateCamera(CameraUpdateFactory
                                            .zoomTo(6));
                                } else {
                                    if (place > 10) {
                                        map.animateCamera(CameraUpdateFactory
                                                .zoomTo(7));
                                    } else {
                                        map.animateCamera(CameraUpdateFactory
                                                .zoomTo(10));
                                    }
                                }
                            }
                        }
                    }
                    try {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    MyAsynctask myAsynctask = new MyAsynctask(lnStart, latLng, handler);
                    myAsynctask.execute();

                }

        });

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
        geocoder = new Geocoder(getActivity());
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setOnMarkerClickListener(this);
        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                map.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = map.addMarker(markerOptions);

        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.zoomTo(11));

        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        map.setMyLocationEnabled(true);
                    }

                } else {
                }
                return;
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(mCurrLocationMarker)) {

        }
        return true;
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

    public String getLocationName(LatLng latLng) {
        String name = "unknow";
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses.size() > 0) {
                name = addresses.get(0).getAddressLine(0);
                name += " - " + addresses.get(0).getAddressLine(1);
                name += " - " + addresses.get(0).getAddressLine(2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (WHAT_WAY == msg.what) {
                ArrayList<LatLng> latLngs = (ArrayList<LatLng>) msg.obj;
                if (polyline != null) {
                    polyline.remove();
                }
                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.color(Color.RED);
                polylineOptions.width(5);
                polylineOptions.addAll(latLngs);
                polyline = map.addPolyline(polylineOptions);
                if (markerStart != null) {
                    markerStart.remove();
                }
                if (markerEnd != null) {
                    markerEnd.remove();
                }
                markerStart = drawMarker(latLngs.get(0), BitmapDescriptorFactory.HUE_BLUE
                        , "Start location", getLocationName(latLngs.get(0)));
                markerEnd = drawMarker(latLngs.get(latLngs.size() - 1), BitmapDescriptorFactory.HUE_MAGENTA
                        , "End location", getLocationName(latLngs.get(latLngs.size() - 1)));
            }
        }
    };

    private Marker drawMarker(LatLng latLng, float hue, String title, String snippet) {
        MarkerOptions options = new MarkerOptions();
        options.position(latLng);
        options.title(title);
        options.snippet(snippet);
        options.icon(BitmapDescriptorFactory.defaultMarker(hue));
        return map.addMarker(options);
    }

    private LatLng midPoint(LatLng lat1, LatLng lat2) {
        return new LatLng((lat1.latitude + lat2.latitude) / 2, (lat1.longitude + lat2.longitude) / 2);

    }

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        return Radius * c;
    }
}
