package com.wkh.picturesharingapplication.http;

import com.wkh.picturesharingapplication.bean.model.picture.UploadPictureModel;
import com.wkh.picturesharingapplication.bean.model.post.PostSpaceModel;
import com.wkh.picturesharingapplication.bean.model.user.LoginModel;
import com.wkh.picturesharingapplication.bean.entity.User;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitRequest {

    /**
     * 登陆
     * @param user
     * @return
     */
    @GET("/api/user/login")
    Call<LoginModel> login(@Body User user);

    /**
     * 注册
     * @param user
     * @return
     */
    @POST("/api/user/register")
    Call<LoginModel> register(@Body User user);

    /**
     * 上传图片
     * @param token
     * @param file
     * @return
     */
    @POST("/api/picture")
    @Multipart
    Call<UploadPictureModel> upLoadPicture(@Part MultipartBody.Part file);

    /**
     * 发布动态
     * @param token 令牌
     * @param content 动态内容
     * @param pictures 动态中的图片
     */
    @POST("/api/post")
    @FormUrlEncoded
    Call<PostSpaceModel> postSpace(@Header("Authorization") String token, @Field("content") String content,
                                         @Field("pictures") List<String> pictures);

}
