package com.example.cdc.smartpan_task.activities.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cdc.smartpan_task.R;
import com.example.cdc.smartpan_task.activities.GPS.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMarkerClickListener{

    View view;

    GoogleMap gMap;

    //Initialize User's location latitude & longitude
    double mLatitude, mLongitude;
    LatLng mlatLng;

    /**
    * Different Locations long & lat
    * */
    double madenty_Lat;
    double madenty_Long;
    LatLng madenty_latlng;
    double nasrCity_lat;
    double nasrCity_long;
    LatLng nasrCity_latlng;
    double maadi_lat;
    double maadi_long;
    LatLng maadi_latling;
    double madenty_distance;
    double nasrCity_distance;
    double maadi_distance;

    //Object from GPSTracker Class to Check on GPS status
    GPSTracker gpsTracker;

    //Integer Using in Multiple Permissions Needed for APIs > 23
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    String[] permissionsRequired = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    public MapFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        gpsTracker = new GPSTracker(getActivity());

        /**
        * Check for Permissions
        * */
        if (ActivityCompat.checkSelfPermission(getActivity(), permissionsRequired[0])
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), permissionsRequired[1])
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), permissionsRequired[2])
                != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(getActivity(), permissionsRequired,
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }

        try {
            view = inflater.inflate(R.layout.fragment_map, container, false);
        } catch (InflateException e) {
            setUpMap();
        }

        initializeMap();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        gpsTracker = new GPSTracker(getActivity());
    }

    private void initializeMap() {
        if (gMap == null) {
            SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
            mapFragment.getMapAsync(this);
        }
    }

    private void setUpMap() {

        gMap.getUiSettings().setZoomControlsEnabled(true);

        madenty_Lat = 30.0967;
        madenty_Long = 31.6625;
        madenty_latlng = new LatLng(madenty_Lat, madenty_Long);

        nasrCity_lat = 30.0566;
        nasrCity_long = 31.3301;
        nasrCity_latlng = new LatLng(nasrCity_lat, nasrCity_long);

        maadi_lat = 29.9627;
        maadi_long = 31.2769;
        maadi_latling = new LatLng(maadi_lat, maadi_long);

        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.8206, 30.8025), 6.0f));

        if(gpsTracker.canGetLocation()) {
            mLatitude = gpsTracker.getLatitude();
            mLongitude = gpsTracker.getLongitude();
            mlatLng = new LatLng(mLatitude, mLongitude);

            madenty_distance = CalculationByDistance(madenty_latlng, mlatLng);
            nasrCity_distance = CalculationByDistance(nasrCity_latlng, mlatLng);
            maadi_distance = CalculationByDistance(maadi_latling, mlatLng);
        }else{
            gpsTracker.showSettingAlert();
        }

        try{
            gMap.setMyLocationEnabled(true);
        }
        catch(SecurityException e){
            e.printStackTrace();
        }

        gMap.addMarker(new MarkerOptions().position(madenty_latlng)
                .title("Madinaty")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));

        gMap.addMarker(new MarkerOptions().position(nasrCity_latlng)
                .title("Nasr City")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

        gMap.addMarker(new MarkerOptions().position(maadi_latling)
                .title("Maadi")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        gMap.setOnInfoWindowClickListener(this);
        gMap.setOnMarkerClickListener(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        setUpMap();
    }

    /**
    * Function returns distance between user's location and any other location on map
    * */
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
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }

    /**
    * Function to handle pinned location title onClick
    * */
    @Override
    public void onInfoWindowClick(Marker marker) {

        if(marker.getTitle().equals("Madinaty")){
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?saddr="+mLatitude+","+mLongitude +"&daddr="+madenty_Lat+","+madenty_Long));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent);
        }
        else if(marker.getTitle().equals("Nasr City")){
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?saddr="+mLatitude+","+mLongitude +"&daddr="+nasrCity_lat+","+nasrCity_long));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent);
        }
        else if(marker.getTitle().equals("Maadi")){
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?saddr="+mLatitude+","+mLongitude +"&daddr="+maadi_lat+","+maadi_long));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent);
        }
    }

    /**
    * Handling marker clicks
    * */
    @Override
    public boolean onMarkerClick(Marker marker) {

        if(gpsTracker.canGetLocation()) {
            mLatitude = gpsTracker.getLatitude();
            mLongitude = gpsTracker.getLongitude();
            mlatLng = new LatLng(mLatitude, mLongitude);

            madenty_distance = CalculationByDistance(madenty_latlng, mlatLng);
            nasrCity_distance = CalculationByDistance(nasrCity_latlng, mlatLng);
            maadi_distance = CalculationByDistance(maadi_latling, mlatLng);
        }else{
            Toast.makeText(getActivity(), "Distance can't be detected", Toast.LENGTH_SHORT).show();
        }

        if(marker.getTitle().equals("Madinaty")){
            marker.setSnippet("Distance: " + madenty_distance + " KM");
            marker.showInfoWindow();
        }
        if(marker.getTitle().equals("Nasr City")){
            marker.setSnippet("Distance: " + nasrCity_distance + " KM");
            marker.showInfoWindow();
        }
        if(marker.getTitle().equals("Maadi")){
            marker.setSnippet("Distance: " + maadi_distance + " KM");
            marker.showInfoWindow();
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_LOCATION:

                for(int i=0;i<grantResults.length;i++){
                    if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                        setUpMap();
                    }
                    else{
                        Toast.makeText(getActivity(), "Permission Denied..", Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }
}
