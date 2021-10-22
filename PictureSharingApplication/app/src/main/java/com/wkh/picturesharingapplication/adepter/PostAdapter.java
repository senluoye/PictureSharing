package com.wkh.picturesharingapplication.adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wkh.picturesharingapplication.R;
import com.wkh.picturesharingapplication.bean.model.post.getAllPostModel;
import com.wkh.picturesharingapplication.databinding.LayoutRecyclerViewItemBinding;
import com.wkh.picturesharingapplication.utils.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    LayoutRecyclerViewItemBinding mBinding;

    Context context;
    List<getAllPostModel.DataDTO> data;
    List<Integer> sourceWidths;
    List<Integer> sourceHeights;

    public PostAdapter(Context context, List<getAllPostModel.DataDTO> data, List<Integer> sourceWidths, List<Integer> sourceHeights) {
        this.context = context;
        this.data = data;
        this.sourceHeights = sourceHeights;
        this.sourceWidths = sourceWidths;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        mBinding = LayoutRecyclerViewItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(mBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String path = context.getString(R.string.url) + data.get(position).getPictures().get(0) + ".jpg";
        System.out.println("图片地址:" + path);

        int screenWidth = DisplayUtils.getScreenWidth(context);
        int imageWidth = screenWidth / 2;
        int imageHeight = (int) (imageWidth / (1.0f * sourceWidths.get(position) / sourceHeights.get(position)));

        ConstraintLayout.LayoutParams layoutParams =
                (ConstraintLayout.LayoutParams) holder.imageView.getLayoutParams();
        layoutParams.height = imageHeight;
        holder.imageView.setLayoutParams(layoutParams);

        Glide.with(context)
                .load(path)
                .placeholder(R.color.black)
                .override(imageWidth, imageWidth)
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
