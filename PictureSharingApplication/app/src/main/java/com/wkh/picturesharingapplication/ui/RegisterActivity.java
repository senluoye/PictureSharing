package com.wkh.picturesharingapplication.ui;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.wkh.picturesharingapplication.R;
import com.wkh.picturesharingapplication.bean.entity.User;
import com.wkh.picturesharingapplication.bean.model.user.LoginModel;
import com.wkh.picturesharingapplication.http.RetrofitRequest;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private ApiService apiService;
    private EditText Username;
    private EditText Password;
    private EditText Confirm;
    private Button signup;
    private Button cancel;

    static final int SUCCESS = 0;
    static final int FAILURE = 1;
    private static final String TAG = "RegisterActivity";

    final Handler handler = new Handler(Looper.getMainLooper())
    {
        @Override
        public void handleMessage(
                @NonNull
                        Message msg)
        {
            switch (msg.what)
            {
                case SUCCESS:
                    Intent intent = new Intent();
                    intent.putExtra("username", Username.getText().toString());
                    intent.putExtra("password", Password.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                case FAILURE:
                    Toast.makeText(RegisterActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.33.109.181:18080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Username=findViewById(R.id.username);
        Password=findViewById(R.id.password);
        Confirm=findViewById(R.id.confirm);
        signup=findViewById(R.id.signup);
        cancel=findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (!Password.getText().toString().equals(Confirm.getText().toString()))
                {
                    Toast.makeText(RegisterActivity.this, "两次输入的密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                final User user= new User();
                user.setName(Username.getText().toString());
                user.setPassword(Password.getText().toString());
                retrofit.create(RetrofitRequest.class).register(user).enqueue(new Callback<LoginModel>()
                {
                    @Override
                    public void onResponse(
                            @NotNull
                                    Call<LoginModel> call,
                            @NotNull
                                    Response<LoginModel> response)
                    {
                        handler.sendEmptyMessage(SUCCESS);
                    }

                    @Override
                    public void onFailure(
                            @NotNull
                                    Call<LoginModel> call,
                            @NotNull
                                    Throwable t)
                    {
                        Log.e(TAG, "onFailure: ", t);
                        Message message = Message.obtain();
                        message.what = FAILURE;
                        message.obj = t.getMessage();
                        handler.sendMessage(message);
                    }
                });
            }
        });
    }
}