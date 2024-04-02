package com.example.schoolcoursehub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.schoolcoursehub.signupandlogin.LoginActivity;
import com.example.schoolcoursehub.signupandlogin.RegistrationActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onRegisterButtonClick(View view) {
        // Handle register button click
        System.out.println("Register Button Clicked");

        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    public void onLoginButtonClick(View view) {
        // Handle login button click
        System.out.println("Login Button Clicked");

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}