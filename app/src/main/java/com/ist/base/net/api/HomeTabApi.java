package com.ist.base.net.api;

import com.ist.base.bean.HomeBean;

import io.reactivex.Observable;
import me.ghui.retrofit.converter.annotations.Html;
import retrofit2.http.GET;

public interface HomeTabApi {
    String HOST = "https://stock.tuchong.com/";

    @GET("/")
    @Html
    Observable<HomeBean> getHome();
}
