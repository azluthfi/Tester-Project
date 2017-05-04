package com.app.basarnas.modules.report.detail;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

public interface XDetailPresenter extends MvpPresenter<XDetailView> {
    public void loadData(boolean pullToRefresh);
}