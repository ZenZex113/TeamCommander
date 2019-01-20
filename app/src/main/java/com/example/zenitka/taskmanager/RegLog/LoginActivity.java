package com.example.zenitka.taskmanager.RegLog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.zenitka.taskmanager.TaskList;
import com.example.zenitka.taskmanager.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }

    public void onLoginBackClick(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void onBackClick(View view) {
        Intent intent = new Intent(LoginActivity.this, TaskList.class);
        startActivity(intent);
    }
}
