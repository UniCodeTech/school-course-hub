package com.example.schoolcoursehub.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schoolcoursehub.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddNewBranch extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap myMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_branch);

        // Initialize the SupportMapFragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        // Ensure mapFragment is not null before calling getMapAsync
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            // Handle the case where mapFragment is null
            Toast.makeText(this, "Error initializing map", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        myMap = googleMap;

        LatLng colombo = new LatLng(6.9271, 79.8612);
        myMap.addMarker(new MarkerOptions().position(colombo).title("Colombo"));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(colombo));

    }


    private void addBranch(String name, double latitude, double longitude) {
        // Insert the new branch into the database
        // Show a toast message to confirm the branch was added
        Toast.makeText(this, "Branch added successfully", Toast.LENGTH_SHORT).show();

        // Finish the activity
        finish();
    }
}