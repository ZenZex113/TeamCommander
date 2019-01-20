package com.example.zenitka.taskmanager;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.zenitka.taskmanager.RegLog.LoginActivity;
import com.example.zenitka.taskmanager.team.TeamList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TaskList extends AppCompatActivity implements TaskAdapter.ItemClickListener, NavigationView.OnNavigationItemSelectedListener {
    List<Task> tasks = new ArrayList<>();
    TaskAdapter adapter;

    Intent intent;

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_WORD_ACTIVITY_REQUEST_CODE = 2;

    private TaskViewModel mTaskViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setInitialData();
        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(tasks);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskList.this, TaskDescription.class);
                intent.putExtra("requestcode", "insert");
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });

        mTaskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        mTaskViewModel.getAllTasksSortedByDate().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable final List<Task> tasks) {
                adapter.setTasks(tasks);
            }
        });

        adapter.mTaskViewModel = mTaskViewModel;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_list_of_tasks) {
            Intent lintent = new Intent(TaskList.this, TaskList.class);
            startActivity(lintent);
        } else if (id == R.id.nav_list_of_teams) {
            Intent lintent = new Intent(TaskList.this, TeamList.class);
            startActivity(lintent);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_login) {
            Intent lintent = new Intent(TaskList.this, LoginActivity.class);
            startActivity(lintent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(int action, int position) {
        intent = new Intent(TaskList.this, TaskDescription.class);
        intent.putExtra("task", adapter.getTask(position));
        intent.putExtra("requestcode", "update");
        startActivityForResult(intent, UPDATE_WORD_ACTIVITY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent Data) {
        super.onActivityResult(requestCode, resultCode, Data);

        if (resultCode == RESULT_OK) {
            Task task = new Task((Task) Objects.requireNonNull(Data.getParcelableExtra(TaskDescription.EXTRA_REPLY)));
            if(requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE) {
                mTaskViewModel.insert(task);
            }
            else if(requestCode == UPDATE_WORD_ACTIVITY_REQUEST_CODE) {
                mTaskViewModel.insert(task);
            }
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Введите название",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void setInitialData(){

    }

    public void onSortByStatusClick(View view) {
        mTaskViewModel.getAllTasksSortedByStatus().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable final List<Task> tasks) {
                adapter.setTasks(tasks);
            }
        });
    }

    public void onSortByDateClick(View view) {
        mTaskViewModel.getAllTasksSortedByDate().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable final List<Task> tasks) {
                adapter.setTasks(tasks);
            }
        });
    }

    public void onSortByPriorityClick(View view) {
        mTaskViewModel.getAllTasksSortedByPriority().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable final List<Task> tasks) {
                adapter.setTasks(tasks);
            }
        });
    }
    public void onDeleteClick () {
    }
}

