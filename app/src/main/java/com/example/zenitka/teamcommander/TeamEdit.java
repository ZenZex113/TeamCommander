package com.example.zenitka.teamcommander;

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
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;


public class TeamEdit extends AppCompatActivity implements ProjectAdapter.ItemClickListener {

    Intent intent;
    Intent pintent;
    List<Project> projects = new ArrayList<>();
    ProjectAdapter adapter;

    private ProjectViewModel mProjectViewModel;

    public static final int NEW_TEAM_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_WORD_ACTIVITY_REQUEST_CODE = 2;
    public static final String EXTRA_REPLY = "com.example.zenitka.taskmanager.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_edit);

        intent = getIntent();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pintent = new Intent(TeamEdit.this, ProjectEdit.class);
                pintent.putExtra("requestcode", "insert");
                Team team = new Team((Team) intent.getParcelableExtra("team"));
                pintent.putExtra("parent", team);
                startActivityForResult(pintent, NEW_TEAM_ACTIVITY_REQUEST_CODE);
            }
        });
        if(intent.getStringExtra("requestcode").equals("update")) {
            Team task_old = new Team((Team) intent.getParcelableExtra("team"));
            EditText name_edit = findViewById(R.id.name_edit);
            name_edit.setText(task_old.name);
        }
        else {
            fab.hide();
        }
        setInitialData();
        RecyclerView recyclerView = findViewById(R.id.projectsrv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProjectAdapter(projects);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mProjectViewModel = ViewModelProviders.of(this).get(ProjectViewModel.class);
        if(intent.getStringExtra("requestcode").equals("update")) {
            Team team = new Team((Team) intent.getParcelableExtra("team"));
            mProjectViewModel.getTeamProjects(team.UID).observe(this, new Observer<List<Project>>() {
                @Override
                public void onChanged(@Nullable final List<Project> projects) {
                    adapter.setProjects(projects);
                }
            });
        }
        adapter.mProjectViewModel = mProjectViewModel;
    }

    private void setInitialData() {
    }

    @Override
    public void onItemClick(int action, int position) {
        pintent = new Intent(TeamEdit.this, ProjectEdit.class);
        pintent.putExtra("project", adapter.getProject(position));
        pintent.putExtra("requestcode", "update");
        Team team = new Team((Team) intent.getParcelableExtra("team"));
        pintent.putExtra("parent", team);
        startActivityForResult(pintent, UPDATE_WORD_ACTIVITY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Project project = new Project((Project) (data.getParcelableExtra(ProjectEdit.EXTRA_REPLY)));
            mProjectViewModel.insert(project);
        }
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
            intent.putExtra(EXTRA_REPLY, team);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
