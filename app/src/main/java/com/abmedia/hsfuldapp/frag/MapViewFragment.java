package com.abmedia.hsfuldapp.frag;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abmedia.hsfuldapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

public class MapViewFragment extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;

    private static final Drawable GREEN = new ColorDrawable(Color.WHITE);
    private IconGenerator mIconGenerator;
    // Make the background of marker transparent


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mapview, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mIconGenerator = new IconGenerator(getContext());
        mIconGenerator.setBackground(GREEN);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;


                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(50.564974, 9.685553))
                        .icon(BitmapDescriptorFactory.fromBitmap(mIconGenerator.makeIcon("E-Gebäude")))
                        .title("E-Gebäude"));

                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(50.564085, 9.685223))
                        .icon(BitmapDescriptorFactory.fromBitmap(mIconGenerator.makeIcon("C-Gebäude")))
                        .title("C-Gebäude"));

                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(50.564480, 9.685331))
                        .icon(BitmapDescriptorFactory.fromBitmap(mIconGenerator.makeIcon("Selbstlernzentrum")))
                        .title("Selbstlernzentrum"));


                // For dropping a marker at a point on the Map
                LatLng hsfulda = new LatLng(50.56535639999999, 9.687459799999942);
                googleMap.addMarker(new MarkerOptions().position(hsfulda).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(hsfulda)
                        .zoom(18)
                        .build();

                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


}