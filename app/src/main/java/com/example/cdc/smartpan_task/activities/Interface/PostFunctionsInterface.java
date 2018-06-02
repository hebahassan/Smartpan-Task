package com.example.cdc.smartpan_task.activities.Interface;

import com.example.cdc.smartpan_task.activities.Data.Login;
import com.example.cdc.smartpan_task.activities.Data.LoginCallback;
import com.example.cdc.smartpan_task.activities.Data.RegisterCallback;
import com.example.cdc.smartpan_task.activities.Data.User;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface PostFunctionsInterface {

    /**
    * API URL
    * */
    public static String API_URL = "http://www.smartpan.com.sa:5551/AndriodAPI";

    /**
    * Retrofit function to post data
    * */
    @POST("/register")
    public void registerUser(@Body User user, Callback<RegisterCallback> callback);

    @POST("/login")
    public void loginUser(@Body Login login, Callback<LoginCallback> callback);
}
