package com.example.schoolcoursehub.admin;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolcoursehub.R;
import com.example.schoolcoursehub.helper.CourseUserDetails;
import com.example.schoolcoursehub.helper.CourseUserDetailsAdapter;
import com.example.schoolcoursehub.helper.DBHandler;

import java.util.List;

public class ViewEnrolledStudent extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CourseUserDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_enrolled_student);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Retrieve details from the database
        DBHandler dbHandler = new DBHandler(this);
        List<CourseUserDetails> courseUserDetailsList = dbHandler.getAllCourseUsersWithDetails();

        // Set up the adapter with the retrieved data
        adapter = new CourseUserDetailsAdapter(courseUserDetailsList);
        recyclerView.setAdapter(adapter);
    }
}
