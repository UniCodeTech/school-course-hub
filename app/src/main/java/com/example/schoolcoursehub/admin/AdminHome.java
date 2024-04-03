package com.example.schoolcoursehub.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.schoolcoursehub.R;

public class AdminHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
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

    }

    private void viewUserList() {
    }

    private void addBranch() {
    }
}