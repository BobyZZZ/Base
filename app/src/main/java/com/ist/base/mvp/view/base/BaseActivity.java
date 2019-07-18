package com.ist.base.mvp.view.base;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ist.base.R;
import com.ist.base.mvp.presenter.base.BasePresenter;
import com.ist.base.utils.StatusBarUtils;

import butterknife.ButterKnife;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView, View.OnClickListener {

    protected View headerLayout;
    protected ViewGroup contentLayout;
    protected ImageView ivHeaderLeft;
    protected TextView tvHeaderTitle;
    protected ImageView ivHeaderRight;

    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (shouldTransparentAndCoverStatusBar()) {
            StatusBarUtils.transparentAndCoverStatusBar(this);
        }
        setContentView(R.layout.activity_base);

        presenter = getPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }

        initData();

        initLayout();

        initListener();

        process();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }

    private void initLayout() {
        findViewAndSetListener();

        //如果设置了透明状态栏,并且headerlayout没有隐藏
        if (shouldTransparentAndCoverStatusBar() && shouldShowHeader()) {
            ViewGroup.LayoutParams lp = headerLayout.getLayoutParams();
            int statusBarHeight = StatusBarUtils.getStatusBarHeight(this);
            lp.height += statusBarHeight;
            headerLayout.setPadding(0, statusBarHeight, 0, 0);
        }

        headerLayout.setVisibility(shouldShowHeader() ? View.VISIBLE : View.GONE);
        View view = LayoutInflater.from(this).inflate(getContentLayoutId(), null);
        contentLayout.addView(view);
        ButterKnife.bind(this, view);

        initView(view);
    }

    private void findViewAndSetListener() {
        headerLayout = findViewById(R.id.header_layout);
        contentLayout = findViewById(R.id.content_layout);
        ivHeaderLeft = findViewById(R.id.header_iv_left);
        ivHeaderRight = findViewById(R.id.header_iv_right);
        tvHeaderTitle = findViewById(R.id.header_tv_title);

        ivHeaderLeft.setOnClickListener(this);
        ivHeaderRight.setOnClickListener(this);
    }

    protected boolean shouldShowHeader() {
        return true;
    }

    protected void onRightClick(View v) {

    }

    protected void onLeftClick(View v) {
        finish();
//        overridePendingTransition(R.anim.translation_right_in, R.anim.translation_left_out);
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        overridePendingTransition(R.anim.translation_right_in, R.anim.translation_left_out);
//    }

    protected void setHeaderLayoutBackgroundColor(int color) {
        headerLayout.setBackgroundColor(color);
    }

    protected void setHeaderLayoutBackgroundResource(int res) {
        headerLayout.setBackgroundResource(res);
    }

    protected void setHeaderLayoutBackground(Drawable drawable) {
        headerLayout.setBackground(drawable);
    }

    protected boolean shouldTransparentAndCoverStatusBar() {
        return false;
    }

    protected abstract P getPresenter();

    protected abstract int getContentLayoutId();

    protected abstract void initData();

    protected abstract void initView(View view);

    protected abstract void initListener();

    protected abstract void process();

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_iv_left:
                onLeftClick(view);
                break;
            case R.id.header_iv_right:
                onRightClick(view);
                break;
        }
    }
}
