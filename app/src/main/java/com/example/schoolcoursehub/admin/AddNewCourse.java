package com.example.schoolcoursehub.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.schoolcoursehub.R;
import com.example.schoolcoursehub.helper.DBHandler;

import java.util.List;

public class AddNewCourse extends AppCompatActivity {
    private Spinner branchSpinner;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_course);

        System.out.println("*** Add New Course Opened ***");

        dbHandler = new DBHandler(this);
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