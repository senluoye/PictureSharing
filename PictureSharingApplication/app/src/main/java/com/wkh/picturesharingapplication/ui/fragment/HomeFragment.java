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
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.wkh.picturesharingapplication.R;
import com.wkh.picturesharingapplication.adepter.PostAdapter;
import com.wkh.picturesharingapplication.bean.model.post.getAllPostModel;
import com.wkh.picturesharingapplication.databinding.FragmentHomeBinding;
import com.wkh.picturesharingapplication.http.RetrofitRequest;
import com.wkh.picturesharingapplication.utils.DecodeUtils;
import com.wkh.picturesharingapplication.utils.RVItemDecoration;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment{
    private static final String TAG = "HomeFragment";
    private RecyclerView lvCardList;
    private Context context = null;

    final List<Integer> sourceWidths = new ArrayList<>();
    final List<Integer> sourceHeights = new ArrayList<>();
    final List<Integer> collectionCounts = new ArrayList<>();

    PostAdapter mAdapter;
    Retrofit mRetrofit;
    static final int SUCCESS = 0;
    static final int FAILURE = 1;
    static final int EXCEPTION = 2;
    static final int SHOW_TOAST = 3;
    FragmentHomeBinding mBinding;
    List<getAllPostModel.DataDTO> dataOnUI = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;

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
                    Toast.makeText(getActivity(), "数据请求失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    //初始化布局
    private void initView() {

        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mBinding.rv.setLayoutManager(mLayoutManager);
        mBinding.rv.addItemDecoration(new RVItemDecoration());

        // 创建适配器
        mAdapter = new PostAdapter(getActivity(), dataOnUI, sourceWidths, sourceHeights);
        mBinding.rv.setAdapter(mAdapter);

        // 初始化Retrofit
        mRetrofit = new Retrofit.Builder()
                .baseUrl(getActivity().getString(R.string.url))
                .addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器（Gson）
                .build();

        //直接请求数据
        GETConnectSynchronous();
    }

    void GETConnectSynchronous() {
        System.out.println("开始请求");
        new Thread(() -> {
            try {
                RetrofitRequest request = mRetrofit.create(RetrofitRequest.class);
                Response<getAllPostModel> response =
                        request.getAllPost(getActivity().getString(R.string.token)).execute();

                if (response.code() == HttpURLConnection.HTTP_OK) {
                    System.out.println("拿到的数据：" + response.body().getData().toString());
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



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dataOnUI.clear();
        sourceHeights.clear();
        sourceWidths.clear();
    }
}