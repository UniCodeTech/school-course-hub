package com.example.schoolcoursehub.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.schoolcoursehub.R;
import com.example.schoolcoursehub.helper.DBHandler;

import java.util.List;

public class AddNewCourse extends AppCompatActivity {
    private EditText courseNameEditText, courseCostEditText, courseDurationEditText,
            maxParticipantsEditText, startingDateEditText, registrationClosingDateEditText;
    private Button registerButton;
    private TextView errorTextView;
    private Spinner branchSpinner;
    private DBHandler dbHandler;

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

        registerButton = findViewById(R.id.registerButton);

        errorTextView = findViewById(R.id.errorTextView);

        branchSpinner = findViewById(R.id.branchSpinner);

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
                // Do something with the selected branch
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }
}