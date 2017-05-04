package com.app.basarnas.modules.report.list;

import android.content.Context;
import android.os.Handler;

import com.app.basarnas.base.BaseRxLcePresenter;
import com.app.basarnas.models.RequestReport;
import com.app.basarnas.utility.CommonUtilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class ListPresenter extends BaseRxLcePresenter<XListView, RequestReport>
        implements XListPresenter {

    public ListPresenter(Context context) {
        super(context);
    }


    @Override public void loadData(boolean pullToRefresh) {
        if (isViewAttached()){
            getView().showLoading(pullToRefresh);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Type mapType = new TypeToken<RequestReport>() {
                }.getType();
                RequestReport requestPosting = new Gson().fromJson(CommonUtilities.loadJSONFromAsset(context, "RequestReport.json"), mapType);

                if (isViewAttached()){
                    getView().resetAdapter();
                    getView().setData(requestPosting);
                    getView().showContent();
                }
            }
        }, 2000);
    }
}