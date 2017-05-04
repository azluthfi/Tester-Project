package com.app.basarnas.modules.report.list;

import com.app.basarnas.models.RequestReport;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

public interface XListView extends MvpLceView<RequestReport> {

    public void resetAdapter();
}