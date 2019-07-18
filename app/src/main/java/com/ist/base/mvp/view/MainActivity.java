package com.ist.base.mvp.view;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.ist.base.MyFragment;
import com.ist.base.R;
import com.ist.base.bean.HomeBean;
import com.ist.base.mvp.presenter.base.BasePresenter;
import com.ist.base.mvp.view.base.BaseActivity;
import com.ist.base.net.NetWorkManager;
import com.ist.base.net.api.HomeTabApi;
import com.ist.base.net.scheduler.SchedulerProvider;
import com.ist.base.net.test.HomeProtocal;
import com.ist.base.net.transformer.ResponseTransformer;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity {

    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.bnv)
    BottomBar bnv;

    private MyFragment home;
    private MyFragment pyq;
    private MyFragment caidan, setting;
    private List<MyFragment> fragments;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    protected void initData() {
        fragments = new ArrayList<>();
        home = MyFragment.newInstance("Home");
        pyq = MyFragment.newInstance("Pyq");
        caidan = MyFragment.newInstance("Caidan");
        setting = MyFragment.newInstance("Setting");
        fragments.add(home);
        fragments.add(pyq);
        fragments.add(caidan);
        fragments.add(setting);
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initListener() {
        initBnv();
    }

    @Override
    protected void process() {
        HomeTabApi homeTabApi = NetWorkManager.getInstance().getHomeTabApi();
        Observable<HomeBean> home = homeTabApi.getHome();
        home.compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe(new Consumer<HomeBean>() {
                    @Override
                    public void accept(HomeBean homeBean) throws Exception {
                        Log.e("zyc", "homeBean: " + homeBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("zyc", "throwableaaaaaaaaaaaaaaaaaa: " + throwable.getMessage());
                    }
                });
    }

    private void initBnv() {
        bnv.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId) {
                    case R.id.tab_favorites:
                        switchFragment(home);
                        break;
                    case R.id.tab_nearby:
                        switchFragment(pyq);
                        break;
                    case R.id.tab_friends:
                        switchFragment(caidan);
                        break;
                    case R.id.tab_me:
                        switchFragment(setting);
                        break;
                    default:
                        Log.e("zyc", "onTabReSelected: default");
                }
            }
        });
    }

    private void addTips(int id, int count) {
        BottomBarTab tab = bnv.getTabWithId(id);
        tab.setBadgeCount(count);
    }

    private void switchFragment(MyFragment fragment) {
        hideAllFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (!fragment.isAdded()) {
            ft.add(R.id.fl_content, fragment, fragment.getName());
        }
        ft.show(fragment);
        ft.commit();
    }

    private void hideAllFragment() {
        for (MyFragment fragment : fragments) {
            if (fragment != null && !fragment.isHidden()) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.hide(fragment);
                ft.commit();
            }
        }
    }
}
