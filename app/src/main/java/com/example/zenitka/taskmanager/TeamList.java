package com.example.zenitka.taskmanager;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.zenitka.taskmanager.R;
import com.example.zenitka.taskmanager.net.CodeListTeam;
import com.example.zenitka.taskmanager.net.HelloApi;
import com.example.zenitka.taskmanager.net.Network;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import static com.example.zenitka.taskmanager.RegLog.LoginActivity.SAVED_TOKEN;
import static com.example.zenitka.taskmanager.helpers.Errors.CODE_OK;
import static com.example.zenitka.taskmanager.helpers.Errors.ERRORS;

public class TeamList extends AppCompatActivity implements TeamAdapter.ItemClickListener, NavigationView.OnNavigationItemSelectedListener {

    List<Team> teams = new ArrayList<>();
    TeamAdapter adapter;


    SharedPreferences mSharedPreferences;

    HelloApi api = Network.getInstance().getApi();

    @SuppressLint("CheckResult")
    public void loadTeamsList(String token) {
        api.loadTeamsList(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CodeListTeam>() {
                    @Override
                    public void accept(CodeListTeam teamsCode) throws Exception {
                        if (teamsCode.getCode() == CODE_OK) {
                            mTeamViewModel.deleteAll();
                            for (Team team : teamsCode.getResponse()) {
                                mTeamViewModel.insert(team);
                            }
                        } else {
                            Toast.makeText(TeamList.this, ERRORS[teamsCode.getCode()], Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.err.println(throwable.getMessage());
                        Toast.makeText(TeamList.this, "Out of internet!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private TeamViewModel mTeamViewModel;

    public static final int NEW_TEAM_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_TEAM_ACTIVITY_REQUEST_CODE = 2;

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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeamList.this, TeamEdit.class);
                intent.putExtra("requestcode", "insert");
                startActivityForResult(intent, NEW_TEAM_ACTIVITY_REQUEST_CODE);
            }
        });

        FloatingActionButton fab_update = findViewById(R.id.fab_update);
        fab_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSharedPreferences = getSharedPreferences("com.example.zenitka.taskmanager.token", MODE_PRIVATE);
                loadTeamsList((mSharedPreferences.getString(SAVED_TOKEN, "No token")));
            }
        });

        mTeamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);
        mTeamViewModel.getAllTeams().observe(this, new Observer<List<Team>>() {
            @Override
            public void onChanged(@Nullable final List<Team> teams) {
                adapter.setTeams(teams);
            }
        });

        adapter.mTeamViewModel = mTeamViewModel;
    }
    private void setInitialData(){

    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_list_of_tasks) {
            finish();
        } else if (id == R.id.nav_list_of_teams) {

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
        Intent intent = new Intent(TeamList.this, TeamEdit.class);
        intent.putExtra("team", adapter.getTeam(position));
        intent.putExtra("requestcode", "update");
        startActivityForResult(intent, UPDATE_TEAM_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent Data) {
        super.onActivityResult(requestCode, resultCode, Data);

        if (resultCode == RESULT_OK) {
            Team team = new Team((Team) Objects.requireNonNull(Data.getParcelableExtra(TeamEdit.EXTRA_REPLY)));
            mTeamViewModel.insert(team);
        }
    }
}
