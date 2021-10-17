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

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>
{
    LayoutRecyclerViewItemBinding mBinding;

    Context context;
    List<getAllPostModel.DataDTO> data;
    String url = "http://10.33.25.42:18080";

    public PostAdapter(Context context, List<getAllPostModel.DataDTO> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        mBinding = LayoutRecyclerViewItemBinding.inflate(
                LayoutInflater.from(context), parent, false);

        return new ViewHolder(mBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String path = url + data.get(position).getPictures().get(0) + ".jpg";
        System.out.println("图片地址:" + path);

        Glide.with(context)
                .load(path)
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
