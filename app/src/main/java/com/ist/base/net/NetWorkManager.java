package com.ist.base.net;

import com.ist.base.net.api.HomeTabApi;

import me.ghui.fruit.converter.retrofit.FruitConverterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetWorkManager {
    private NetRequest request;
    private static NetWorkManager instance;
    private Retrofit retrofit;
    private HomeTabApi homeTabApi;

    private NetWorkManager() {

    }

    public static NetWorkManager getInstance() {
        if (instance == null) {
            synchronized (NetWorkManager.class) {
                if (instance == null) {
                    instance = new NetWorkManager();
                }
            }
        }
        return instance;
    }

    private void init() {
        OkHttpClient client = new OkHttpClient.Builder().build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(NetRequest.HOST)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(FruitConverterFactory.create())//html解析
                .build();
    }

    public NetRequest getRequest() {
        if (request == null) {
            init();
            request = retrofit.create(NetRequest.class);
        }
        return request;
    }

    public HomeTabApi getHomeTabApi() {
        if (homeTabApi == null) {
            init();
            homeTabApi = retrofit.create(HomeTabApi.class);
        }
        return homeTabApi;
    }
}
