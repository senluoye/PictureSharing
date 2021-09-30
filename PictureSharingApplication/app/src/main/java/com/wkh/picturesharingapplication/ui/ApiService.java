package com.wkh.picturesharingapplication.ui;

import com.wkh.picturesharingapplication.bean.entity.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    /*无参GET请求 */
    //没有数据就填 '.' 或者 '/'
    @GET("getUser")
    Call<User> getUser();

    /*有参GET请求 */
    @GET("getParamUser")
    Call<User>getParamUser(@Query("id") int id);

    /*无参POST请求 */
    @POST("postNoParamUser")
    Call<User>postNoParamUser();

    /*有参POST请求 */
    @FormUrlEncoded
    @POST("postParamUser")
    Call<User> postParamUser(@Field("id") int id);

    /*JSON化参数POST请求 */
    @POST("postObjectParamUser")
    Call<User>postObjectParamUser(@Body User user);

}
