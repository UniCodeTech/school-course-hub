package com.example.schoolcoursehub.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolcoursehub.R;
import com.example.schoolcoursehub.helper.Course;
import com.example.schoolcoursehub.helper.DBHandler;
import com.example.schoolcoursehub.signupandlogin.LoginActivity;
import com.example.schoolcoursehub.user.UserHome;

import java.util.List;

public class AdminHome extends AppCompatActivity {

    private DBHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        int userId = getIntent().getIntExtra("userId", -1);

        System.out.println("Admin Home Opened. UserID: "+userId);
        db = new DBHandler(this);

        displayCourses();
    }

    private void displayCourses() {
        List<Course> courses = db.getAllCourses();

        LinearLayout courseListLayout = findViewById(R.id.course_list_layout);
        courseListLayout.removeAllViews();

        for (Course course : courses) {
            View courseCardView = getLayoutInflater().inflate(R.layout.item_course, null);

            TextView courseNameTextView = courseCardView.findViewById(R.id.course_name);
            TextView courseCostTextView = courseCardView.findViewById(R.id.course_cost);
            TextView coursDurationTextView = courseCardView.findViewById(R.id.course_duration);
            TextView maxParticipantTextView = courseCardView.findViewById(R.id.max_participant);
            TextView startDateTextView = courseCardView.findViewById(R.id.start_date);

            courseNameTextView.setText(course.getCourseName());
            courseCostTextView.setText(String.valueOf(course.getCourseCost()));
            coursDurationTextView.setText(String.valueOf(course.getCourseDuration()));
            maxParticipantTextView.setText(String.valueOf(course.getMaxParticipants()));
            startDateTextView.setText(String.valueOf(course.getStartingDate()));


            // Set click listener for the card
            final int courseId = course.getCourseId();
            courseCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AdminHome.this, UpdateCourseActivity.class);
                    intent.putExtra("courseId", courseId);
                    startActivity(intent);
                }
            });

            courseListLayout.addView(courseCardView);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle item selection
        if(item.getItemId()==R.id.new_course){
            addNewCourse();
        } else if(item.getItemId()==R.id.view_user){
            viewUserList();
        } else if (item.getItemId()==R.id.add_branch) {
            addBranch();
        } else if (item.getItemId()==R.id.view_enrolled_student) {
            viewEnrolledStudent();
        }
        return true;
    }


    private void addNewCourse() {
        System.out.println("Add New Course Clicked");
        Intent intent = new Intent(AdminHome.this, AddNewCourse.class);
        startActivity(intent);
    }

    private void viewUserList() {
        System.out.println("View User Clicked");
        Intent intent = new Intent(AdminHome.this, ViewUserList.class);
        startActivity(intent);
    }

    private void addBranch() {
        System.out.println("Add Branch Clicked");
        Intent intent = new Intent(AdminHome.this, AddNewBranch.class);
        startActivity(intent);
    }

    private void viewEnrolledStudent(){
        Intent intent = new Intent(AdminHome.this, ViewEnrolledStudent.class);
        startActivity(intent);
    }
}