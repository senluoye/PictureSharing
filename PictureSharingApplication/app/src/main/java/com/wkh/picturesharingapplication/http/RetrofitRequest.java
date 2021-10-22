package com.wkh.picturesharingapplication.http;

import com.wkh.picturesharingapplication.bean.entity.Post;
import com.wkh.picturesharingapplication.bean.model.picture.UploadPictureModel;
import com.wkh.picturesharingapplication.bean.model.post.PostSpaceModel;
import com.wkh.picturesharingapplication.bean.model.post.getAllPostModel;
import com.wkh.picturesharingapplication.bean.model.user.LoginModel;
import com.wkh.picturesharingapplication.bean.entity.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

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
    Call<UploadPictureModel> upLoadPicture(@Header("token") String token, @Part MultipartBody.Part file);

    /**
     * 发布动态
     * @param token
     * @param content
     * @param pictures
     */
    @POST("/api/post")
    Call<PostSpaceModel> addPost(@Header("token") String token, @Body Post post);

    /**
     * 获取全部动态
     * @return
     */
    @GET("/api/post/hello")
    Call<getAllPostModel> getAllPost(@Header("token") String token);

    /**
     * 获取一张图片
     * @param token
     * @param pictureId
     */
    @GET("/{id}")
    Call<ResponseBody> getPicture(@Header("token") String token, @Path("id") String pictureId);

}
