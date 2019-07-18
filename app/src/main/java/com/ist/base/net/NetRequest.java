package com.ist.base.net;

import com.ist.base.bean.Message;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface NetRequest {
    // 填上需要访问的服务器地址
    public static String HOST = "https://www.baidu.com/";

    @GET("/")
    Observable<NetResponse<List<Message>>> get();
}
