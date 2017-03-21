package com.app.base.modules.dashboard;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

/**
 * Created by j3p0n on 3/21/2017.
 */

interface IDashboardPresenter extends MvpPresenter<IDashboardView>{

    public void loadData();
}
