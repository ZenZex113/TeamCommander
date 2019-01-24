package com.example.zenitka.taskmanager;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.zenitka.taskmanager.net.Code;
import com.example.zenitka.taskmanager.net.CodeID;
import com.example.zenitka.taskmanager.net.CodeListProject;
import com.example.zenitka.taskmanager.net.CodeListTask;
import com.example.zenitka.taskmanager.net.CodeListTeam;
import com.example.zenitka.taskmanager.net.HelloApi;
import com.example.zenitka.taskmanager.net.NameDateTeamIDInfo;
import com.example.zenitka.taskmanager.net.Network;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import static com.example.zenitka.taskmanager.RegLog.LoginActivity.SAVED_TOKEN;
import static com.example.zenitka.taskmanager.helpers.Errors.CODE_OK;
import static com.example.zenitka.taskmanager.helpers.Errors.ERRORS;


public class ProjectEdit extends AppCompatActivity implements TeamTaskAdapter.ItemClickListener {

    Intent ttintent;
    Intent tintent;
    List<TeamTask> ttasks = new ArrayList<>();
    Project project = new Project();
    TeamTaskAdapter adapter;

    SharedPreferences mSharedPreferences;

    HelloApi api = Network.getInstance().getApi();

    //.observeOn(AndroidSchedulers.mainThread())

