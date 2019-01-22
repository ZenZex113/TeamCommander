package com.example.zenitka.taskmanager.Team_;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zenitka.taskmanager.Project_.ProjectList;
import com.example.zenitka.taskmanager.R;
import com.example.zenitka.taskmanager.net.CodeID;
import com.example.zenitka.taskmanager.net.HelloApi;
import com.example.zenitka.taskmanager.net.NameDescription;
import com.example.zenitka.taskmanager.net.Network;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import static com.example.zenitka.taskmanager.RegLog.LoginActivity.SAVED_TOKEN;
import static com.example.zenitka.taskmanager.helpers.Errors.CODE_OK;
import static com.example.zenitka.taskmanager.helpers.Errors.ERRORS;

public class TeamEdit extends AppCompatActivity {

    SharedPreferences mSharedPreferences;

    Intent intent;

    HelloApi api = Network.getInstance().getApi();

    //.observeOn(AndroidSchedulers.mainThread())

    @SuppressLint("CheckResult")
    public void createTeam(NameDescription nameDescription) {
        api.createTeam(nameDescription)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CodeID>() {
                    @Override
                    public void accept(CodeID codeID) throws Exception {
                        System.out.println("Accepting...");
                        if (codeID.getCode() == CODE_OK) {
                            Toast.makeText(TeamEdit.this, codeID.getID().toString(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TeamEdit.this, ProjectList.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(TeamEdit.this, ERRORS[codeID.getCode()], Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(TeamEdit.this, "Out of Internet!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

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
            mSharedPreferences = getSharedPreferences("com.example.zenitka.taskmanager.token", MODE_PRIVATE);
            createTeam(new NameDescription(mSharedPreferences.getString(SAVED_TOKEN, "No token"), team.name, "TODO"));
            intent.putExtra(EXTRA_REPLY, team);
            setResult(RESULT_OK, intent);
            }
    }
}
