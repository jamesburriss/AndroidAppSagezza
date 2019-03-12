package com.example.myapplication.retrofit;

import retrofit2.Call;
import retrofit2.http.*;
import com.example.myapplication.retrofit.models.ModelToken;
import com.example.myapplication.retrofit.models.ModelUsers;

public interface SaggezzaService
{
    /*
     * /fetch_token
     */
    @FormUrlEncoded
    @POST("authenticate/")
    Call<ModelToken> fetch_token(@Field("username") String username, @Field("password") String password);

    /*
     * /users
     */
    @GET("users/")
    Call<ModelUsers> users(@Query("page") int page);

    @GET("users/")
    Call<ModelUsers> users(@Query("search") String search, @Query("department") String department, @Query("page") int page);
}
