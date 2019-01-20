package com.example.zenitka.taskmanager.Team_;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.example.zenitka.taskmanager.RegLog.LoginActivity;
import com.example.zenitka.taskmanager.TaskList;
import com.example.zenitka.taskmanager.R;

import java.util.ArrayList;
import java.util.List;

public class TeamList extends AppCompatActivity implements TeamAdapter.ItemClickListener, NavigationView.OnNavigationItemSelectedListener {

    List<Team> teams = new ArrayList<>();
    TeamAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);

        setInitialData();
        RecyclerView recyclerView = findViewById(R.id.teamrv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TeamAdapter(teams);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private void setInitialData(){

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
            finish();
        } else if (id == R.id.nav_list_of_teams) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_login) {
            Intent lintent = new Intent(TeamList.this, LoginActivity.class);
            startActivity(lintent);
        }

//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onItemClick(int action, int position) {
        Intent intent = new Intent(TeamList.this, TeamDescription.class);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void onAddFABClick(View view) {
        Intent intent = new Intent(TeamList.this, TeamDescription.class);
        startActivity(intent);
    }
}
