package com.wkh.picturesharingapplication.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.wkh.picturesharingapplication.R;
import com.wkh.picturesharingapplication.databinding.FragmentHomeBinding;
import com.wkh.picturesharingapplication.databinding.FragmentMyBinding;
import com.wkh.picturesharingapplication.ui.LoginActivity;
import com.wkh.picturesharingapplication.ui.MainActivity;
import com.wkh.picturesharingapplication.utils.PreferenceUtils;

import org.w3c.dom.Text;

public class MyFragment extends Fragment {

    FragmentMyBinding mBinding;
    String name;
    String password;
    PreferenceUtils preferenceUtils;
    private static final int EXIT = 1;

    final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case EXIT:
                    Toast.makeText(getActivity(), "退出成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    break;
            }
        }
    };

    public MyFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = FragmentMyBinding.inflate(inflater, container, false);

        initView();
        return mBinding.getRoot();
    }

    private void initView(){
        Bundle bundle = this.getArguments();
        name = bundle.getString("name");
        password = bundle.getString("password");
        mBinding.accountName.setText(name);

        preferenceUtils = PreferenceUtils.getInstance();
        System.out.println("拿到的token：" + preferenceUtils.getToken());

        mBinding.exit.setOnClickListener(v -> {
            preferenceUtils.reset();
            handler.sendEmptyMessage(EXIT);
        });
    }
}