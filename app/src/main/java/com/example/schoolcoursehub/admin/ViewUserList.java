package com.example.schoolcoursehub.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.schoolcoursehub.R;
import com.example.schoolcoursehub.helper.DBHandler;

import java.util.ArrayList;
import java.util.List;

public class ViewUserList extends AppCompatActivity {

    private ListView listViewUsers;
    private ArrayAdapter<String> adapter;
    private List<String> userList;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_list);

        listViewUsers = findViewById(R.id.listViewUsers);
        userList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        listViewUsers.setAdapter(adapter);
        dbHandler = new DBHandler(this);

        fetchUserDetails();
    }

    private void fetchUserDetails() {
        List<String> userDetailsList = dbHandler.getAllUsers();

        userList.clear();

        if (userDetailsList.isEmpty()) {
            userList.add("No users found");
        } else {
            userList.addAll(userDetailsList);
        }

        adapter.notifyDataSetChanged();
    }
}
