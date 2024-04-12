package com.example.schoolcoursehub.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.schoolcoursehub.R;
import com.example.schoolcoursehub.helper.Course;
import com.example.schoolcoursehub.helper.CourseAdapter;
import com.example.schoolcoursehub.helper.DBHandler;

import java.util.ArrayList;
import java.util.List;

public class RegisterNow extends AppCompatActivity {

    private int userId;
    private RecyclerView recyclerView;
    private CourseAdapter adapter;
    private List<Course> courses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_now);

        userId = getIntent().getIntExtra("userId", -1);

        Toast.makeText(this, "Register now open: "+userId, Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.recyclerView);

        fetchCourseDetailsFromDatabase();

        adapter = new CourseAdapter(courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void fetchCourseDetailsFromDatabase() {
        DBHandler dbHandler = new DBHandler(this);
        courses = dbHandler.getAllCourses();
    }
}