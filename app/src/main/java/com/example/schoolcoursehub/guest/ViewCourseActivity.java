package com.example.schoolcoursehub.guest;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.schoolcoursehub.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ViewCourseActivity extends AppCompatActivity implements OnMapReadyCallback {
    private int courseId;
    private GoogleMap myMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);

        courseId = getIntent().getIntExtra("courseId", -1);
        String courseName = getIntent().getStringExtra("courseName");
        setTitle(courseName);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.branchMap);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Log.e(TAG, "Error: Map Fragment not found!");
            throw new RuntimeException("Map Fragment Not Found!");
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;

        if (myMap != null) {
            try {
                MapsInitializer.initialize(getApplicationContext());
            } catch (Exception e) {
                Log.e(TAG, "Error initializing Google Maps: ", e);
                Toast.makeText(this, "Error initializing Google Maps", Toast.LENGTH_SHORT).show();
                return;
            }

            // Add a marker for the location
            double latitude = 37.7749;
            double longitude = -122.4194;
            String locationName = "San Francisco";

            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .title(locationName);

            myMap.addMarker(markerOptions);

            // Move camera to the marker position
            myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 12));
        } else {
            Toast.makeText(this, "Error: Google Map is null!", Toast.LENGTH_SHORT).show();
        }
    }

}