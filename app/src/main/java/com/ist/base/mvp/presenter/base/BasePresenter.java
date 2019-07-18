package com.ist.base.mvp.presenter.base;

import com.ist.base.mvp.view.base.BaseView;

public class BasePresenter<V extends BaseView> {
    private V view;

    public void attachView(V view) {
        this.view = view;
    }

    public void detachView() {
        this.view = null;
    }

    public V getView() {
        return view;
    }

    public boolean isAttachView() {
        return view != null;
    }
}
