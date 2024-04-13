package com.example.schoolcoursehub.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolcoursehub.R;
import com.example.schoolcoursehub.helper.Course;
import com.example.schoolcoursehub.helper.CourseAdapter;
import com.example.schoolcoursehub.helper.DBHandler;

import java.util.ArrayList;
import java.util.List;

public class RegisterNow extends AppCompatActivity implements CourseAdapter.OnCourseCheckedChangeListener {

    private int userId;
    private RecyclerView recyclerView;
    private EditText promoCodeEditText;
    private TextView totalFeeTextView;
    private CourseAdapter adapter;
    private List<Course> courses = new ArrayList<>();
    private double totalFee = 0.0;
    private String promoCode;
    private DBHandler db;
    double discountPercentage = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_now);

        userId = getIntent().getIntExtra("userId", -1);

        Toast.makeText(this, "Register now open: "+userId, Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.recyclerView);
        promoCodeEditText = findViewById(R.id.promoCodeEditText);
        totalFeeTextView = findViewById(R.id.totalFeeTextView);

        db = new DBHandler(this);

        fetchCourseDetailsFromDatabase();

        adapter = new CourseAdapter(courses, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Button applyPromoCodeButton = findViewById(R.id.applyPromoCodeButton);
        applyPromoCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyPromoCode();
            }
        });
    }

    private void applyPromoCode() {
        promoCode = promoCodeEditText.getText().toString().trim();

        discountPercentage = db.getDiscountPercentageForPromoCode(promoCode);

        if (discountPercentage > 0) {
            double discountedTotalFee = totalFee - (totalFee * (discountPercentage / 100.0));

            totalFeeTextView.setText("Total Fee: Rs." + String.format("%.2f", discountedTotalFee));
        } else {
            Toast.makeText(this, "Invalid or expired promo code", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchCourseDetailsFromDatabase() {
        DBHandler dbHandler = new DBHandler(this);
        courses = dbHandler.getAllCourses();
    }



    @Override
    public void onCourseCheckedChanged(double courseFee) {
        if (courseFee > 0) {
            totalFee += courseFee;
        } else {
            for (Course course : courses) {
                if (course.getCourseCost() == Math.abs(courseFee)) {
                    totalFee -= course.getCourseCost();
                    break;
                }
            }
        }

        totalFeeTextView.setText("Total Fee: Rs." + totalFee);
    }



}