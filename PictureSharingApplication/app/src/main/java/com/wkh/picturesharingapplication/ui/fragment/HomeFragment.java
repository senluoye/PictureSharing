package com.wkh.picturesharingapplication.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.wkh.picturesharingapplication.R;


public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private RecyclerView lvCardList;
//    private List<Card> CardData;
    private Context context = null;

    private View rootView;//home视图
    private View cardView;//card视图

    public HomeFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        cardView = inflater.inflate(R.layout.card_item, container, false);

        initView();

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //初始化布局
    private void initView() {
        lvCardList = rootView.findViewById(R.id.lv_card_list);

//        lvCardList.setOnItemClickListener(
//                new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView,
//                                            View view, int i, long l) {
//
//                        Intent intent = new Intent(context,
//                                DetailActivity.class);
//
//                        Card Card = adapter.getItem(i);
//                        Log.d(TAG, "Card为: " + Card);
//                        intent.putExtra(Constants.Card_DETAIL_URL_KEY,
//                                Card.getContentUrl());
//                        intent.putExtra(Constants.GET_Card_KEY,
//                                Card);
//                        startActivity(intent);
//                    }
//                });

//        //下拉功能
//        swipe = rootView.findViewById(R.id.swipe);
//        swipe.setOnRefreshListener(
//                new SwipeRefreshLayout.OnRefreshListener() {
//                    @Override
//                    public void onRefresh() {
//                        downRefreshData();
//                    }
//                });
    }

//    //下拉刷新数据
//    private void downRefreshData() {
//        mPage++;
//        Log.d(TAG,"下拉刷新");
//        adapter.clear();
//        adapter.notifyDataSetChanged();
//        refreshData(mPage,source);
//        swipe.setRefreshing(false);
//    }


    //初始化对象并保存数据
//    private void initData() {
//        CardData = new ArrayList<>();
////        StaggeredGridLayoutManager layoutManager =
////                new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
//        GridLayoutManager layoutManager = new GridLayoutManager(this.context,2);
//        lvCardList.setLayoutManager(layoutManager);
//        adapter = new CardAdapter(context, CardData);
//        lvCardList.setAdapter(adapter);
//    }

}