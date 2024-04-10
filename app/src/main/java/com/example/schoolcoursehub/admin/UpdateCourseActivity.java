package com.example.schoolcoursehub.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolcoursehub.R;
import com.example.schoolcoursehub.helper.Branch;
import com.example.schoolcoursehub.helper.Course;
import com.example.schoolcoursehub.helper.DBHandler;

import java.util.ArrayList;
import java.util.List;

public class UpdateCourseActivity extends AppCompatActivity {

    private EditText courseNameEditText, courseCostEditText, courseDurationEditText,
            maxParticipantsEditText, startingDateEditText, registrationClosingDateEditText;
    private Button registerButton;
    private TextView errorTextView;
    private Spinner branchSpinner;
    private DBHandler dbHandler;

    private int selectedBranchId;
    private int branchId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_course);

        //int courseId = 1;
        int courseId = getIntent().getIntExtra("courseId", -1);

        dbHandler = new DBHandler(this);    // database

        courseNameEditText = findViewById(R.id.courseNameEditText);
        courseCostEditText = findViewById(R.id.courseCostEditText);
        courseDurationEditText = findViewById(R.id.courseDurationEditText);
        maxParticipantsEditText = findViewById(R.id.maxParticipantsEditText);
        startingDateEditText = findViewById(R.id.startingDateEditText);
        registrationClosingDateEditText = findViewById(R.id.registrationClosingDateEditText);

        registerButton = findViewById(R.id.registerButton);

        errorTextView = findViewById(R.id.errorTextView);

        branchSpinner = findViewById(R.id.branchSpinner);

        /*Course course = dbHandler.fetchCourseDetails(courseId);

        // Populate TextViews with the fetched course details
        if (course != null) {
            courseNameEditText.setText(course.getCourseName());
            courseCostEditText.setText(String.valueOf(course.getCourseCost()));
            courseDurationEditText.setText(course.getCourseDuration());
            maxParticipantsEditText.setText(course.getMaxParticipants());
            startingDateEditText.setText(course.getStartingDate());
            registrationClosingDateEditText.setText(course.getRegistrationClosingDate());
            branchId = course.getBranchId();
        }*/

        //populateBranchSpinner();
    }

    private void populateBranchSpinner() {
        // Get all branches from the database
        List<Branch> branches = dbHandler.getAllBranches();

        List<String> branchNames = new ArrayList<>();
        int selectedPosition = 0;
        for (int i = 0; i < branches.size(); i++) {
            Branch branch = branches.get(i);
            branchNames.add(branch.getBranchName());
            if (branch.getBranchId() == branchId) {
                selectedPosition = i;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, branchNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branchSpinner.setAdapter(adapter);

        branchSpinner.setSelection(selectedPosition);
    }
}