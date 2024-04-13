package com.example.schoolcoursehub.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolcoursehub.R;
import com.example.schoolcoursehub.emailsender.Email;
import com.example.schoolcoursehub.emailsender.GMailSender;
import com.example.schoolcoursehub.emailsender.SendMailTask;
import com.example.schoolcoursehub.helper.Course;
import com.example.schoolcoursehub.helper.CourseAdapter;
import com.example.schoolcoursehub.helper.DBHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RegisterNow extends AppCompatActivity {

    private int userId, courseId;
    private int promotionId = -1;
    private DBHandler db;

    private TextView totalFeeTextView, discountedTotalFeeTextView, discountTextView,
            courseTitleTextView, courseDescriptionTextView;
    private EditText promoCodeEditText;

    private double courseFee, discountAmt, discountedTotalFee, discount = 0.0;
    double totalFeeAfterDiscount = 0.0;
    private String courseName;
    private Button applyPromoCodeButton, payButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_now);

        userId = getIntent().getIntExtra("userId", -1);
        courseId = getIntent().getIntExtra("courseId", -1);
        db = new DBHandler(this);

        totalFeeTextView = findViewById(R.id.totalFeeTextView);
        discountedTotalFeeTextView = findViewById(R.id.discountedTotalFeeTextView);
        courseTitleTextView = findViewById(R.id.courseTitleTextView);
        courseDescriptionTextView = findViewById(R.id.courseDescriptionTextView);
        discountTextView = findViewById(R.id.discountTextView);
        promoCodeEditText = findViewById(R.id.promoCodeEditText);


        applyPromoCodeButton = findViewById(R.id.applyPromoCodeButton);
        payButton = findViewById(R.id.payButton);

        Toast.makeText(this, "CourseID: "+courseId, Toast.LENGTH_SHORT).show();
        displayCourseDetails();

        applyPromoCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyPromoCode();
            }
        });

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payNow();
            }
        });
    }

    public void displayCourseDetails() {
        Course course = db.fetchCourseDetails(courseId);

        if (course != null) {
            if (isRegistrationClosed(course.getRegistrationClosingDate())) {
                // Disable buttons
                applyPromoCodeButton.setEnabled(false);
                payButton.setEnabled(false);

                courseTitleTextView.setText(course.getCourseName());
                courseDescriptionTextView.setText("Course registration has closed");
                totalFeeTextView.setText("");
                discountTextView.setText("");
                discountedTotalFeeTextView.setText("Course registration has closed");

                Toast.makeText(this, "Course registration has closed.", Toast.LENGTH_SHORT).show();
            } else if (isMaxParticipantsReached(course.getCurrentEnrollment(), course.getMaxParticipants())) {
                applyPromoCodeButton.setEnabled(false);
                payButton.setEnabled(false);

                courseTitleTextView.setText(course.getCourseName());
                courseDescriptionTextView.setText("Course reached maximum participants limit.");
                totalFeeTextView.setText("");
                discountTextView.setText("");
                discountedTotalFeeTextView.setText("Course reached maximum participants limit.");

                Toast.makeText(this, "Course reached maximum participants limit.", Toast.LENGTH_SHORT).show();
            } else {
                String courseDesc = "This course, titled " + course.getCourseName() + ", spans " + course.getCourseDuration() + "." +
                        "It costs " + course.getCourseCost() + " and accepts up to " + course.getMaxParticipants() + " participants. " +
                        "Starting on " + course.getStartingDate() + ", registration closes on " +
                        "" + course.getRegistrationClosingDate() + ". Published on " + course.getPublishDate() + ". " +
                        "Currently, " + course.getCurrentEnrollment() + " participants are enrolled";

                courseName = course.getCourseName();

                courseFee = course.getCourseCost();
                discountedTotalFee = courseFee - discountAmt;

                courseTitleTextView.setText(course.getCourseName());
                courseDescriptionTextView.setText(courseDesc);
                totalFeeTextView.setText("Course Fee: Rs." + String.valueOf(courseFee));
                discountTextView.setText("");
                discountedTotalFeeTextView.setText("Discounted Total Fee: Rs." + String.valueOf(discountedTotalFee));

            }
        } else {
            Toast.makeText(this, "Please try again later!", Toast.LENGTH_SHORT).show();
        }
    }

    private void applyPromoCode() {
        EditText promoCodeEditText = findViewById(R.id.promoCodeEditText);

        if (promoCodeEditText != null) {
            String promoCode = promoCodeEditText.getText().toString();

            discount = db.getDiscountPercentageForPromoCode(promoCode);

            if (discount > 0.0) {
                discountAmt = courseFee * (discount / 100);

                discountTextView.setText("You got " + discount + "% discount");
                discountedTotalFee = courseFee - discountAmt;
                discountedTotalFeeTextView.setText("Discounted Total Fee: Rs." + String.valueOf(discountedTotalFee));
            } else {
                Toast.makeText(this, "Invalid or Expired Promo Code.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please Enter a Promo Code.", Toast.LENGTH_SHORT).show();
        }
    }


    private void payNow() {
        String registrationDate = getCurrentDateTime();

        String promoCode = promoCodeEditText.getText().toString();
        double discount = 0.0;
        discount = this.discount;

        totalFeeAfterDiscount = courseFee - discountAmt;

        long result;
        boolean increment;
        if (promoCode != null && !promoCode.isEmpty()) {
            result = db.insertCourseUser(courseId, userId, registrationDate, promoCode, discount, totalFeeAfterDiscount);
            increment = db.incrementCurrentEnrollment(courseId);
        } else {
            result = db.insertCourseUser(courseId, userId, registrationDate, null, discount, totalFeeAfterDiscount);
            increment = db.incrementCurrentEnrollment(courseId);
        }

        if (result != -1 && increment) {
            if (sendConfirmEmail()) {
                Toast.makeText(this, "Payment successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterNow.this, UserHome.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
                finish();
            }

        } else {
            Toast.makeText(this, "Payment failed. Please try again later!", Toast.LENGTH_SHORT).show();
        }
    }

    private String getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(currentDate);
    }

    private boolean isRegistrationClosed(String registrationClosingDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date closingDate = dateFormat.parse(registrationClosingDate);
            Date currentDate = new Date();
            return currentDate.after(closingDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isMaxParticipantsReached(int currentEnrollment, int maxParticipants) {
        return currentEnrollment >= maxParticipants;
    }

    public boolean sendConfirmEmail() {
        String emailAddress = db.getUserEmailAddress(userId);

        if (emailAddress == null || emailAddress.isEmpty()) {
            Log.e("SendConfirmEmail", "User email address is null or empty.");
            return false;
        }

        Email email = new Email();
        String sender = email.getEmail();
        String pass = email.getPassword();

        try {
            String confirmationEmailSubject = "Payment Confirmation for " + courseName;
            String confirmationEmailBody = "Dear User,\n\nThank you for your payment. You have successfully registered for the course \"" + courseName + "\". " +
                    "The total fee is Rs." + totalFeeAfterDiscount + ". Please keep this email as a payment receipt.\n\nBest regards,\nThe School Course Hub Team";

            GMailSender gmailSender = new GMailSender(sender, pass);
            SendMailTask sendMailTask = new SendMailTask(gmailSender,
                    confirmationEmailSubject,
                    confirmationEmailBody,
                    pass,
                    emailAddress) {
                @Override
                protected void onPostExecute(Boolean result) {
                    if (result) {
                        // Email sent successfully
                        Log.d("SendConfirmEmail", "Confirmation email sent successfully.");
                    } else {
                        // Failed to send email
                        Log.e("SendConfirmEmail", "Failed to send confirmation email.");
                    }
                }
            };
            sendMailTask.execute();
            return true;
        } catch (Exception e) {
            Log.e("SendConfirmEmail", e.getMessage(), e);
            return false;
        }
    }
}