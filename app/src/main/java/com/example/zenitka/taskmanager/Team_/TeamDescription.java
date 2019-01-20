package com.example.zenitka.taskmanager.Team_;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.zenitka.taskmanager.R;

public class TeamDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_description);
    }

    public void onBackClick(View view) {
        finish();
    }
}
