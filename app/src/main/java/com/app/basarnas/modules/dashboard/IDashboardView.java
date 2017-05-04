package com.app.basarnas.modules.dashboard;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by j3p0n on 3/21/2017.
 */

interface IDashboardView extends MvpView {

    public void isAuth();

    public void isNotUth();

    public void goToLogin(String type);

    public void goToReport();
}