    @SuppressLint("CheckResult")
    public void createProject(NameDateTeamIDInfo nameDateTeamIDInfo, final Project toPut, final Intent intent) {
        api.createProject(nameDateTeamIDInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CodeID>() {
                    @Override
                    public void accept(CodeID codeID) throws Exception {
                        System.out.println("Accepting...");
                        if (codeID.getCode() == CODE_OK) {
                            Toast.makeText(ProjectEdit.this, codeID.getID().toString(), Toast.LENGTH_SHORT).show();
                            toPut.id = codeID.getID();
                            intent.putExtra(EXTRA_REPLY, toPut);
                            setResult(RESULT_OK, intent);
                            System.out.println(toPut.id);
                            finish();
                        } else {
                            Toast.makeText(ProjectEdit.this, ERRORS[codeID.getCode()], Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.err.println(throwable.getMessage());
                        Toast.makeText(ProjectEdit.this, "Out of Internet!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void updateProject(Project project) {
        api.updateProject(project)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Code>() {
                    @Override
                    public void accept(Code code) throws Exception {
                        System.out.println("Accepting...");
                        if (code.getCode() == CODE_OK) {
                            finish();
                        } else {
                            Toast.makeText(ProjectEdit.this, ERRORS[code.getCode()], Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.err.println(throwable.getMessage());
                        Toast.makeText(ProjectEdit.this, "Out of Internet!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void loadTasksList(String token, int project_id) {
        api.loadTasksList(token, project_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CodeListTask>() {
                    @Override
                    public void accept(CodeListTask tasksCode) throws Exception {
                        if (tasksCode.getCode() == CODE_OK) {
                            mTeamTaskViewModel.deleteAll();
                            for (TeamTask ttask : tasksCode.getResponse()) {
                                ttask.parentUID = GLProject.UID;
                                System.err.println(project.name);
                                System.err.println(mTeamTaskViewModel);
                                mTeamTaskViewModel.insert(ttask);
                            }
                        } else {
                            Toast.makeText(ProjectEdit.this, ERRORS[tasksCode.getCode()], Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.err.println(throwable.getMessage());
                        Toast.makeText(ProjectEdit.this, "Out of internet!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    Project GLProject;

    TextView currentDateTime;
    Calendar dateAndTime = Calendar.getInstance();

    public static final String EXTRA_REPLY = "com.example.zenitka.taskmanager.REPLY";

    public static final int NEW_TEAM_TASK_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_TEAM_TASK_ACTIVITY_REQUEST_CODE = 1;

    private TeamTaskViewModel mTeamTaskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_edit);
        tintent = getIntent();

        Team team = tintent.getParcelableExtra("parent");
        TextView teamName = findViewById(R.id.teamName);
        teamName.setText(team.name);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ttintent = new Intent(ProjectEdit.this, TeamTaskEdit.class);
                Project project = new Project((Project) tintent.getParcelableExtra("project"));
                ttintent.putExtra("parentUID", "" + project.UID);
                ttintent.putExtra("project_id", "" + project.id);
                ttintent.putExtra("requestcode", "insert");
                startActivityForResult(ttintent, NEW_TEAM_TASK_ACTIVITY_REQUEST_CODE);
            }
        });

        FloatingActionButton fab_update = findViewById(R.id.fab_update);

        if(tintent.getStringExtra("requestcode").equals("update")) {
            final Project project_old = new Project((Project) tintent.getParcelableExtra("project"));
            EditText name_edit = findViewById(R.id.name_edit);
            name_edit.setText(project_old.name);
            fab_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSharedPreferences = getSharedPreferences("com.example.zenitka.taskmanager.token", MODE_PRIVATE);
                    loadTasksList(mSharedPreferences.getString(SAVED_TOKEN, "No token"), project_old.id);
                }
            });
        } else {
            fab.hide();
            fab_update.hide();
        }
        setInitialData();
        RecyclerView recyclerView = findViewById(R.id.teamtaskrv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TeamTaskAdapter(ttasks);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mTeamTaskViewModel = ViewModelProviders.of(this).get(TeamTaskViewModel.class);
        if(tintent.getStringExtra("requestcode").equals("update")) {
            GLProject = new Project((Project) tintent.getParcelableExtra("project"));
            mTeamTaskViewModel.getProjectTeamTasks(GLProject.UID).observe(this, new Observer<List<TeamTask>>() {
                @Override
                public void onChanged(@Nullable final List<TeamTask> ttasks) {
                    adapter.setTeamTasks(ttasks);
                }
            });
        }

        adapter.mTeamTaskViewModel = mTeamTaskViewModel;

        currentDateTime = findViewById(R.id.date_edit);
        setInitialDateTime();
    }

    public void setDate(View v) {
        new DatePickerDialog(ProjectEdit.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public void setTime(View v) {
        new TimePickerDialog(ProjectEdit.this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }

    private void setInitialDateTime() {

        currentDateTime.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
    }

    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };

    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    private void setInitialData() {
    }

    @Override
    public void onItemClick(int action, int position) {
        ttintent = new Intent(ProjectEdit.this, TeamTaskEdit.class);
        ttintent.putExtra("teamtask", adapter.getTeamTask(position));
        Project project = new Project((Project) tintent.getParcelableExtra("project"));
        ttintent.putExtra("parentUID", "" + project.UID);
        ttintent.putExtra("project_id", "" + project.id);
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
        EditText name_edit = findViewById(R.id.name_edit);
        TextView date_edit = findViewById(R.id.date_edit);
        tintent = getIntent();
        if(tintent.getStringExtra("requestcode").equals("update")) {
            Project project_old = new Project((Project) tintent.getParcelableExtra("project"));
            project.UID = project_old.UID;
            project.id = project_old.id;
        }
        project.date = date_edit.getText().toString();
        project.name = name_edit.getText().toString();
        Team team = tintent.getParcelableExtra("parent");
        project.parentUID = team.UID;
        project.parentUID = Integer.parseInt(tintent.getStringExtra("parentUID"));
        project.team_id = Integer.parseInt(tintent.getStringExtra("team_id"));
        project.info = " Info ";    //TODO
        if(TextUtils.isEmpty(name_edit.getText())) {
            setResult(RESULT_CANCELED,tintent);
        } else {
            if (tintent.getStringExtra("requestcode").equals("update")) {
                updateProject(project);
            } else {
                mSharedPreferences = getSharedPreferences("com.example.zenitka.taskmanager.token", MODE_PRIVATE);
                createProject(new NameDateTeamIDInfo(mSharedPreferences.getString(SAVED_TOKEN, "No token"), project.name, project.date, project.team_id, project.info), project, tintent);
            }
            //            tintent.putExtra(EXTRA_REPLY, project);
//            setResult(RESULT_OK, tintent);
//            finish();
        }

    }
}

