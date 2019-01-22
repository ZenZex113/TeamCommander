package com.example.zenitka.taskmanager.Team_;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zenitka.taskmanager.R;

public class TeamDescription extends AppCompatActivity {



    public static final String EXTRA_REPLY = "com.example.zenitka.taskmanager.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_description);
    }
}
