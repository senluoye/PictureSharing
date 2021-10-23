package com.wkh.picturesharingapplication.ui;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wkh.picturesharingapplication.R;
import com.wkh.picturesharingapplication.bean.entity.User;
import com.wkh.picturesharingapplication.bean.model.user.LoginModel;
import com.wkh.picturesharingapplication.http.RetrofitRequest;
import com.wkh.picturesharingapplication.utils.PreferenceUtils;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private Boolean bPwdSwitch = false;
    private EditText etPwd;
    private EditText etUsername;
    private CheckBox cbRememberPwd;
    private TextView Sign;

    static final int SUCCESS = 0;
    static final int FAILURE = 1;
    static final int ACTION_REGISTER = 2;
    private static final String TAG = "LoginActivity";
    private String n;
    private String p;

    final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    startActivity(new Intent(LoginActivity.this, MainActivity.class)
                                    .putExtra("name", n)
                                    .putExtra("password", p));
                    finish();
                    break;
                case FAILURE:
                    Toast.makeText(LoginActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        retrofit = new Retrofit.Builder()
                .baseUrl(this.getString(R.string.url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ImageView ivPwdSwitch = findViewById(R.id.iv_pwd_switch);
        etPwd = findViewById(R.id.et_pwd);
        etUsername = findViewById(R.id.et_username);
        cbRememberPwd = findViewById(R.id.cb_remember_pwd);
        Button btLogin = findViewById(R.id.bt_login);
        Sign=findViewById(R.id.tv_sign_up);

        Sign.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivityForResult(intent, ACTION_REGISTER);
        });

        btLogin.setOnClickListener(view -> {

            String spFileName = getResources()
                    .getString(R.string.shared_preferences_file_name);
            String accountKey = getResources()
                    .getString(R.string.login_account_name);
            String passwordKey =  getResources()
                    .getString(R.string.login_password);
            String rememberPasswordKey = getResources()
                    .getString(R.string.login_remember_passoword);

            SharedPreferences spFile = getSharedPreferences(spFileName, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = spFile.edit();

            final User user= new User();
            user.setName(etUsername.getText().toString());
            user.setPassword(etPwd.getText().toString());

            retrofit.create(RetrofitRequest.class)
                    .login(etUsername.getText().toString(), etPwd.getText().toString())
                    .enqueue(new Callback<LoginModel>() {

                        @Override
                        public void onResponse(@NotNull Call<LoginModel> call,
                                               @NotNull Response<LoginModel> response) {
                            String password = etPwd.getText().toString();
                            String account = etUsername.getText().toString();
                            n = account;
                            p = password;

                            if(cbRememberPwd.isChecked()) {
                                editor.putString(accountKey, account);
                                editor.putString(passwordKey, password);
                                editor.putBoolean(rememberPasswordKey, true);
                            } else {
                                editor.remove(accountKey);
                                editor.remove(passwordKey);
                                editor.remove(rememberPasswordKey);
                            }
                            editor.apply();

//                            String token = response.body().getData().getToken();
                            System.out.println(response.body().toString());
                            PreferenceUtils.init(getApplication());
                            PreferenceUtils preferenceUtils= PreferenceUtils.getInstance();
//                            preferenceUtils.saveToken(token);
                            System.out.println("储存的数据:" + preferenceUtils.getToken());
                            handler.sendEmptyMessage(SUCCESS);
                        }

                        @Override
                        public void onFailure(@NotNull Call<LoginModel> call, @NotNull Throwable t) {
                            Log.e(TAG, "onFailure: ", t);
                            Message message = Message.obtain();
                            message.what = FAILURE;
                            message.obj = t.getMessage();
                            handler.sendMessage(message);
                        }
            });
        });

        ivPwdSwitch.setOnClickListener(view -> {
            bPwdSwitch = !bPwdSwitch;
            if (bPwdSwitch) {
                ivPwdSwitch.setImageResource(R.drawable.ic_baseline_visibility_24);
                etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                ivPwdSwitch.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD
                        | InputType.TYPE_CLASS_TEXT);
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

        SharedPreferences spFile = getSharedPreferences(spFileName, MODE_PRIVATE);
        String account = spFile.getString(accountKey, null);
        String password = spFile.getString(passwordKey, null);
        boolean rememberPassword = spFile.getBoolean(
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            assert data != null;
            etUsername.setText(data.getStringExtra("username"));
            etPwd.setText(data.getStringExtra("password"));
        }
    }
}
