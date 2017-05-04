package com.app.basarnas.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

/**
 * Created by luthfi on 24/03/2017.
 */

public abstract class BaseLceRefreshFragment<CV extends View, M, V extends MvpLceView<M>, P extends MvpPresenter<V>>
        extends BaseMvpLceFragment<SwipeRefreshLayout, M, V, P> implements SwipeRefreshLayout.OnRefreshListener {

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contentView.setOnRefreshListener(this);
//        int[] colors = getActivity().getResources().getIntArray(R.array.loading_colors);
//        contentView.setColorSchemeColors(colors);
    }

    @Override public void onRefresh() {
        loadData(true);
    }

    @Override public void showContent() {
        super.showContent();
        contentView.setRefreshing(false);
    }

    @Override public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, pullToRefresh);
        contentView.setRefreshing(false);
    }

    @Override public void showLoading(boolean pullToRefresh) {
        super.showLoading(pullToRefresh);
        if (pullToRefresh && !contentView.isRefreshing()) {
            contentView.post(new Runnable() {
                @Override public void run() {
                    contentView.setRefreshing(true);
                }
            });
        }
    }

    @Override public void onDestroyView() {
        contentView.setRefreshing(false);
        super.onDestroyView();
    }
}
