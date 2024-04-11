package com.example.schoolcoursehub.guest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.schoolcoursehub.R;

public class ViewCourseActivity extends AppCompatActivity {
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);

        courseId = getIntent().getIntExtra("courseId", -1);
        String courseName = getIntent().getStringExtra("courseName");

        setTitle(courseName);
    }
}