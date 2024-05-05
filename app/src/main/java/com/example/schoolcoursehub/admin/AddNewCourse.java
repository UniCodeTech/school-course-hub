package com.example.schoolcoursehub.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolcoursehub.R;
import com.example.schoolcoursehub.helper.DBHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddNewCourse extends AppCompatActivity {
    private EditText courseNameEditText, courseCostEditText, courseDurationEditText,
            maxParticipantsEditText, startingDateEditText, registrationClosingDateEditText;
    private Button addButton;
    private TextView errorTextView;
    private Spinner branchSpinner;
    private DBHandler dbHandler;

    private int selectedBranchId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_course);

        dbHandler = new DBHandler(this);    // database

        courseNameEditText = findViewById(R.id.courseNameEditText);
        courseCostEditText = findViewById(R.id.courseCostEditText);
        courseDurationEditText = findViewById(R.id.courseDurationEditText);
        maxParticipantsEditText = findViewById(R.id.maxParticipantsEditText);
        startingDateEditText = findViewById(R.id.startingDateEditText);
        registrationClosingDateEditText = findViewById(R.id.registrationClosingDateEditText);

        addButton = findViewById(R.id.registerButton);

        errorTextView = findViewById(R.id.errorTextView);

        branchSpinner = findViewById(R.id.branchSpinner);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    Date currentDate = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String publishDate = dateFormat.format(currentDate);

                    // Get input values
                    String courseName = courseNameEditText.getText().toString().trim();
                    String courseCostStr = courseCostEditText.getText().toString().trim();
                    String courseDuration = courseDurationEditText.getText().toString().trim();
                    String maxParticipantsStr = maxParticipantsEditText.getText().toString().trim();
                    String startingDate = startingDateEditText.getText().toString().trim();
                    String registrationClosingDate = registrationClosingDateEditText.getText().toString().trim();

                    float courseCost = Float.parseFloat(courseCostStr);
                    int maxParticipants = Integer.parseInt(maxParticipantsStr);

                    long courseId = dbHandler.addCourse(courseName, courseCost, courseDuration, maxParticipants, startingDate, registrationClosingDate, publishDate, selectedBranchId); // Assuming branch ID is "1"

                    if(courseId > 0) {
                        Toast.makeText(getApplicationContext(), "Course added successfully!", Toast.LENGTH_SHORT).show();
                        courseNameEditText.getText().clear();
                        courseCostEditText.getText().clear();
                        courseDurationEditText.getText().clear();
                        maxParticipantsEditText.getText().clear();
                        startingDateEditText.getText().clear();
                        registrationClosingDateEditText.getText().clear();

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    } else {
                        // Failed to add course
                        Toast.makeText(getApplicationContext(), "Failed to add course. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        startingDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(startingDateEditText);
            }
        });

        registrationClosingDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(registrationClosingDateEditText);
            }
        });

        // get all branch details
        List<String> branchNames = dbHandler.getAllBranchNames();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, branchNames);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        branchSpinner.setAdapter(adapter);

        branchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected branch name
                String selectedBranch = parent.getItemAtPosition(position).toString();
                selectedBranchId = getBranchId(selectedBranch);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private int getBranchId(String branchName) {
        return dbHandler.getBranchId(branchName);
    }

    private void showDatePickerDialog(final EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Set the selected date on the EditText
                        editText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                },
                year,
                month,
                dayOfMonth
        );

        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        datePickerDialog.show();
    }

    public boolean validate() {
        // Reset error message
        errorTextView.setText("");

        // Get input values
        String courseName = courseNameEditText.getText().toString().trim();
        String courseCostStr = courseCostEditText.getText().toString().trim();
        String courseDuration = courseDurationEditText.getText().toString().trim();
        String maxParticipantsStr = maxParticipantsEditText.getText().toString().trim();
        String startingDate = startingDateEditText.getText().toString().trim();
        String registrationClosingDate = registrationClosingDateEditText.getText().toString().trim();

        // Check if any field is empty
        if (courseName.isEmpty() || courseCostStr.isEmpty() || courseDuration.isEmpty() ||
                maxParticipantsStr.isEmpty() || startingDate.isEmpty() || registrationClosingDate.isEmpty()) {
            errorTextView.setText("Please fill in all fields");
            return false;
        }

        float courseCost;
        int maxParticipants;
        try {
            courseCost = Float.parseFloat(courseCostStr);
            maxParticipants = Integer.parseInt(maxParticipantsStr);
        } catch (NumberFormatException e) {
            errorTextView.setText("Invalid input for course cost or max participants");
            return false;
        }

        return true;
    }

}