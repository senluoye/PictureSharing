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

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.wkh.picturesharingapplication.R;
import com.wkh.picturesharingapplication.bean.model.picture.UploadPictureModel;
import com.wkh.picturesharingapplication.http.RetrofitRequest;
import com.wkh.picturesharingapplication.ui.widget.PhotoPopupWindow;
import com.wkh.picturesharingapplication.utils.PreferenceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AddFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "AddFragment";
    private Context mContext = null;
    private ImageView mImage;
    private String imgUrl;
    //发布视图
    private View rootView;
    //照片选取类
    PhotoPopupWindow mPhotoPopupWindow;
    //权限常量
    private static final int REQUEST_IMAGE_GET = 0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_SMALL_IMAGE_CUTTING = 2;


    String url = "http://192.168.244.1:18080";
    Bitmap mbitmap = null;
    Retrofit mRetrofit;
    String filePath;
    PreferenceUtils mPreferenceUtils;

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

        mContext = getActivity();
        rootView = inflater.inflate(R.layout.fragment_add, container, false);

        init();
        return rootView;
    }

    private void init() {
        EditText mEditText = rootView.findViewById(R.id.add_content);
        mImage = rootView.findViewById(R.id.add_img);
        Button mButton = rootView.findViewById(R.id.add_button);
        //设置点击事件
        mImage.setOnClickListener(this);
        mButton.setOnClickListener(this);

        mPreferenceUtils = PreferenceUtils.getInstance();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(url)
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
            //上传
            case R.id.add_button:
                sentImage(filePath);
                break;
            default:
                break;
        }
    }

    //进入相册
    protected void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, REQUEST_IMAGE_GET);
    }

    //捕获状态
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;

        Uri uri;
        Uri fileUri = null;
        Bundle bundle;
        switch (requestCode){

            //图库
            case REQUEST_IMAGE_GET:
                // 此处的uri是content类型的。还有一种是file 型的。应该转换为后者
                uri = data.getData();
                Log.d(TAG, "onActivityResult: " + uri);
                // 记住： 要转化为file类型的uri
//                fileUri = converUri(uri);
                // 启动裁剪
//                starImageZoom(fileUri);

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(uri,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                filePath = cursor.getString(columnIndex);
                mImage.setImageURI(uri);
                break;

            //相机
            case REQUEST_IMAGE_CAPTURE:
                bundle = data.getExtras();
                if (bundle != null) {
                    Bitmap bitmap = bundle.getParcelable("data");
                    // 记住： 要转化为 file 类型的Uri
                    uri = saveBitmap(bitmap);
                    // 启动裁剪器
                    starImageZoom(uri);
                }
                break;

            //裁剪
            case REQUEST_SMALL_IMAGE_CUTTING:
                bundle = data.getExtras();
                Bitmap bitmap = bundle.getParcelable("data");
                mImage.setImageBitmap(bitmap);
                mbitmap = bitmap;
                break;

            default:
                break;
        }
    }

    /**
     * 把 content 类型的uri 转换为 file 类型的 uri （其实，就是通过content类型的uri
     * 解释为bitmap，然后保存在sd卡中，通过保存路径来获得file类型uri）
     * @param uri
     * @return
     */
    private Uri converUri(Uri uri) {
        InputStream is = null;
        try {
            is = Objects.requireNonNull(getActivity()).getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return saveBitmap(bitmap);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 保存经过Base64编码的图片到服务器
     * @param filePath
     */
    private void sentImage(String filePath) {

        new Thread(() -> {
            try {
                File file = new File(filePath);

                System.out.println(filePath);

                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                RetrofitRequest request = mRetrofit.create(RetrofitRequest.class);
                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                System.out.println("qks" + body.toString());

                Response<UploadPictureModel> response =
                        request.upLoadPicture(body).execute();

                System.out.println(response.toString());

                if (response.code() == HttpURLConnection.HTTP_OK) {
                    // 利用Handler切换成主线程进行UI操作
                    System.out.println("提交成功");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }


    /**
     * 把 Bitmap保存在SD卡路径后，返回file 类型的 uri
     *
     * @param bm
     * @return
     */
    private Uri saveBitmap(Bitmap bm) {

        File tmDir = new File(Environment.getExternalStorageDirectory()
                + "/qiujun");
        if (!tmDir.exists()) {
            tmDir.mkdir();
        }
        File img = new File(tmDir.getAbsolutePath() + "pic.png");
        try {
            FileOutputStream fos = new FileOutputStream(img);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(img);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过 file 类型的 uri 去启动系统图片裁剪器
     *
     * @param uri
     */
    private void starImageZoom(Uri uri) {
        // 打开裁剪器
        Intent intent = new Intent("com.android.camera.action.CROP");
        // 设置 裁剪的数据uri 和类型 image
        intent.setDataAndType(uri, "image/*");
        // 是可裁剪的
        intent.putExtra("crop", true);
        // 设置裁剪宽高的比例 1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 设置最终裁剪出来的图片的宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        // 设置 最终裁剪完是通过intent 传回来的
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUEST_SMALL_IMAGE_CUTTING);
    }

}