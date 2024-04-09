package com.example.schoolcoursehub.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolcoursehub.R;
import com.example.schoolcoursehub.helper.DBHandler;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

public class AddNewBranch extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "AddNewBranch";
    private GoogleMap myMap;
    private Marker branchMarker;
    private EditText branchNameInput;
    private Button addButton;
    private TextView locationTextView;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_branch);

        Toast.makeText(this, "Open Add Branch", Toast.LENGTH_SHORT).show();

        branchNameInput = findViewById(R.id.branchNameInput);
        addButton = findViewById(R.id.addButton);
        locationTextView = findViewById(R.id.locationTextView);
        dbHandler = new DBHandler(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Log.e(TAG, "Error: Map Fragment not found!");
            throw new RuntimeException("Map Fragment Not Found!");
        }

        addButton.setOnClickListener(view -> addBranch());
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

            myMap.setOnMapClickListener(latLng -> {
                if (branchMarker != null) {
                    branchMarker.remove();
                }
                branchMarker = myMap.addMarker(new MarkerOptions().position(latLng).title("Branch Location"));
            });
        } else {
            Toast.makeText(this, "Error: Google Map is null!", Toast.LENGTH_SHORT).show();
        }
    }

    private void addBranch() {
        String branchName = branchNameInput.getText().toString().trim();
        if (branchName.isEmpty()) {
            Toast.makeText(this, "Please enter branch name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (branchMarker == null) {
            Toast.makeText(this, "Please select branch location on the map", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get latitude and longitude of the selected location
        LatLng branchLatLng = branchMarker.getPosition();
        double latitude = branchLatLng.latitude;
        double longitude = branchLatLng.longitude;

        SQLiteDatabase db = dbHandler.getWritableDatabase();
        locationTextView.setText(branchName + " - " + latitude + ", " + longitude);

        boolean isInserted = false;
        try {
            isInserted = dbHandler.insertBranch(db, branchName, latitude, longitude);
        } catch (SQLException e) {
            Log.e(TAG, "Error inserting branch into database", e);
        }

        if (isInserted) {
            // Insertion successful
            Toast.makeText(this, "Branch inserted successfully", Toast.LENGTH_SHORT).show();
            branchNameInput.setText("");
            if (branchMarker != null) {
                branchMarker.remove();
                branchMarker = null;
            }
        } else {
            Toast.makeText(this, "Failed to insert branch (Database Error)", Toast.LENGTH_SHORT).show();
        }
    }
}