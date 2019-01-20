package com.example.zenitka.taskmanager.net;


import com.example.zenitka.taskmanager.Team_.User;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HelloApi {

    @GET("get_user/{id}/")
    Observable<User> getUser(@Path("id") long id);

    @POST("create_user/")
    Observable<User> saveUser(@Body User user);

    @POST("enter/")
    Observable<CodeToken> enterUser(@Body InputLoginPassword inputLoginPassword);

    @POST("create_user/")
    Observable<CodeID> registerUser(@Body NameSurnameLoginPassword nameSurnameLoginPassword);
}
