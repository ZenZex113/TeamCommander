package com.example.zenitka.taskmanager.RegLog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zenitka.taskmanager.net.CodeToken;
import com.example.zenitka.taskmanager.net.HelloApi;
import com.example.zenitka.taskmanager.net.InputLoginPassword;
import com.example.zenitka.taskmanager.net.Network;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import static com.example.zenitka.taskmanager.helpers.Errors.CODE_OK;
import static com.example.zenitka.taskmanager.helpers.Errors.ERRORS;

import com.example.zenitka.taskmanager.TaskList;
import com.example.zenitka.taskmanager.R;

public class LoginActivity extends AppCompatActivity {

    HelloApi api = Network.getInstance().getApi();

    //.observeOn(AndroidSchedulers.mainThread())

    @SuppressLint("CheckResult")
    public void login(InputLoginPassword inputLoginPassword) {
        api.enterUser(inputLoginPassword)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CodeToken>() {
                    @Override
                    public void accept(CodeToken codeToken) throws Exception {
                        System.out.println("Accepting...");
                        if (codeToken.getCode() == CODE_OK) {
                            Toast.makeText(LoginActivity.this, codeToken.getToken(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, ERRORS[codeToken.getCode()], Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

//    public void getUser(long id) {
//        api.getUser(id)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<User>() {
//                    @Override
//                    public void accept(User user) throws Exception {
//                        if (user == null || user.getName() == null) {
//                            tv_main.setText("No such user, or other error...");
//                        } else {
//                            tv_main.setText(user.toString());
//                        }
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        Log.e("error", throwable.getMessage(), throwable);
//                        tv_main.setText("Something went wrong.");
//                    }
//                });
//    }
//
//    public void saveUser(User user) {
//        api.saveUser(user)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<User>() {
//                    @Override
//                    public void accept(User user) throws Exception {
//                        tv_main.setText("OK, Added!");
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        Log.e("error", throwable.getMessage(), throwable);
//                    }
//                });
//    }


//    public void getHello() {
//        api.getHelloMessage()
//                .observeOn(AndroidSchedulers.mainThread())
//                .delaySubscription(3, TimeUnit.SECONDS)
//                .subscribe(new Consumer<HelloMessage>() {
//                    @Override
//                    public void accept(HelloMessage helloMessage) throws Exception {
//                        ((TextView) findViewById(R.id.text_view)).setText(helloMessage.message);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        Log.e("error", throwable.getMessage(), throwable);
//                    }
//                });
//    }

    android.support.design.widget.TextInputLayout et_login, et_password;
    Button btn_log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_login = findViewById(R.id.nick_l);
        et_password = findViewById(R.id.password_l);

        btn_log = findViewById(R.id.sign_up);

        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = et_login.getEditText().getText().toString();
                String password = et_password.getEditText().getText().toString();
                if (login.equals("") || password.equals("") || password.length() < 8) {
                    //Too short!
                } else {
                    login(new InputLoginPassword(login, password));
                }
            }
        });
    }

    public void onLoginBackClick(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void onBackClick(View view) {
        Intent intent = new Intent(LoginActivity.this, TaskList.class);
        startActivity(intent);
    }


}
