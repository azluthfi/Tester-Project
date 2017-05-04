package com.app.basarnas.modules.report.list;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

public interface XListPresenter extends MvpPresenter<XListView> {

    public void loadData(boolean pullToRefresh);
}