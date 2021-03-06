package com.wkh.picturesharingapplication.adepter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wkh.picturesharingapplication.R;
import com.wkh.picturesharingapplication.bean.entity.Star;
import com.wkh.picturesharingapplication.bean.model.post.getAllPostModel;
import com.wkh.picturesharingapplication.bean.model.star.StarModel;
import com.wkh.picturesharingapplication.databinding.LayoutRecyclerViewItemBinding;
import com.wkh.picturesharingapplication.http.RetrofitRequest;
import com.wkh.picturesharingapplication.utils.DisplayUtils;
import com.wkh.picturesharingapplication.utils.PreferenceUtils;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    LayoutRecyclerViewItemBinding mBinding;

    Context context;
    List<getAllPostModel.DataDTO> data;
    List<Integer> sourceWidths;
    List<Integer> sourceHeights;
    Retrofit retrofit;
    static final int SUCCESS = 1;

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Toast.makeText(context, "ηΉθ΅ζε", Toast.LENGTH_SHORT).show();
        }
    };

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

        retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return new ViewHolder(mBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String path = context.getString(R.string.url) + data.get(position).getPictures().get(0) + ".jpg";
        System.out.println("εΎηε°ε:" + path);

        Glide.with(context)
                .load(path)
                .placeholder(R.color.black)
                .override(600, 600)
                .into(holder.imageView);

        holder.textView.setText(data.get(position).getUsername());

        holder.star.setOnClickListener(v -> new Thread(() -> {
            try {
                PreferenceUtils preferenceUtils = PreferenceUtils.getInstance();

                Star star = new Star();
                star.setUserId(preferenceUtils.getUserId());
                star.setPostId(data.get(position).getId());

                RetrofitRequest request = retrofit.create(RetrofitRequest.class);
                Response<StarModel> response =
                        request.star(holder.imageView.getContext().getString(R.string.token), star).execute();

                if (response.code() == HttpURLConnection.HTTP_OK) {
                    System.out.println("ηΉθ΅ζε");

                    Message msg = handler.obtainMessage();
                    msg.obj = holder.imageView;
                    holder.star.setImageResource(R.drawable.stared);
                    handler.sendMessage(msg);
                } else {
                    System.out.println("ηΉθ΅ε€±θ΄₯");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }).start());

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
        TextView textView;
        ImageView star;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = mBinding.image;
            textView = mBinding.username;
            star = mBinding.star;
        }
    }
}
