package com.example.zenitka.taskmanager.Team_;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zenitka.taskmanager.R;

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
                            Intent intent = new Intent(TeamDescription.this, ProjectList.class);
                            startActivity(intent);
                            finish();
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
