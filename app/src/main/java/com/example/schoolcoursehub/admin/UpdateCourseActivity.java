package com.example.schoolcoursehub.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
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
    private List<Branch> branches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_course);

        //int courseId = 1;
        int courseId = getIntent().getIntExtra("courseId", -1);

        dbHandler = new DBHandler(this);    // database

        courseNameEditText = findViewById(R.id.updateCourseNameEditText);
        courseCostEditText = findViewById(R.id.updateCourseCostEditText);
        courseDurationEditText = findViewById(R.id.updateCourseDurationEditText);
        maxParticipantsEditText = findViewById(R.id.updateMaxParticipantsEditText);
        startingDateEditText = findViewById(R.id.updateStartingDateEditText);
        registrationClosingDateEditText = findViewById(R.id.updateRegistrationClosingDateEditText);

        registerButton = findViewById(R.id.updateButton);

        errorTextView = findViewById(R.id.errorTextView);

        branchSpinner = findViewById(R.id.updateBranchSpinner);

        // get course details
        Course course = dbHandler.fetchCourseDetails(courseId);

        // fill the text box
        if (course != null) {
            courseNameEditText.setText(course.getCourseName());
            courseCostEditText.setText(String.valueOf(course.getCourseCost()));
            courseDurationEditText.setText(course.getCourseDuration());
            maxParticipantsEditText.setText(String.valueOf(course.getMaxParticipants()));
            startingDateEditText.setText(course.getStartingDate());
            registrationClosingDateEditText.setText(course.getRegistrationClosingDate());
            branchId = course.getBranchId();
        }

        // branch spinner
        populateBranchSpinner();

        // register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedCourseName = courseNameEditText.getText().toString().trim();
                double updatedCourseCost = Double.parseDouble(courseCostEditText.getText().toString().trim());
                String updatedCourseDuration = courseDurationEditText.getText().toString().trim();
                int updatedMaxParticipants = Integer.parseInt(maxParticipantsEditText.getText().toString().trim());
                String updatedStartingDate = startingDateEditText.getText().toString().trim();
                String updatedRegistrationClosingDate = registrationClosingDateEditText.getText().toString().trim();

                // validate
                if (TextUtils.isEmpty(updatedCourseName) || TextUtils.isEmpty(updatedCourseDuration) || TextUtils.isEmpty(updatedStartingDate) || TextUtils.isEmpty(updatedRegistrationClosingDate)) {
                    Toast.makeText(UpdateCourseActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // create Course obj
                Course updatedCourse = new Course();
                updatedCourse.setCourseName(updatedCourseName);
                updatedCourse.setCourseCost(updatedCourseCost);
                updatedCourse.setCourseDuration(updatedCourseDuration);
                updatedCourse.setMaxParticipants(updatedMaxParticipants);
                updatedCourse.setStartingDate(updatedStartingDate);
                updatedCourse.setRegistrationClosingDate(updatedRegistrationClosingDate);
                updatedCourse.setBranchId(selectedBranchId);

                /*boolean isUpdated = dbHandler.updateCourse(updatedCourse);

                // Display toast message based on update result
                if (isUpdated) {
                    Toast.makeText(UpdateCourseActivity.this, "Course updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateCourseActivity.this, "Failed to update course", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
    }

    private void populateBranchSpinner() {
        // Get all branches from the database
        branches = dbHandler.getAllBranches();

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

        branchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Branch selectedBranch = branches.get(position);

                selectedBranchId = selectedBranch.getBranchId();
            }

            // optional one
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
