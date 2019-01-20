package com.example.zenitka.taskmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TeamDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_description);
    }

    public void onBackToTeamListClick(View view) {
        finish();
    }
}
