package com.example.zenitka.teamcommander;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;


public class ProjectEdit extends AppCompatActivity implements TeamTaskAdapter.ItemClickListener {

    Intent ttintent;
    Intent tintent;
    List<TeamTask> ttasks = new ArrayList<>();
    Project project = new Project();
    TeamTaskAdapter adapter;

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
                ttintent.putExtra("requestcode", "insert");
                startActivityForResult(ttintent, NEW_TEAM_TASK_ACTIVITY_REQUEST_CODE);
            }
        });

        if(tintent.getStringExtra("requestcode").equals("update")) {
            Project project_old = new Project((Project) tintent.getParcelableExtra("project"));
            EditText name_edit = findViewById(R.id.name_edit);
            name_edit.setText(project_old.name);
        } else {
            fab.hide();
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
            Project project = new Project((Project) tintent.getParcelableExtra("project"));
            mTeamTaskViewModel.getProjectTeamTasks(project.UID).observe(this, new Observer<List<TeamTask>>() {
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
        }
        project.date = date_edit.getText().toString();
        project.name = name_edit.getText().toString();
        Team team = tintent.getParcelableExtra("parent");
        project.parentUID = team.UID;
        if(TextUtils.isEmpty(name_edit.getText())) {
            setResult(RESULT_CANCELED,tintent);
        } else {
            tintent.putExtra(EXTRA_REPLY, project);
            setResult(RESULT_OK, tintent);
            finish();
        }

    }
}

