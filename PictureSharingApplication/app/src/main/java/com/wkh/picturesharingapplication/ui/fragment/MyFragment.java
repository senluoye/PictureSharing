package com.wkh.picturesharingapplication.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.wkh.picturesharingapplication.R;
import com.wkh.picturesharingapplication.databinding.FragmentHomeBinding;
import com.wkh.picturesharingapplication.databinding.FragmentMyBinding;
import com.wkh.picturesharingapplication.utils.PreferenceUtils;

import org.w3c.dom.Text;

public class MyFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    FragmentMyBinding mBinding;
    String name;
    String password;

    public MyFragment() {
    }


    public static MyFragment newInstance(String param1, String param2) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        PreferenceUtils preferenceUtils = PreferenceUtils.getInstance();
        System.out.println("拿到的token：" + preferenceUtils.getToken());
    }
}