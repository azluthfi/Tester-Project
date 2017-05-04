package com.app.basarnas.modules.report.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.basarnas.R;
import com.app.basarnas.base.BaseLceRefreshFragment;
import com.app.basarnas.models.Report;
import com.app.basarnas.models.RequestReport;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import butterknife.BindView;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;

@FragmentWithArgs
public class DetailFragment extends BaseLceRefreshFragment<SwipeRefreshLayout, RequestReport, XDetailView, DetailPresenter>
        implements XDetailView {

    @Arg String idReport;
    @BindView(R.id.tvHeaderContent) TextView tvHeaderContent;
    @BindView(R.id.ivLocation) ImageView ivLocation;
    @BindView(R.id.tvLocation) TextView tvLocation;
    @BindView(R.id.tvStatus) TextView tvStatus;
    @BindView(R.id.tvReport) TextView tvReport;
    @BindView(R.id.contentView) SwipeRefreshLayout contentView;
    @BindView(R.id.errorView) TextView errorView;
    @BindView(R.id.loadingView) CircularProgressBar loadingView;

    private Report report;

    public DetailFragment() {

    }

    @Override public DetailPresenter createPresenter() {
        return new DetailPresenter(getContext());
    }

    @Override protected int getLayoutRes() {
        return R.layout.fragment_detail;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData(false);
    }

    @Override protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return e.getMessage();
    }

    @Override public void setData(RequestReport data) {
        report = data.getReports().get(Integer.valueOf(idReport));
        tvHeaderContent.setText(report.getReportDate());
        tvLocation.setText(report.getReportLocation());
        tvReport.setText(report.getReportDesc());
        switch (report.getReportStatus().toLowerCase()){
            case "new":
                tvStatus.setText("Menunggu");
                tvStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.status_new));
                break;
            case "progress":
                tvStatus.setText("Diproses");
                tvStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.status_pending));
                break;
            case "reject":
                tvStatus.setText("Ditolak");
                tvStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.status_cancel));
                break;
            case "confirm":
                tvStatus.setText("Diterima");
                tvStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.status_success));
                break;
        }
    }

    @Override public void loadData(boolean pullToRefresh) {
        presenter.loadData(pullToRefresh);
    }
}