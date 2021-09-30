package com.wkh.picturesharingapplication.ui;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wkh.picturesharingapplication.R;
import com.wkh.picturesharingapplication.bean.entity.User;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Retrofit retrofit;
    private ApiService apiService;
    private Boolean bPwdSwitch = false;
    private EditText etPwd;
    private EditText etUsername;
    private CheckBox cbRememberPwd;
    private TextView Sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        retrofit = new Retrofit.Builder()
                .baseUrl("/api/user/login/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);


        final ImageView ivPwdSwitch = findViewById(R.id.iv_pwd_switch);
        etPwd = findViewById(R.id.et_pwd);
        etUsername = findViewById(R.id.et_username);
        cbRememberPwd = findViewById(R.id.cb_remember_pwd);
        Button btLogin = findViewById(R.id.bt_login);
        Sign=findViewById(R.id.tv_sign_up);

        Sign.setOnClickListener(this);
        Sign.setOnClickListener(view -> {
            Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        btLogin.setOnClickListener(this);
        btLogin.setOnClickListener(view -> {

            String spFileName = getResources()
                    .getString(R.string.shared_preferences_file_name);
            String accountKey = getResources()
                    .getString(R.string.login_account_name);
            String passwordKey =  getResources()
                    .getString(R.string.login_password);
            String rememberPasswordKey = getResources()
                    .getString(R.string.login_remember_passoword);


            SharedPreferences spFile = getSharedPreferences(
                    spFileName,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = spFile.edit();

            if(cbRememberPwd.isChecked()) {
                String password = etPwd.getText().toString();
                String account = etUsername.getText().toString();

                editor.putString(accountKey, account);
                editor.putString(passwordKey, password);
                editor.putBoolean(rememberPasswordKey, true);
                editor.apply();
            } else {
                editor.remove(accountKey);
                editor.remove(passwordKey);
                editor.remove(rememberPasswordKey);
                editor.apply();
            }

            final User user= new User();
            user.setName(etUsername.getText().toString());
            user.setPassword(etPwd.getText().toString());

            //判断账号密码是否正确

        });

        ivPwdSwitch.setOnClickListener(view -> {
            bPwdSwitch = !bPwdSwitch;
            if (bPwdSwitch) {
                ivPwdSwitch.setImageResource(
                        R.drawable.ic_baseline_visibility_24);
                etPwd.setInputType(
                        InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                ivPwdSwitch.setImageResource(
                        R.drawable.ic_baseline_visibility_off_24);
                etPwd.setInputType(
                        InputType.TYPE_TEXT_VARIATION_PASSWORD |
                                InputType.TYPE_CLASS_TEXT);
                etPwd.setTypeface(Typeface.DEFAULT);
            }
        });

        String spFileName = getResources()
                .getString(R.string.shared_preferences_file_name);
        String accountKey = getResources()
                .getString(R.string.login_account_name);
        String passwordKey =  getResources()
                .getString(R.string.login_password);
        String rememberPasswordKey = getResources()
                .getString(R.string.login_remember_passoword);

        SharedPreferences spFile = getSharedPreferences(
                spFileName,
                MODE_PRIVATE);
        String account = spFile.getString(accountKey, null);
        String password = spFile.getString(passwordKey, null);
        Boolean rememberPassword = spFile.getBoolean(
                rememberPasswordKey,
                false);

        if (account != null && !TextUtils.isEmpty(account)) {
            etUsername.setText(account);
        }

        if (password != null && !TextUtils.isEmpty(password)) {
            etPwd.setText(password);
        }

        cbRememberPwd.setChecked(rememberPassword);
    }

    //消息提示框
    private void toast(String s) {
        Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        String spFileName = getResources()
                .getString(R.string.shared_preferences_file_name);
        String accountKey = getResources()
                .getString(R.string.login_account_name);
        String passwordKey =  getResources()
                .getString(R.string.login_password);
        String rememberPasswordKey = getResources()
                .getString(R.string.login_remember_passoword);

        SharedPreferences spFile = getSharedPreferences(
                spFileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spFile.edit();

         if(cbRememberPwd.isChecked()) {
            String password = etPwd.getText().toString();
            String account = etUsername.getText().toString();

            editor.putString(accountKey, account);
            editor.putString(passwordKey, password);
            editor.putBoolean(rememberPasswordKey, true);
            editor.apply();
        } else {
            editor.remove(accountKey);
            editor.remove(passwordKey);
            editor.remove(rememberPasswordKey);
            editor.apply();
        }

    }
}
