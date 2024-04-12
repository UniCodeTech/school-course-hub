package com.example.schoolcoursehub.signupandlogin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolcoursehub.MainActivity;
import com.example.schoolcoursehub.R;
import com.example.schoolcoursehub.emailsender.Email;
import com.example.schoolcoursehub.emailsender.GMailSender;
import com.example.schoolcoursehub.emailsender.SendMailTask;
import com.example.schoolcoursehub.helper.DBHandler;

import java.util.Calendar;
import java.util.Random;

public class RegistrationActivity extends AppCompatActivity {

    private DBHandler dbHandler;

    private ImageView profileImageView;
    private EditText nameEditText, addressEditText, cityEditText, dobEditText, nicEditText,
            emailEditText, phoneEditText, passwordEditText, confirmPasswordEditText;
    private Button uploadButton, registerButton;
    private TextView errorTextView;
    private Spinner genderSpinner;
    private Calendar dobCalendar;
    private static final int PICK_IMAGE_REQUEST = 1;

    private String profileImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        dbHandler = new DBHandler(this);

        nameEditText = findViewById(R.id.nameEditText);
        addressEditText = findViewById(R.id.addressEditText);
        cityEditText = findViewById(R.id.cityEditText);
        dobEditText = findViewById(R.id.dobEditText);
        nicEditText = findViewById(R.id.nicEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        dobCalendar = Calendar.getInstance();
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);

        uploadButton = findViewById(R.id.uploadButton);
        registerButton = findViewById(R.id.registerButton);

        errorTextView = findViewById(R.id.errorTextView);

        genderSpinner = findViewById(R.id.genderSpinner);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateFields()){
                    String email = emailEditText.getText().toString().trim();
                    String verifyCode = generateVerificationCode();
                    if(sendVerificationEmail(email, verifyCode)){ // send email
                        showVerificationDialog(verifyCode);
                    } else {
                        Toast.makeText(RegistrationActivity.this, "Please try again later!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Choose Profile Picture
            }
        });
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

    //generate a verification code
    private String generateVerificationCode() {
        // Generate a random 6-digit code
        Random random = new Random();
        int verificationCode = 100000 + random.nextInt(900000);
        return String.valueOf(verificationCode);
    }

    // send an email
    private boolean sendVerificationEmail(String emailAddress, String verificationCode) {
        Email email = new Email();

        String sender = email.getEmail();
        String pass = email.getPassword();

        try {
            String verificationEmailSubject = getString(R.string.verification_email_subject);
            String verificationEmailBody = getString(R.string.verification_email_body, verificationCode);

            GMailSender gmailSender = new GMailSender(sender, pass);
            SendMailTask sendMailTask = new SendMailTask(gmailSender,
                    verificationEmailSubject,
                    verificationEmailBody,
                    pass,
                    emailAddress) {
                @Override
                protected void onPostExecute(Boolean result) {
                    if (result) {
                        // Email sent successfully
                        Toast.makeText(RegistrationActivity.this, "Verification Email send. Check you email.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Failed to send email
                        Toast.makeText(RegistrationActivity.this, "Failed to send email", Toast.LENGTH_SHORT).show();
                    }
                }
            };
            sendMailTask.execute();
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);

        }
        return true;
    }

    // Method to display a dialog for entering the verification code
    private void showVerificationDialog(final String verificationCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Verification Code");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Verify", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String enteredCode = input.getText().toString().trim();

                if (enteredCode.equals(verificationCode)) {
                    // Insert user data into the database
                    insertUserDataIntoDatabase();

                } else {
                    // Display an error message
                    Toast.makeText(RegistrationActivity.this, "Incorrect verification code. Please try again.", Toast.LENGTH_SHORT).show();
                    showVerificationDialog(verificationCode); // Show the dialog again for retry
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Show the dialog
        builder.show();
    }

    public void insertUserDataIntoDatabase(){
        String name = nameEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String city = cityEditText.getText().toString().trim();
        String dob = dobEditText.getText().toString().trim();
        String nic = nicEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String selectedGender = genderSpinner.getSelectedItem().toString();
        String role = "User";

        // Insert user data into database
        boolean newRowId = dbHandler.insertUser(name, address, city, dob, nic, email, selectedGender, phone, password, profileImagePath, role);

        if (newRowId) {
            // Insertion successful
            Toast.makeText(RegistrationActivity.this, "Registration successful!.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            finish();
        } else {
            // Insertion failed
            Toast.makeText(RegistrationActivity.this, "Registration failed!. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }



    private boolean validateFields() {
        errorTextView.setText("");
        boolean isValid = true;

        String name = nameEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String city = cityEditText.getText().toString().trim();
        String dob = dobEditText.getText().toString().trim();
        String nic = nicEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        String selectedGender = genderSpinner.getSelectedItem().toString();

        // Validate name
        if (name.isEmpty()) {
            nameEditText.setError("Please enter your name");
            errorTextView.setText("Please enter your name");
            isValid = false;
        }

        // Validate address
        else if (address.isEmpty()) {
            addressEditText.setError("Please enter your address");
            errorTextView.setText("Please enter your address");
            isValid = false;
        }

        // Validate city
        else if (city.isEmpty()) {
            cityEditText.setError("Please enter your city");
            errorTextView.setText("Please enter your city");
            isValid = false;
        }

        // Validate date of birth
        else if (dob.isEmpty()) {
            dobEditText.setError("Please select your date of birth");
            errorTextView.setText("Please select your date of birth");
            isValid = false;
        }

        // Validate NIC
        else if (nic.isEmpty()) {
            nicEditText.setError("Please enter your NIC");
            errorTextView.setText("Please enter your NIC");
            isValid = false;
        }

        else if (!idUniqueNIC(nic)){
            nicEditText.setError("NIC is already taken");
            errorTextView.setText("NIC is already taken");
            isValid = false;
        }
        else if (selectedGender == null && selectedGender.isEmpty()) {
            errorTextView.setText("Please select a gender");
            errorTextView.setText("Please select a gender");
            isValid = false;
        }

        // Validate email
        else if (email.isEmpty()) {
            emailEditText.setError("Please enter your email");
            errorTextView.setText("Please enter your email");
            isValid = false;
        } else if (!isValidEmail(email)) {
            emailEditText.setError("Please enter a valid email");
            errorTextView.setText("Please enter a valid email");
            isValid = false;
        }

        // Validate phone number
        else if (phone.isEmpty()) {
            phoneEditText.setError("Please enter your phone number");
            errorTextView.setText("Please enter your phone number");
            isValid = false;
        } else if (!isValidPhoneNumber(phone)) {
            phoneEditText.setError("Please enter a valid phone number");
            errorTextView.setText("Please enter a valid phone number");
            isValid = false;
        }

        // Validate password
        else if (password.isEmpty()) {
            passwordEditText.setError("Please enter a password");
            errorTextView.setText("Please enter a password");
            isValid = false;
        }

        // Validate confirm password
        else if (confirmPassword.isEmpty()) {
            confirmPasswordEditText.setError("Please confirm your password");
            errorTextView.setText("Please confirm your password");
            isValid = false;
        } else if (!confirmPassword.equals(password)) {
            confirmPasswordEditText.setError("Passwords do not match");
            errorTextView.setText("Passwords do not match");
            isValid = false;
        }

        return isValid;
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPhoneNumber(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    private boolean idUniqueNIC(String nic){
        return dbHandler.isUniqueNIC(nic);
    }

}