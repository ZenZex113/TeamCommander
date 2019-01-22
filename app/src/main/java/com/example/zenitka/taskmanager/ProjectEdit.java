package com.example.zenitka.taskmanager;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ProjectEdit extends AppCompatActivity implements TeamTaskAdapter.ItemClickListener {

    Intent ttintent;
    List<TeamTask> ttasks = new ArrayList<>();
    TeamTaskAdapter adapter;

    public static final String EXTRA_REPLY = "com.example.zenitka.taskmanager.REPLY";

    public static final int NEW_TEAM_TASK_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_TEAM_TASK_ACTIVITY_REQUEST_CODE = 1;

    private TeamTaskViewModel mTeamTaskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_edit);
//        ttintent = getIntent();
//        if(intent.getStringExtra("requestcode").equals("update")) {
//            Team task_old = new Team((Team) intent.getParcelableExtra("team"));
//            EditText name_edit = findViewById(R.id.act);
//            name_edit.setText(task_old.name);
//    }
        setInitialData();
        RecyclerView recyclerView = findViewById(R.id.teamtaskrv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TeamTaskAdapter(ttasks);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ttintent = new Intent(ProjectEdit.this, TeamTaskEdit.class);
                ttintent.putExtra("requestcode", "insert");
                startActivityForResult(ttintent, NEW_TEAM_TASK_ACTIVITY_REQUEST_CODE);
            }
        });

        mTeamTaskViewModel = ViewModelProviders.of(this).get(TeamTaskViewModel.class);
        mTeamTaskViewModel.getAllTasksSortedByDate().observe(this, new Observer<List<TeamTask>>() {
            @Override
            public void onChanged(@Nullable final List<TeamTask> ttasks) {
                adapter.setTeamTasks(ttasks);
            }
        });

        adapter.mTeamTaskViewModel = mTeamTaskViewModel;
    }

    private void setInitialData() {
    }

    @Override
    public void onItemClick(int action, int position) {
        ttintent = new Intent(ProjectEdit.this, TeamTaskEdit.class);
        ttintent.putExtra("teamtask", adapter.getTeamTask(position));
        ttintent.putExtra("requestcode", "update");
        startActivityForResult(ttintent, UPDATE_TEAM_TASK_ACTIVITY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent TTdata) {
        super.onActivityResult(requestCode, resultCode, TTdata);

        if (resultCode == RESULT_OK) {
            TeamTask ttask = new TeamTask((TeamTask) Objects.requireNonNull(TTdata.getParcelableExtra(TeamTaskEdit.EXTRA_REPLY)));
            mTeamTaskViewModel.insert(ttask);
        }
    }

    public void onBackProjectEditClick(View view) {
        finish();
    }

    public void onSaveProjectEditClick(View view) {

    }
}

