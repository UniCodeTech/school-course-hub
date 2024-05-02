package com.example.schoolcoursehub.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.schoolcoursehub.R;
import com.example.schoolcoursehub.helper.Course;
import com.example.schoolcoursehub.helper.CourseAdapter;
import com.example.schoolcoursehub.helper.CourseUserDetails;
import com.example.schoolcoursehub.helper.CourseUserDetailsAdapter;
import com.example.schoolcoursehub.helper.DBHandler;
import com.example.schoolcoursehub.helper.RegisteredCourseAdapter;

import java.util.List;

public class ViewRegisteredCourse extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RegisteredCourseAdapter adapter;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_registered_course);
        userId = getIntent().getIntExtra("userId", -1);
        if (userId == -1) {
            Toast.makeText(this, "User ID not passed", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Retrieve details from the database
        DBHandler dbHandler = new DBHandler(this);
        List<Course> courseDetailsList = dbHandler.getCoursesForUser(userId);

        // Set up the adapter with the retrieved data
        adapter = new RegisteredCourseAdapter(courseDetailsList);
        recyclerView.setAdapter(adapter);

        if (courseDetailsList == null || courseDetailsList.isEmpty()) {
            Toast.makeText(this, "No courses found for this user", Toast.LENGTH_SHORT).show();
        }
    }
}