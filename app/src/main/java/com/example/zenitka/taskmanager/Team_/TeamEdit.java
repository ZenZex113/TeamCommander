package com.example.zenitka.taskmanager.Team_;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.zenitka.taskmanager.R;

public class TeamEdit extends AppCompatActivity {

    Intent intent;

    public static final String EXTRA_REPLY = "com.example.zenitka.taskmanager.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_edit);
        intent = getIntent();
        if(intent.getStringExtra("requestcode").equals("update")) {
            Team task_old = new Team((Team) intent.getParcelableExtra("team"));
            EditText name_edit = findViewById(R.id.name_edit);
            name_edit.setText(task_old.name);
        }
    }

    public void onBackClick(View view) {
        finish();
    }

    public void onSaveClick(View view) {
        EditText name_edit = findViewById(R.id.name_edit);
        intent = getIntent();
        Team team = new Team();
        if(intent.getStringExtra("requestcode").equals("update")) {
            Team task_old = new Team((Team) intent.getParcelableExtra("team"));
            team.UID = task_old.UID;
        }
        team.name = name_edit.getText().toString();
        if(TextUtils.isEmpty(name_edit.getText())) {
            setResult(RESULT_CANCELED, intent);
        } else {
            intent.putExtra(EXTRA_REPLY, team);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
