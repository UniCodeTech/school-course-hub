package com.example.schoolcoursehub.signupandlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.schoolcoursehub.R;

import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity {

    private EditText dobEditText;
    private Calendar dobCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        dobEditText = findViewById(R.id.dobEditText);
        dobCalendar = Calendar.getInstance();

        dobEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }

    private void showDatePicker() {
        int year = dobCalendar.get(Calendar.YEAR);
        int month = dobCalendar.get(Calendar.MONTH);
        int dayOfMonth = dobCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dobEditText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                },
                year, month, dayOfMonth);

        datePickerDialog.show();
    }
}