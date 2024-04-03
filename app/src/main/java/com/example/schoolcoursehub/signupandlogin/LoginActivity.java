package com.example.schoolcoursehub.signupandlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.schoolcoursehub.R;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button loginButton;
    private TextView error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        loginButton = findViewById(R.id.loginButton);
        error = findViewById(R.id.error);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the email text from EditText
                String email = emailEditText.getText().toString().trim();

                // Check if the email is empty
                if (email.isEmpty()) {
                    error.setVisibility(View.VISIBLE);
                    error.setText("Email is required");
                    emailEditText.requestFocus();
                    return;
                }
                // TODO: implement email verify logic
            }
        });
    }
}