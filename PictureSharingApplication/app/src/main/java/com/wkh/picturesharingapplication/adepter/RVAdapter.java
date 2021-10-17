package com.wkh.picturesharingapplication.adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wkh.picturesharingapplication.R;
import com.wkh.picturesharingapplication.bean.model.post.getAllPostModel;
import com.wkh.picturesharingapplication.databinding.LayoutRecyclerViewItemBinding;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder>
{
    LayoutRecyclerViewItemBinding mBinding;

    Context context;
    List<getAllPostModel.DataDTO> data;
    String url = "http://192.168.244.1:18080";

    public RVAdapter(Context context, List<getAllPostModel.DataDTO> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RVAdapter.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        mBinding = LayoutRecyclerViewItemBinding.inflate(
                LayoutInflater.from(context), parent, false);

        return new ViewHolder(mBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String path = url + data.get(position).getPictures().get(0);
        System.out.println("图片地址:" + path);

        Glide.with(context)
                .load("http://192.168.244.1:18080/df7124a7-8e1b-4162-bb2e-5444b6b5b99f.jpg")
//                .centerCrop()
                .placeholder(R.color.black)
                .override(200,200)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = mBinding.image;
        }
    }
}
