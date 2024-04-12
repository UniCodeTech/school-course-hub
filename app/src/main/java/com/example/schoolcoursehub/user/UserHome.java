package com.example.schoolcoursehub.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolcoursehub.MainActivity;
import com.example.schoolcoursehub.R;
import com.example.schoolcoursehub.guest.ViewCourseActivity;
import com.example.schoolcoursehub.helper.Course;
import com.example.schoolcoursehub.helper.DBHandler;

import java.util.List;

public class UserHome extends AppCompatActivity {

    private DBHandler db;
    String userName = "";
    private TextView welcomeUser;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        userId = getIntent().getIntExtra("userId", -1);
        System.out.println("User Home Opened. UserID: "+userId); // check point

        db = new DBHandler(this);

        displayCourses();
        getUserName(userId);

        welcomeUser = findViewById(R.id.welcomeUser);
        welcomeUser.setText("Welcome, "+userName);
    }

    public void getUserName(int id){
        userName = db.getUserNameById(id);
    }

    private void displayCourses() {
        List<Course> courses = db.getAllCourses();

        if(courses != null) {
            LinearLayout courseListLayout = findViewById(R.id.userCourseListLayout);
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
                        Intent intent = new Intent(UserHome.this, UserViewCourse.class);
                        intent.putExtra("courseId", courseId);
                        intent.putExtra("userId", userId);
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
}