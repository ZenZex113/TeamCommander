package com.example.zenitka.taskmanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zenitka.taskmanager.net.CodeID;
import com.example.zenitka.taskmanager.net.HelloApi;
import com.example.zenitka.taskmanager.net.NameDescription;
import com.example.zenitka.taskmanager.net.Network;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import static com.example.zenitka.taskmanager.RegLog.LoginActivity.SAVED_TOKEN;
import static com.example.zenitka.taskmanager.helpers.Errors.CODE_OK;
import static com.example.zenitka.taskmanager.helpers.Errors.ERRORS;


public class TeamEdit extends AppCompatActivity implements ProjectAdapter.ItemClickListener {

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

    List<Project> projects = new ArrayList<>();
    ProjectAdapter adapter;


    public static final int NEW_TEAM_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_WORD_ACTIVITY_REQUEST_CODE = 2;
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
        setInitialData();
        RecyclerView recyclerView = findViewById(R.id.projectsrv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProjectAdapter(projects);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeamEdit.this, ProjectEdit.class);
                intent.putExtra("requestcode", "insert");
                startActivityForResult(intent, NEW_TEAM_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    private void setInitialData() {
    }

    @Override
    public void onItemClick(int action, int position) {
        intent = new Intent(TeamEdit.this, ProjectEdit.class);
        intent.putExtra("teamtask", adapter.getProject(position));
        intent.putExtra("requestcode", "update");
        startActivityForResult(intent, UPDATE_WORD_ACTIVITY_REQUEST_CODE);
    }

    public void onBackTeamEditClick(View view) {
        finish();
    }

    public void onSaveTeamEditClick(View view) {
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
