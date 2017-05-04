package com.app.basarnas.modules.dashboard;

import android.content.Context;

import com.app.basarnas.base.BasePresenter;
import com.app.basarnas.models.EventLogin;
import com.app.basarnas.modules.auth.LoginActivity;
import com.app.basarnas.utility.Config;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by j3p0n on 3/21/2017.
 */

class DashboardPresenter extends BasePresenter<IDashboardView>
        implements IDashboardPresenter{

    public DashboardPresenter(Context context) {
        super(context);
    }

    @Override public void loadData() {

    }

    private Boolean isAuth(){
        return prefs.getPreferencesString(Config.CUSTOMER_PHONE) != null;
    }

    @Override public void checkIsAuth() {
        if (isAuth()){
            getView().isAuth();
        }else{
            getView().isNotUth();
        }
    }

    @Override public void onEventReport() {
        if (isAuth()){
            getView().goToReport();
        }else{
            getView().goToLogin(LoginActivity.EXTRA_TYPE_REPORT);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventLogin(EventLogin eventLogin) {
        checkIsAuth();
    }

    @Override public void attachView(IDashboardView view) {
        super.attachView(view);
        EventBus.getDefault().register(this);
    }

    @Override public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        EventBus.getDefault().unregister(this);
    }
}
