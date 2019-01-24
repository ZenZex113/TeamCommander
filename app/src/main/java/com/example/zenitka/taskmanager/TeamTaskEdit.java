package com.example.zenitka.teamcommander;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.zenitka.taskmanager.net.HelloApi;
import com.example.zenitka.taskmanager.net.NameDateTeamIDInfo;
import com.example.zenitka.taskmanager.net.Network;

import java.util.Calendar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import static com.example.zenitka.taskmanager.RegLog.LoginActivity.SAVED_TOKEN;
import static com.example.zenitka.taskmanager.helpers.Errors.CODE_OK;
import static com.example.zenitka.taskmanager.helpers.Errors.ERRORS;

public class TeamTaskEdit extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.zenitka.taskmanager.REPLY";

    public int parent_UID_true = -1;

    TeamTask ttask = new TeamTask();

    Intent intent;

    SharedPreferences mSharedPreferences;

    HelloApi api = Network.getInstance().getApi();

    //.observeOn(AndroidSchedulers.mainThread())

    @SuppressLint("CheckResult")
    public void createTeamTask(final TeamTask toPut) {
        api.createTask(toPut)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CodeID>() {
                    @Override
                    public void accept(CodeID codeID) throws Exception {
                        System.out.println("Accepting...");
                        if (codeID.getCode() == CODE_OK) {
                            Toast.makeText(TeamTaskEdit.this, codeID.getID().toString(), Toast.LENGTH_SHORT).show();
                            toPut.id = codeID.getID();
                            toPut.parentUID = parent_UID_true;

                            intent.putExtra(EXTRA_REPLY, toPut);
                            setResult(RESULT_OK, intent);
                            System.out.println(toPut.id);
                            finish();
                        } else {
                            Toast.makeText(TeamTaskEdit.this, ERRORS[codeID.getCode()], Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.err.println(throwable.getMessage());
                        Toast.makeText(TeamTaskEdit.this, "Out of Internet!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void updateTeamTask(TeamTask teamTask) {
        api.updateTeamTask(teamTask)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Code>() {
                    @Override
                    public void accept(Code code) throws Exception {
                        System.out.println("Accepting...");
                        if (code.getCode() == CODE_OK) {
                            finish();
                        } else {
                            Toast.makeText(TeamTaskEdit.this, ERRORS[code.getCode()], Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.err.println(throwable.getMessage());
                        Toast.makeText(TeamTaskEdit.this, "Out of Internet!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    TextView currentDateTime;
    Calendar dateAndTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_task_edit);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        intent = getIntent();
        parent_UID_true = Integer.parseInt(intent.getStringExtra("parentUID"));
        if (intent.getStringExtra("requestcode").equals("update")) {
            TeamTask ttask_edit = new TeamTask((TeamTask) intent.getParcelableExtra("teamtask"));
            TextInputEditText name_edit = findViewById(R.id.name_edit);
            EditText desc_edit = findViewById(R.id.description_edit);
            EditText worker_edit = findViewById(R.id.worker_edit);
            TextView date_edit = findViewById(R.id.date_edit);
            name_edit.setText(ttask_edit.tname);
            date_edit.setText(ttask_edit.tdate);
            desc_edit.setText(ttask_edit.tdesc);
            worker_edit.setText(ttask_edit.worker);
            switch (ttask_edit.tstatus) {
                case 1:
                    RadioButton status_not_started = findViewById(R.id.status_not_started);
                    status_not_started.setChecked(true);
                    break;
                case 2:
                    RadioButton status_in_progress = findViewById(R.id.status_in_progress);
                    status_in_progress.setChecked(true);
                    break;
                case 3:
                    RadioButton status_complete = findViewById(R.id.status_complete);
                    status_complete.setChecked(true);
                    break;
            }
            switch (ttask_edit.tpriority) {
                case 1:
                    RadioButton priority_low = findViewById(R.id.priority_low);
                    priority_low.setChecked(true);
                    break;
                case 2:
                    RadioButton priority_medium = findViewById(R.id.priority_medium);
                    priority_medium.setChecked(true);
                    break;
                case 3:
                    RadioButton priority_high = findViewById(R.id.priority_high);
                    priority_high.setChecked(true);
                    break;
            }
        }
        currentDateTime = findViewById(R.id.date_edit);
        setInitialDateTime();
    }

    public void onBackClick(View view) {
        finish();
    }

    public void setDate(View v) {
        new DatePickerDialog(TeamTaskEdit.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public void setTime(View v) {
        new TimePickerDialog(TeamTaskEdit.this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }

    private void setInitialDateTime() {

        currentDateTime.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
        ttask.tdate_ms = dateAndTime.getTimeInMillis();
    }

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    public void onSaveClick(View view) {
        TextInputEditText name_edit = findViewById(R.id.name_edit);
        EditText desc_edit = findViewById(R.id.description_edit);
        EditText worker_edit = findViewById(R.id.worker_edit);
        TextView date_edit = findViewById(R.id.date_edit);
        intent = getIntent();
        if (intent.getStringExtra("requestcode").equals("update")) {
            TeamTask teamtask = new TeamTask((TeamTask) intent.getParcelableExtra("teamtask"));
            ttask.TUID = teamtask.TUID;
            ttask.id = teamtask.id;
        }
        ttask.tdesc = desc_edit.getText().toString();
        ttask.tdate = date_edit.getText().toString();
        ttask.tname = name_edit.getText().toString();
        ttask.worker = worker_edit.getText().toString();

        RadioButton status_not_started = findViewById(R.id.status_not_started);
        RadioButton status_in_progress = findViewById(R.id.status_in_progress);
        RadioButton status_complete = findViewById(R.id.status_complete);
        RadioButton priority_low = findViewById(R.id.priority_low);
        RadioButton priority_medium = findViewById(R.id.priority_medium);
        RadioButton priority_high = findViewById(R.id.priority_high);

        if (status_not_started.isChecked())
            ttask.tstatus = 1;
        else if (status_in_progress.isChecked())
            ttask.tstatus = 2;
        else if (status_complete.isChecked())
            ttask.tstatus = 3;
        else
            ttask.tstatus = 0;
        if (priority_low.isChecked())
            ttask.tpriority = 1;
        else if (priority_medium.isChecked())
            ttask.tpriority = 2;
        else if (priority_high.isChecked())
            ttask.tpriority = 3;
        else
            ttask.tpriority = 0;
        ttask.parentUID = Integer.parseInt(intent.getStringExtra("parentUID"));
        ttask.project_id = Integer.parseInt(intent.getStringExtra("project_id"));

        ttask.watcher_id = 0;       //TODO
        ttask.executor_id = 0;      //TODO
        if (TextUtils.isEmpty(name_edit.getText())) {
            setResult(RESULT_CANCELED, intent);
        } else {
            if (intent.getStringExtra("requestcode").equals("update")) {
                updateTeamTask(ttask);
            } else {
                mSharedPreferences = getSharedPreferences("com.example.zenitka.taskmanager.token", MODE_PRIVATE);
                ttask.token = mSharedPreferences.getString(SAVED_TOKEN, "No token");
                createTeamTask(ttask);
            }

//            intent.putExtra(EXTRA_REPLY, ttask);
//            setResult(RESULT_OK, intent);
//            finish();
        }
    }
}
