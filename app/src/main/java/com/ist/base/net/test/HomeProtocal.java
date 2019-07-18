package com.ist.base.net.test;

import android.util.Log;

import com.ist.base.bean.Message;
import com.ist.base.net.NetRequest;
import com.ist.base.net.NetWorkManager;
import com.ist.base.net.scheduler.SchedulerProvider;
import com.ist.base.net.transformer.ResponseTransformer;

import java.util.List;

import io.reactivex.functions.Consumer;

public class HomeProtocal {

    public void t() {
        NetRequest request = NetWorkManager.getInstance().getRequest();
        SchedulerProvider schedulerProvider = SchedulerProvider.getInstance();
        request.get()
                .compose(ResponseTransformer.handleResult())
                .compose(schedulerProvider.applySchedulers())
                .subscribe(new Consumer<List<Message>>() {
                               @Override
                               public void accept(List<Message> messages) throws Exception {
                                   Log.e("zyc", "accept: " + messages);
                               }
                           }
                        , new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.e("zyc", "Exception: " + throwable.getMessage());
                            }
                        });
    }
}
