package com.example.schoolcoursehub.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.schoolcoursehub.R;
import com.example.schoolcoursehub.signupandlogin.LoginActivity;
import com.example.schoolcoursehub.user.UserHome;

public class AdminHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        int userId = getIntent().getIntExtra("userId", -1);

        System.out.println("Admin Home Opened. UserID: "+userId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle item selection
        if(item.getItemId()==R.id.new_course){
            addNewCourse();
        } else if(item.getItemId()==R.id.view_user){
            viewUserList();
        } else if (item.getItemId()==R.id.add_branch) {
            addBranch();
        }
        return true;
    }


    // TODO: implement below functions
    private void addNewCourse() {
        System.out.println("Add New Course Clicked");
        Intent intent = new Intent(AdminHome.this, AddNewCourse.class);
        startActivity(intent);
    }

    private void viewUserList() {
        System.out.println("View User Clicked");
    }

    private void addBranch() {
        System.out.println("Add Branch Clicked");
    }
}