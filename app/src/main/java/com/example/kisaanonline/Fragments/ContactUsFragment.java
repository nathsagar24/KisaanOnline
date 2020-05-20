package com.example.kisaanonline.Fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kisaanonline.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends Fragment {

    MapView locationMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact_us, container, false);
        locationMap = v.findViewById(R.id.location_map);
        locationMap.onCreate(savedInstanceState);
        locationMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.addMarker(new MarkerOptions().position(new LatLng(23.0040284,72.500764)).title(getString(R.string.location_description)));
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        locationMap.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        locationMap.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        locationMap.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        locationMap.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        locationMap.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationMap.onDestroy();
    }
}
