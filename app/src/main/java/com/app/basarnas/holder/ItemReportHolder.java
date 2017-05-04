package com.app.basarnas.holder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.basarnas.R;
import com.app.basarnas.models.Report;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemReportHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tvDate) TextView tvDate;
    @BindView(R.id.tvStatus) TextView tvStatus;
    @BindView(R.id.tvReport) TextView tvReport;

    private Context context;

    public ItemReportHolder(LayoutInflater inflater, ViewGroup parent) {
        this(inflater.inflate(R.layout.item_report, parent, false));
    }

    public ItemReportHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
        this.context = view.getContext();
    }

    public void bindView(Report report){
        tvDate.setText(report.getReportDate());
        tvReport.setText(report.getReportDesc());
        switch (report.getReportStatus().toLowerCase()){
            case "new":
                tvStatus.setText("Menunggu");
                tvStatus.setTextColor(ContextCompat.getColor(context, R.color.status_new));
                break;
            case "progress":
                tvStatus.setText("Diproses");
                tvStatus.setTextColor(ContextCompat.getColor(context, R.color.status_pending));
                break;
            case "reject":
                tvStatus.setText("Ditolak");
                tvStatus.setTextColor(ContextCompat.getColor(context, R.color.status_cancel));
                break;
            case "confirm":
                tvStatus.setText("Diterima");
                tvStatus.setTextColor(ContextCompat.getColor(context, R.color.status_success));
                break;
        }
    }

}
