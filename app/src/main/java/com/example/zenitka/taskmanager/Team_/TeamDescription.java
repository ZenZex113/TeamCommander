package com.example.zenitka.taskmanager.Team_;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.zenitka.taskmanager.R;
import com.example.zenitka.taskmanager.RegLog.LoginActivity;
import com.example.zenitka.taskmanager.TaskList;
import com.example.zenitka.taskmanager.net.CodeID;
import com.example.zenitka.taskmanager.net.CodeToken;
import com.example.zenitka.taskmanager.net.HelloApi;
import com.example.zenitka.taskmanager.net.InputLoginPassword;
import com.example.zenitka.taskmanager.net.NameDescription;
import com.example.zenitka.taskmanager.net.Network;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import static com.example.zenitka.taskmanager.helpers.Errors.CODE_OK;
import static com.example.zenitka.taskmanager.helpers.Errors.ERRORS;

public class TeamDescription extends AppCompatActivity {

    HelloApi api = Network.getInstance().getApi();

    //.observeOn(AndroidSchedulers.mainThread())

    @SuppressLint("CheckResult")
    public void createTeam(NameDescription nameDescription) {
        api.createTeam(nameDescription)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CodeID>() {
                    @Override
                    public void accept(CodeID codeID) throws Exception {
                        System.out.println("Accepting...");
                        if (codeID.getCode() == CODE_OK) {
                            Toast.makeText(TeamDescription.this, codeID.getID().toString(), Toast.LENGTH_SHORT).show();
                            finish();

//                            Intent intent = new Intent(LoginActivity.this, TaskList.class);
//                            startActivity(intent);
                        } else {
                            Toast.makeText(TeamDescription.this, ERRORS[codeID.getCode()], Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    public static final String EXTRA_REPLY = "com.example.zenitka.taskmanager.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_description);
    }

    public void onBackClick(View view) {
        finish();
    }

    public void onSaveClick(View view) {
        TextInputEditText name_edit = findViewById(R.id.name_edit);
        Intent intent = getIntent();
        Team team = new Team();
        team.name = name_edit.getText().toString();
        if(TextUtils.isEmpty(name_edit.getText())) {
            setResult(RESULT_CANCELED, intent);
        } else {
            intent.putExtra("team_back", team);
            setResult(RESULT_OK, intent);
        }

        createTeam(new NameDescription(team.name, "TempDescription"));

//        if(TextUtils.isEmpty(name_edit.getText())) {
//            setResult(RESULT_CANCELED, intent);
//        } else {
//            intent.putExtra("team_back", team);
//            setResult(RESULT_OK, intent);
//            finish();
//        }
    }
}
