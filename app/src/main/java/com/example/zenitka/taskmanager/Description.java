package com.example.zenitka.taskmanager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class Description extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.zenitka.taskmanager.REPLY";

    Task task = new Task();

    TextView currentDateTime;
    Calendar dateAndTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = getIntent();
        Task task_edit = new Task((Task) intent.getParcelableExtra("task"));
        TextInputEditText name_edit = findViewById(R.id.name_edit);
        EditText desc_edit = findViewById(R.id.description_edit);
        TextView date_edit = findViewById(R.id.date_edit);
        name_edit.setText(task_edit.name);
        date_edit.setText(task_edit.date);
        desc_edit.setText(task_edit.desc);
        switch (task_edit.status) {
            case 1:
                RadioButton status_not_started = (RadioButton) findViewById(R.id.status_not_started);
                status_not_started.setChecked(true);
                break;
            case 2:
                RadioButton status_in_progress = (RadioButton) findViewById(R.id.status_in_progress);
                status_in_progress.setChecked(true);
                break;
            case 3:
                RadioButton status_complete = (RadioButton) findViewById(R.id.status_complete);
                status_complete.setChecked(true);
                break;
        }
        switch (task_edit.priority) {
            case 1:
                RadioButton priority_low = (RadioButton) findViewById(R.id.priority_low);
                priority_low.setChecked(true);
                break;
            case 2:
                RadioButton priority_medium = (RadioButton) findViewById(R.id.priority_medium);
                priority_medium.setChecked(true);
                break;
            case 3:
                RadioButton priority_high = (RadioButton) findViewById(R.id.priority_high);
                priority_high.setChecked(true);
                break;
        }

        currentDateTime = findViewById(R.id.date_edit);
        setInitialDateTime();
    }

    public void setDate(View v) {
        new DatePickerDialog(Description.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public void setTime(View v) {
        new TimePickerDialog(Description.this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }

    private void setInitialDateTime() {

        currentDateTime.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
        task.date_ms = dateAndTime.getTimeInMillis();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_description, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.save) {
            TextInputEditText name_edit = findViewById(R.id.name_edit);
            EditText desc_edit = findViewById(R.id.description_edit);
            TextView date_edit = findViewById(R.id.date_edit);

            Intent intent = getIntent();
            if(intent.getStringExtra("requestcode").equals("update")) {
                Task task_old = new Task((Task) intent.getParcelableExtra("task"));
                task.UID = task_old.UID;
            }
            task.desc = desc_edit.getText().toString();
            task.date = date_edit.getText().toString();
            task.name = name_edit.getText().toString();

            RadioButton status_not_started = findViewById(R.id.status_not_started);
            RadioButton status_in_progress = findViewById(R.id.status_in_progress);
            RadioButton status_complete = findViewById(R.id.status_complete);
            RadioButton priority_low = findViewById(R.id.priority_low);
            RadioButton priority_medium = findViewById(R.id.priority_medium);
            RadioButton priority_high = findViewById(R.id.priority_high);

            if(status_not_started.isChecked())
                task.status = 1;
            else if(status_in_progress.isChecked())
                task.status = 2;
            else if(status_complete.isChecked())
                task.status = 3;
            else
                task.status = 0;
            if(priority_low.isChecked())
                task.priority = 1;
            else if(priority_medium.isChecked())
                task.priority = 2;
            else if(priority_high.isChecked())
                task.priority = 3;
            else
                task.priority = 0;

            if(TextUtils.isEmpty(name_edit.getText())) {
                setResult(RESULT_CANCELED, intent);
            } else {
                intent.putExtra(EXTRA_REPLY, task);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        return true;
    }

    public void onBackClick(View view) {
        finish();
    }
}
