package com.wkh.picturesharingapplication.ui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.wkh.picturesharingapplication.R;
import com.wkh.picturesharingapplication.bean.entity.Post;
import com.wkh.picturesharingapplication.bean.model.picture.UploadPictureModel;
import com.wkh.picturesharingapplication.bean.model.post.PostSpaceModel;
import com.wkh.picturesharingapplication.http.RetrofitRequest;
import com.wkh.picturesharingapplication.ui.LoginActivity;
import com.wkh.picturesharingapplication.ui.MainActivity;
import com.wkh.picturesharingapplication.ui.widget.PhotoPopupWindow;
import com.wkh.picturesharingapplication.utils.PreferenceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;


public class AddFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "AddFragment";
    private static final int SUCCESS = 1;
    private static final int FAILURE = 2;
    private ImageView mImage;
    private EditText mEditText;
    //????????????
    private View rootView;
    //????????????
    private static final int REQUEST_IMAGE_GET = 0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_SMALL_IMAGE_CUTTING = 2;
    Retrofit mRetrofit;
    String filePath;
    PreferenceUtils preferenceUtils;
    HomeFragment mHomeFragment;

    final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    Toast.makeText(getActivity(), "????????????", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_body, mHomeFragment)
                            .commit();
                    break;
                case FAILURE:
                    Toast.makeText(getActivity(), (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public AddFragment() {
    }

    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        Context mContext = getActivity();
        rootView = inflater.inflate(R.layout.fragment_add, container, false);

        init();
        return rootView;
    }

    private void init() {
        mEditText = rootView.findViewById(R.id.add_content);
        mImage = rootView.findViewById(R.id.add_img);
        Button mButton = rootView.findViewById(R.id.add_button);
        mHomeFragment = new HomeFragment();

        //??????????????????
        mImage.setOnClickListener(this);
        mButton.setOnClickListener(this);

        preferenceUtils = PreferenceUtils.getInstance();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(getActivity().getString(R.string.url))
                .client(new OkHttpClient.Builder()
                        .callTimeout(60, TimeUnit.SECONDS)
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .build())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.add_img:
                getImageFromAlbum();
                break;
            //??????
            case R.id.add_button:
                sentImage(filePath);
                break;
            default:
                break;
        }
    }

    //????????????
    protected void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//????????????
        startActivityForResult(intent, REQUEST_IMAGE_GET);
    }

    //????????????
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;

        Uri uri;
        switch (requestCode){
            //??????
            case REQUEST_IMAGE_GET:
                uri = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(uri,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                filePath = cursor.getString(columnIndex);
                mImage.setImageURI(uri);
                break;

            default:
                break;
        }
    }

    /**
     * @param filePath
     */
    private void sentImage(String filePath) {
        new Thread(() -> {
            try {
                File file = new File(filePath);
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                RetrofitRequest request = mRetrofit.create(RetrofitRequest.class);
                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                Response<UploadPictureModel> response =
                        request.upLoadPicture(preferenceUtils.getToken(), body).execute();


                if (response.code() == HttpURLConnection.HTTP_OK) {
                    String id = response.body().getData();
                    System.out.println("???????????????id???" + id);

                    Post post = new Post();
                    post.setContent(mEditText.getText().toString());
                    post.setPictures(id);

                    Response<PostSpaceModel> postResponse =
                            request.addPost(preferenceUtils.getToken(), post).execute();

                    System.out.println(postResponse.body().toString());
                    if (postResponse.code() == HttpURLConnection.HTTP_OK){

                        System.out.println("????????????");
                        String postId = postResponse.body().getData().getId();
                        System.out.println("Data:" + postResponse.body().getData());
                        System.out.println("??????postId???" + postId);

                        handler.sendEmptyMessage(SUCCESS);
                    } else {
                        Message message = Message.obtain();
                        message.what = FAILURE;
                        message.obj = "??????????????????";
                        handler.sendMessage(message);
                    }
                } else {
                    Message message = Message.obtain();
                    message.what = FAILURE;
                    message.obj = "??????????????????";
                    handler.sendMessage(message);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

}