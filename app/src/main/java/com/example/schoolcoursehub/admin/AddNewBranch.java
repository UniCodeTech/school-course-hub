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

    private MapView mapView;
    private GoogleMap myMap;
    private EditText branchNameInput;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_branch);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(AddNewBranch.this);
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        myMap = googleMap;

        LatLng sydney = new LatLng(-34, 151);
        myMap.addMarker(new MarkerOptions().position(sydney).title("Sydney"));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }


    private void addBranch(String name, double latitude, double longitude) {
        // Insert the new branch into the database
        //AppDatabase db = AppDatabase.getInstance(this);
        //Branch branch = new Branch(name, latitude, longitude);
        //db.branchDao().insert(branch);

        // Show a toast message to confirm the branch was added
        Toast.makeText(this, "Branch added successfully", Toast.LENGTH_SHORT).show();

        // Finish the activity
        finish();
    }
}