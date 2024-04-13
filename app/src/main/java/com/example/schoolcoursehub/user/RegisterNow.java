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

public class RegisterNow extends AppCompatActivity {

    private int userId;
    private DBHandler db;

    private TextView totalFeeTextView, discountedTotalFeeTextView;
    private EditText promoCodeEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_now);

        userId = getIntent().getIntExtra("userId", -1);
        db = new DBHandler(this);

        totalFeeTextView = findViewById(R.id.totalFeeTextView);
        discountedTotalFeeTextView = findViewById(R.id.totalFeeTextView);
        promoCodeEditText = findViewById(R.id.passwordEditText);


        Button applyPromoCodeButton = findViewById(R.id.applyPromoCodeButton);
        applyPromoCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyPromoCode();
            }
        });

        Button payButton = findViewById(R.id.payButton);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payNow();
            }
        });
    }

    private void applyPromoCode() {

    }

    private void payNow(){

    }

}