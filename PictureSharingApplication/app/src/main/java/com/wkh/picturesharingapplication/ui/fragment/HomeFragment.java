package com.wkh.picturesharingapplication.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wkh.picturesharingapplication.R;
import com.wkh.picturesharingapplication.adepter.RVAdapter;
import com.wkh.picturesharingapplication.bean.model.post.getAllPostModel;
import com.wkh.picturesharingapplication.databinding.FragmentHomeBinding;
import com.wkh.picturesharingapplication.http.RetrofitRequest;
import com.wkh.picturesharingapplication.utils.RVItemDecoration;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment{
    private static final String TAG = "HomeFragment";
    private RecyclerView lvCardList;
    private Context context = null;

    private View rootView;//home视图

    String url = "http://192.168.244.1:18080";
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiYWRtaTIzNDJuYXMiLCJpZCI6ImYyOWYyMjY0LTg0YTEtNDBmOS1hNGRlLTg1NTIxZGQwZTQ5MyIsImlhdCI6MTYzNDM3ODM4M30.nEpR7elezBvgEHjOY_dXs0mzj1WN2HX5WXw-GEdCkNg";
    RVAdapter mAdapter;
    Retrofit mRetrofit;
    GridLayoutManager mGridLayoutManager;
    RecyclerView mRecyclerView;
    static final int SUCCESS = 0;
    static final int FAILURE = 1;
    static final int EXCEPTION = 2;
    static final int SHOW_TOAST = 3;
    FragmentHomeBinding mBinding;
    List<getAllPostModel.DataDTO> dataOnUI = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        mBinding = FragmentHomeBinding.inflate(inflater, container, false);

        initView();
        return mBinding.getRoot();
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    mAdapter.notifyDataSetChanged();
                    break;
                case FAILURE:
                    Toast.makeText(getActivity(), "网络连接失败，请重试", Toast.LENGTH_SHORT).show();
                    mAdapter.notifyDataSetChanged();
                    break;
                case EXCEPTION:
                    Toast.makeText(getActivity(), "网络连接出错，请检查", Toast.LENGTH_SHORT).show();
                    mAdapter.notifyDataSetChanged();
                    break;
                case SHOW_TOAST:
                    String text = msg.arg1 == 0 ? "登陆成功" : "登陆失败";
                    Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    //初始化布局
    private void initView() {

        mBinding.rv.setLayoutManager(
                new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        mBinding.rv.setLayoutManager(
                new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        mBinding.rv.addItemDecoration(new RVItemDecoration());
        mBinding.btnGet.setOnClickListener(v -> {
            dataOnUI.clear();
            GETConnectSynchronous();
        });

        // 创建适配器
        mAdapter = new RVAdapter(getActivity(), dataOnUI);
        mBinding.rv.setAdapter(mAdapter);

        // 初始化Retrofit
        mRetrofit = new Retrofit.Builder()
                .baseUrl(url) // 设置网络请求的域名
                .addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器（Gson）
                .build();

    }

    void GETConnectSynchronous() {
        System.out.println("开始请求");
        new Thread(() -> {
            try {
                RetrofitRequest request = mRetrofit.create(RetrofitRequest.class);
                Response<getAllPostModel> response = request.getAllPost(token).execute();

                if (response.code() == HttpURLConnection.HTTP_OK) {
                    System.out.println("拿到的数据：" + response.body().getData());
                    dataOnUI.addAll(response.body().getData());
                    handler.sendEmptyMessage(SUCCESS);
                } else {
                    handler.sendEmptyMessage(FAILURE);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                handler.sendEmptyMessage(EXCEPTION);
            }
        }).start();
    }

}