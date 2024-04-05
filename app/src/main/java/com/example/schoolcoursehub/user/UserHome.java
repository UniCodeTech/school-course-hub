package com.example.schoolcoursehub.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.schoolcoursehub.R;

public class UserHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        int userId = getIntent().getIntExtra("userId", -1);

        System.out.println("User Home Opened. UserID: "+userId); // check point
    }
}