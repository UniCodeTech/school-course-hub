package com.example.schoolcoursehub.signupandlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolcoursehub.R;
import com.example.schoolcoursehub.admin.AdminHome;
import com.example.schoolcoursehub.helper.DBHandler;
import com.example.schoolcoursehub.helper.UserInfo;

public class LoginActivity extends AppCompatActivity {

    private DBHandler dbHandler;
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView error;

    private TextView adminLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        System.out.println("Login Activity opened");
        dbHandler = new DBHandler(this);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        error = findViewById(R.id.error);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the email text from EditText
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Check if the email is empty
                if (email.isEmpty()) {
                    error.setVisibility(View.VISIBLE);
                    error.setText("Email is required");
                    emailEditText.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    error.setVisibility(View.VISIBLE);
                    error.setText("Password is required");
                    passwordEditText.requestFocus();
                    return;
                }
                if(login(email, password)){
                    System.out.println("Login method");

                    UserInfo userInfo = dbHandler.getUserInfo(email, password);
                    int userId = userInfo.getUserId();
                    String role = userInfo.getRole();
                    System.out.println("UserInfo: userID: "+userId+ " role: "+role);

                    if(role.equals("Admin")){
                        System.out.println("Admin");
                        Toast.makeText(LoginActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, AdminHome.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                        finish();
                    } else if (role.equals("User")) {
                        System.out.println("User");
                        Toast.makeText(LoginActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Try Again Later!", Toast.LENGTH_SHORT).show();
                        emailEditText.setText("");
                        passwordEditText.setText("");
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Incorrect Email or Password.", Toast.LENGTH_SHORT).show();
                    emailEditText.setText("");
                    passwordEditText.setText("");
                }
            }
        });

    }

    public boolean login(String email, String password){
        return dbHandler.login(email, password);
    }
}