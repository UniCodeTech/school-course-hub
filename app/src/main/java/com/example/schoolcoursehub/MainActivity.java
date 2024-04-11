package com.example.schoolcoursehub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.schoolcoursehub.admin.AdminHome;
import com.example.schoolcoursehub.helper.DBHandler;
import com.example.schoolcoursehub.signupandlogin.LoginActivity;
import com.example.schoolcoursehub.signupandlogin.RegistrationActivity;

import java.io.File;

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

        Intent intent = new Intent(MainActivity.this, AdminHome.class);
        intent.putExtra("userId", 1);
        startActivity(intent);

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