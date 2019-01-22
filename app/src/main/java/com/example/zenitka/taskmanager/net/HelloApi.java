package com.example.zenitka.taskmanager.net;


import com.example.zenitka.taskmanager.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HelloApi {

    @POST("enter/")
    Observable<CodeToken> enterUser(@Body InputLoginPassword inputLoginPassword);

    @POST("create_user/")
    Observable<CodeID> registerUser(@Body NameSurnameLoginPassword nameSurnameLoginPassword);

    @POST("create_team/")
    Observable<CodeID> createTeam(@Body NameDescription nameDescription);

    @GET("load_teams_list/{token}")
    Observable<CodeListTeam> loadTeamsList(@Path("token") String token);
}
