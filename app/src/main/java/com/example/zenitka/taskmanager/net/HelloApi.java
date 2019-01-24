package com.example.zenitka.taskmanager.net;


import com.example.zenitka.taskmanager.Project;
import com.example.zenitka.taskmanager.Team;
import com.example.zenitka.taskmanager.TeamTask;
import com.example.zenitka.taskmanager.User;

import java.util.List;

import io.reactivex.Completable;
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

    @POST("delete_user_from_team/")
    Observable<Code> deleteUserFromTeam(@Body TokenTeam tokenTeam);

    @POST("change_team/")
    Observable<Code> updateTeam(@Body Team team);

    @POST("create_project/")
    Observable<CodeID> createProject(@Body NameDateTeamIDInfo nameDateTeamIDInfo);

    @GET("load_projects_list/{token}/{team_id}")
    Observable<CodeListProject> loadProjectsList(@Path("token") String token, @Path("team_id") int team_id);

    @POST("delete_project/")
    Observable<Code> deleteProject(@Body TokenProject tokenProject);

    @POST("change_project/")
    Observable<Code> updateProject(@Body Project project);

    @POST("create_task/")
    Observable<CodeID> createTask(@Body TeamTask toPut);

    @POST("change_task/")
    Observable<Code> updateTeamTask(@Body TeamTask teamTask);

    @GET("load_tasks_list/{token}/{project_id}")
    Observable<CodeListTask> loadTasksList(@Path("token") String token, @Path("project_id") int project_id);

    @POST("delete_task/")
    Observable<Code> deleteTask(@Body TokenTask tokenTask);
}
