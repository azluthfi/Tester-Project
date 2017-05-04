package com.app.basarnas.adapter;

import com.app.basarnas.base.BaseRecyclerViewAdapter;
import com.app.basarnas.holder.ItemReportHolder;
import com.app.basarnas.models.Report;

/**
 * Created by luthfi on 26/04/2017.
 */
public class ReportAdapterAdapter extends BaseRecyclerViewAdapter<Report, ItemReportHolder> {

    public ReportAdapterAdapter() {
        super(ItemReportHolder.class);
    }

    @Override protected void populateViewHolder(ItemReportHolder viewHolder, Report item, int position) {
        viewHolder.bindView(item);
    }
}