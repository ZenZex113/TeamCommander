package com.example.zenitka.taskmanager.RegLog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zenitka.taskmanager.net.CodeID;
import com.example.zenitka.taskmanager.net.CodeToken;
import com.example.zenitka.taskmanager.net.HelloApi;
import com.example.zenitka.taskmanager.net.InputLoginPassword;
import com.example.zenitka.taskmanager.net.NameSurnameLoginPassword;
import com.example.zenitka.taskmanager.net.Network;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import static com.example.zenitka.taskmanager.helpers.Errors.CODE_OK;

import com.example.zenitka.taskmanager.R;

public class RegisterActivity extends AppCompatActivity {

    HelloApi api = Network.getInstance().getApi();

    @SuppressLint("CheckResult")
    public void registrate(NameSurnameLoginPassword nameSurnameLoginPassword) {
        api.registerUser(nameSurnameLoginPassword)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CodeID>() {
                    @Override
                    public void accept(CodeID codeID) throws Exception {
                        System.out.println("Accepting...");
                        if (codeID.getCode() == CODE_OK) {
                            Toast.makeText(RegisterActivity.this, "ID = " + codeID.getID(), Toast.LENGTH_SHORT).show();
//                            status.setText(codeToken.getToken());
                        } else {
                            Toast.makeText(RegisterActivity.this, codeID.getCode().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(RegisterActivity.this, "Out of internet!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    android.support.design.widget.TextInputEditText et_name, et_sername, et_login, et_password, et_repeatpassword;
    Button btn_reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_name = findViewById(R.id.name);
        et_sername = findViewById(R.id.sername);
        et_login = findViewById(R.id.login);
        et_password = findViewById(R.id.password);
        et_repeatpassword = findViewById(R.id.repeatpassword);

        btn_reg = findViewById(R.id.register);
    }

    public void onRegisterBackClick(View view) {
        finish();
    }

    public void onRegisterClick(View view) {
        registrate(new NameSurnameLoginPassword(et_name.getText().toString(),
                et_sername.getText().toString(),
                et_login.getText().toString(),
                et_password.getText().toString()));
    }
}
