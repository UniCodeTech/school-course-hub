package com.example.schoolcoursehub.guest;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolcoursehub.R;
import com.example.schoolcoursehub.helper.Branch;
import com.example.schoolcoursehub.helper.Course;
import com.example.schoolcoursehub.helper.DBHandler;
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
    private TextView courseNameTextView, courseCostTextView, courseDurationTextView,
            currentEnrollmentTextView, startingDateTextView, registrationClosingDateTextView,
            publishDateTextView, branchTextView;
    private DBHandler dbHandler;
    private int branchId;
    Branch branch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);

        courseId = getIntent().getIntExtra("courseId", -1);
        String courseName = getIntent().getStringExtra("courseName");
        branchId = getIntent().getIntExtra("branchId", -1);
        setTitle(courseName);

        dbHandler = new DBHandler(this);    // database

        courseNameTextView = findViewById(R.id.courseNameTextView);
        courseCostTextView = findViewById(R.id.courseCostTextView);
        courseDurationTextView = findViewById(R.id.courseDurationTextView);
        currentEnrollmentTextView = findViewById(R.id.currentEnrollmentTextView);
        startingDateTextView = findViewById(R.id.startingDateTextView);
        registrationClosingDateTextView = findViewById(R.id.registrationClosingDateTextView);
        publishDateTextView = findViewById(R.id.publishDateTextView);
        branchTextView = findViewById(R.id.branchTextView);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.branchMap);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Log.e(TAG, "Error: Map Fragment not found!");
            throw new RuntimeException("Map Fragment Not Found!");
        }

        // get all branch details
        // get course details
        Course course = dbHandler.fetchCourseDetails(courseId);
        branch = dbHandler.getBranchById(branchId);

        // fill the text box
        if (course != null || branch != null) {
            String enroll = String.valueOf(course.getCurrentEnrollment())+"/"+String.valueOf(course.getMaxParticipants());
            courseNameTextView.setText(course.getCourseName());
            courseCostTextView.setText(String.valueOf(course.getCourseCost()));
            courseDurationTextView.setText(course.getCourseDuration());
            currentEnrollmentTextView.setText(enroll);
            startingDateTextView.setText(course.getStartingDate());
            registrationClosingDateTextView.setText(course.getRegistrationClosingDate());
            publishDateTextView.setText(course.getPublishDate());
            branchTextView.setText(branch.getBranchName());
        } else {
            Toast.makeText(this, "Please try again later!", Toast.LENGTH_SHORT).show();
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
            double latitude = branch.getLatitude();
            double longitude = branch.getLongitude();
            String locationName = branch.getBranchName();

            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .title(locationName);

            myMap.addMarker(markerOptions);

            myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 12));
        } else {
            Toast.makeText(this, "Error: Google Map is null!", Toast.LENGTH_SHORT).show();
        }
    }

}