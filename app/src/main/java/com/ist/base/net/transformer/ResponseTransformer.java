package com.ist.base.net.transformer;

import android.util.Log;

import com.ist.base.net.Exception.ApiException;
import com.ist.base.net.Exception.CustomException;
import com.ist.base.net.NetResponse;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

public class ResponseTransformer {

    public static <T> ObservableTransformer<NetResponse<T>, T> handleResult() {
        return upstream -> upstream
                .onErrorResumeNext(new ErrorResumeFunction<>())
                .flatMap(new ResponseFunction<>());
    }

    /**
     * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
     *
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends NetResponse<T>>> {

        @Override
        public ObservableSource<? extends NetResponse<T>> apply(Throwable throwable) throws Exception {
            return Observable.error(CustomException.handleException(throwable));
        }
    }

    /**
     * 服务其返回的数据解析
     * 正常服务器返回数据和服务器可能返回的exception
     *
     * @param <T>
     */
    private static class ResponseFunction<T> implements Function<NetResponse<T>, ObservableSource<T>> {

        @Override
        public ObservableSource<T> apply(NetResponse<T> tNetResponse) throws Exception {
            int code = tNetResponse.getRet();
            String message = tNetResponse.getMsg();
            Log.e("ResponseFunction", "code: " + code);
            if (code == 200) {
                return Observable.just(tNetResponse.getData());
            } else {
                return Observable.error(new ApiException(code, message));
            }
        }
    }
}
