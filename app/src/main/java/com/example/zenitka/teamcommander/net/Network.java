package com.example.zenitka.teamcommander.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {

    private static Network ourInstance = new Network();
    private static HelloApi api;

    private static final String BASE_URL = "http://10.23.45.214:5000/db/api/";

    public static Network getInstance() {
        return ourInstance;
    }

    private Network() {
        OkHttpClient client = new OkHttpClient.Builder()
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder().setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();

        api = retrofit.create(HelloApi.class);
    }

    public HelloApi getApi() {
        return api;
    }
}
