package com.example.schoolcoursehub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolcoursehub.admin.AdminHome;
import com.example.schoolcoursehub.admin.UpdateCourseActivity;
import com.example.schoolcoursehub.emailsender.Email;
import com.example.schoolcoursehub.emailsender.GMailSender;
import com.example.schoolcoursehub.emailsender.SendMailTask;
import com.example.schoolcoursehub.guest.ViewCourseActivity;
import com.example.schoolcoursehub.helper.Course;
import com.example.schoolcoursehub.helper.DBHandler;
import com.example.schoolcoursehub.signupandlogin.LoginActivity;
import com.example.schoolcoursehub.signupandlogin.RegistrationActivity;
import com.example.schoolcoursehub.user.RegisterNow;
import com.example.schoolcoursehub.user.UserHome;
import com.example.schoolcoursehub.user.UserViewCourse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    private DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHandler(this);
        String DB_NAME = "schoolcoursehub-db";
        File dbFile = this.getDatabasePath(DB_NAME);
        if (dbFile.exists()) {
            Log.d("Database", "Database exists");
        } else {
            Log.d("Database", "Database does not exist");
        }

        displayCourses();
    }

    private void displayCourses() {
        List<Course> courses = db.getAllCourses();

        if(courses != null) {
            LinearLayout courseListLayout = findViewById(R.id.courseListLayout);
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

                final int courseId = course.getCourseId();
                courseCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, ViewCourseActivity.class);
                        intent.putExtra("courseId", courseId);
                        intent.putExtra("courseName", course.getCourseName());
                        intent.putExtra("branchId", course.getBranchId());
                        startActivity(intent);
                    }
                });

                courseListLayout.addView(courseCardView);
            }
        } else {
            Toast.makeText(this, "Error retrieving courses!", Toast.LENGTH_SHORT).show();
        }


    }

    public void onRegisterButtonClick(View view) {

        System.out.println("Register Button Clicked");

        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    public void onLoginButtonClick(View view) {

        System.out.println("Login Button Clicked");

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}