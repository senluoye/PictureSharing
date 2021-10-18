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

    public PostAdapter(Context context, List<getAllPostModel.DataDTO> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        mBinding = LayoutRecyclerViewItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(mBinding.getRoot());

//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_view_item,
//                parent, false);
//        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String path = context.getString(R.string.url) + data.get(position).getPictures().get(0) + ".jpg";
        System.out.println("图片地址:" + path);
        System.out.println("positon: " + position);

//        ImageView imageView = holder.itemView.findViewById(R.id.image);
//        imageView.getLayoutParams().height = 100 + (position % 3) * 30;

        Glide.with(context)
                .load(path)
                .placeholder(R.color.black)
                .override(200,100 + (position % 3) *30)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = mBinding.image;
        }
    }
}
