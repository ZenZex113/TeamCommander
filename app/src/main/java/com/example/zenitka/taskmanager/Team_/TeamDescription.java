package com.example.zenitka.taskmanager.Team_;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.zenitka.taskmanager.R;

public class TeamDescription extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.zenitka.taskmanager.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_description);
    }

    public void onBackClick(View view) {
        finish();
    }

    public void onSaveClick(View view) {
        TextInputEditText name_edit = findViewById(R.id.name_edit);
        Intent intent = getIntent();
        Team team = new Team();
        team.name = name_edit.getText().toString();
        if(TextUtils.isEmpty(name_edit.getText())) {
            setResult(RESULT_CANCELED, intent);
        } else {
            intent.putExtra("team_back", team);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
