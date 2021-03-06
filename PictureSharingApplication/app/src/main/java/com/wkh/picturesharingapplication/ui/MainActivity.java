package com.wkh.picturesharingapplication.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SimpleTarget;
import com.wkh.picturesharingapplication.R;
import com.wkh.picturesharingapplication.ui.fragment.AddFragment;
import com.wkh.picturesharingapplication.ui.fragment.HomeFragment;
import com.wkh.picturesharingapplication.ui.fragment.MyFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout main_body;
    private LinearLayout main_bottom_bar;

    private RelativeLayout bottom_bar_1_btn;
    private TextView bottom_bar_text_1;
    private ImageView bottom_bar_image_1;
    private RelativeLayout bottom_bar_2_btn;
    private TextView bottom_bar_text_2;
    private ImageView bottom_bar_image_2;
    private RelativeLayout bottom_bar_3_btn;
    private TextView bottom_bar_text_3;
    private ImageView bottom_bar_image_3;

    private HomeFragment mHomeFragment;//??????
    private AddFragment mAddFragment;//??????
    private MyFragment mMyFragment;//??????

    private int RESULT_LOAD_IMAGE = 1;
    private int RESULT_CAMERA_IMAGE = 2;
    Intent intent;
    private String name;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //???????????????
        initView();
        //??????????????????
        setMain();

        checkNeedPermissions();
    }

    private void initView(){
        //???????????????
        main_body=findViewById(R.id.main_body);
        main_bottom_bar=findViewById(R.id.main_bottom_bar);
        //??????
        bottom_bar_1_btn=findViewById(R.id.bottom_bar_1_btn);
        bottom_bar_text_1=findViewById(R.id.bottom_bar_text_1);
        bottom_bar_image_1=findViewById(R.id.bottom_bar_image_1);
        //??????????????????
        bottom_bar_1_btn.setOnClickListener(this);
        //??????
        bottom_bar_2_btn=findViewById(R.id.bottom_bar_2_btn);
        bottom_bar_text_2=findViewById(R.id.bottom_bar_text_2);
        bottom_bar_image_2=findViewById(R.id.bottom_bar_image_2);
        //??????????????????
        bottom_bar_2_btn.setOnClickListener(this);
        //??????
        bottom_bar_3_btn=findViewById(R.id.bottom_bar_3_btn);
        bottom_bar_text_3=findViewById(R.id.bottom_bar_text_3);
        bottom_bar_image_3=findViewById(R.id.bottom_bar_image_3);
        //??????????????????
        bottom_bar_3_btn.setOnClickListener(this);

        //Fragment??????
        mHomeFragment = new HomeFragment();
        mAddFragment = new AddFragment();
        mMyFragment = new MyFragment();

        intent = getIntent();
        name = intent.getStringExtra("name");
        password = intent.getStringExtra("password");

        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("password", password);
        mMyFragment.setArguments(bundle);
    }

    //????????????
    private void initButton(RelativeLayout bt){
        Animation animation=new AlphaAnimation(1.0f,0.0f);
        animation.setDuration(300);
        bt.startAnimation(animation);
    }

    @Override
    public void onClick(View v) {
        //??????Fragment
        switch (v.getId()){
            case R.id.bottom_bar_1_btn:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_body,mHomeFragment).commit();
                break;
            case R.id.bottom_bar_2_btn:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_body, mAddFragment).commit();
                break;
            case R.id.bottom_bar_3_btn:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_body, mMyFragment).commit();
                break;
        }
    }

    private void setMain() {
        //??????????????????
        this.getSupportFragmentManager().beginTransaction().add(R.id.main_body, mHomeFragment).commit();
    }


    private void checkNeedPermissions(){
        //6.0??????????????????????????????
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //????????????????????????
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, 1);
        }
    }
}