package com.example.zenitka.taskmanager;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.example.zenitka.taskmanager.net.Code;
import com.example.zenitka.taskmanager.net.CodeID;
import com.example.zenitka.taskmanager.net.CodeListProject;
import com.example.zenitka.taskmanager.net.CodeListTeam;
import com.example.zenitka.taskmanager.net.HelloApi;
import com.example.zenitka.taskmanager.net.NameDescription;
import com.example.zenitka.taskmanager.net.Network;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import static com.example.zenitka.taskmanager.RegLog.LoginActivity.SAVED_TOKEN;
import static com.example.zenitka.taskmanager.helpers.Errors.CODE_OK;
import static com.example.zenitka.taskmanager.helpers.Errors.ERRORS;


public class TeamEdit extends AppCompatActivity implements ProjectAdapter.ItemClickListener {

    SharedPreferences mSharedPreferences;

    Intent intent;
    Intent pintent;

    HelloApi api = Network.getInstance().getApi();

    //.observeOn(AndroidSchedulers.mainThread())

    @SuppressLint("CheckResult")
    public void createTeam(NameDescription nameDescription, final Team toPut, final Intent intent) {
        api.createTeam(nameDescription)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CodeID>() {
                    @Override
                    public void accept(CodeID codeID) throws Exception {
                        System.out.println("Accepting...");
                        if (codeID.getCode() == CODE_OK) {
                            Toast.makeText(TeamEdit.this, codeID.getID().toString(), Toast.LENGTH_SHORT).show();
                            toPut.id = codeID.getID();
                            intent.putExtra(EXTRA_REPLY, toPut);
                            setResult(RESULT_OK, intent);
                            System.out.println(toPut.id);
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

    @SuppressLint("CheckResult")
    public void updateTeam(Team team) {
        api.updateTeam(team)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Code>() {
                    @Override
                    public void accept(Code code) throws Exception {
                        System.out.println("Accepting...");
                        if (code.getCode() == CODE_OK) {
                            finish();
                        } else {
                            Toast.makeText(TeamEdit.this, ERRORS[code.getCode()], Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.err.println(throwable.getMessage());
                        Toast.makeText(TeamEdit.this, "Out of Internet!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void loadProjectsList(String token, int team_id) {
        api.loadProjectsList(token, team_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CodeListProject>() {
                    @Override
                    public void accept(CodeListProject projectsCode) throws Exception {
                        if (projectsCode.getCode() == CODE_OK) {
                            mProjectViewModel.deleteAll();
                            for (Project project : projectsCode.getResponse()) {
                                project.parentUID = GLteam.UID;
                                System.err.println(project.name);
                                System.err.println(mProjectViewModel);
                                mProjectViewModel.insert(project);
                            }
                        } else {
                            Toast.makeText(TeamEdit.this, ERRORS[projectsCode.getCode()], Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.err.println(throwable.getMessage());
                        Toast.makeText(TeamEdit.this, "Out of internet!", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    List<Project> projects = new ArrayList<>();
    ProjectAdapter adapter;

    private ProjectViewModel mProjectViewModel;

    public static final int NEW_TEAM_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_WORD_ACTIVITY_REQUEST_CODE = 2;
    public static final String EXTRA_REPLY = "com.example.zenitka.taskmanager.REPLY";

    Team GLteam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_edit);

        intent = getIntent();

        setInitialData();
        RecyclerView recyclerView = findViewById(R.id.projectsrv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProjectAdapter(projects);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mProjectViewModel = ViewModelProviders.of(this).get(ProjectViewModel.class);

        if(intent.getStringExtra("requestcode").equals("update")) {
            GLteam = new Team((Team) intent.getParcelableExtra("team"));
            mProjectViewModel.getTeamProjects(GLteam.UID).observe(this, new Observer<List<Project>>() {
                @Override
                public void onChanged(@Nullable final List<Project> projects) {
                    adapter.setProjects(projects);
                }
            });
        }
        adapter.mProjectViewModel = mProjectViewModel;

        FloatingActionButton fab = findViewById(R.id.fab);
        FloatingActionButton fab_update = findViewById(R.id.fab_update);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pintent = new Intent(TeamEdit.this, ProjectEdit.class);
                pintent.putExtra("requestcode", "insert");
                Team team = new Team((Team) intent.getParcelableExtra("team"));
                pintent.putExtra("parentUID", "" + team.UID);
                pintent.putExtra("team_id", "" + team.id);
                pintent.putExtra("parent", team);
                startActivityForResult(pintent, NEW_TEAM_ACTIVITY_REQUEST_CODE);
            }
        });
        if (intent.getStringExtra("requestcode").equals("update")) {
            final Team task_old = new Team((Team) intent.getParcelableExtra("team"));
            EditText name_edit = findViewById(R.id.name_edit);
            name_edit.setText(task_old.name);
            fab_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSharedPreferences = getSharedPreferences("com.example.zenitka.taskmanager.token", MODE_PRIVATE);
                    loadProjectsList(mSharedPreferences.getString(SAVED_TOKEN, "No token"), task_old.id);
                }
            });
        }
        else {
            fab.hide();
            fab_update.hide();
        }
    }

    private void setInitialData() {
    }

    @Override
    public void onItemClick(int action, int position) {
        pintent = new Intent(TeamEdit.this, ProjectEdit.class);
        pintent.putExtra("project", adapter.getProject(position));
        pintent.putExtra("requestcode", "update");
        Team team = new Team((Team) intent.getParcelableExtra("team"));
        pintent.putExtra("parentUID", "" + team.UID);
        pintent.putExtra("team_id", "" + team.id);
        pintent.putExtra("parent", team);
        startActivityForResult(pintent, UPDATE_WORD_ACTIVITY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Project project = new Project((Project) (data.getParcelableExtra(ProjectEdit.EXTRA_REPLY)));
            System.err.println(mProjectViewModel);
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
        if (intent.getStringExtra("requestcode").equals("update")) {
            Team task_old = new Team((Team) intent.getParcelableExtra("team"));
            team.UID = task_old.UID;
            team.id = task_old.id;
        }
        team.name = name_edit.getText().toString();
        if (TextUtils.isEmpty(name_edit.getText())) {
            setResult(RESULT_CANCELED, intent);
        } else {
            if (intent.getStringExtra("requestcode").equals("update")) {
                updateTeam(team);
            } else {
                mSharedPreferences = getSharedPreferences("com.example.zenitka.taskmanager.token", MODE_PRIVATE);
                createTeam(new NameDescription(mSharedPreferences.getString(SAVED_TOKEN, "No token"), team.name, "TODO"), team, intent);
            }
        }
    }
}
