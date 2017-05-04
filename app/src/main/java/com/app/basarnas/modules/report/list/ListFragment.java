package com.app.basarnas.modules.report.list;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.app.basarnas.R;
import com.app.basarnas.adapter.ReportAdapterAdapter;
import com.app.basarnas.base.BaseLceRefreshFragment;
import com.app.basarnas.models.RequestReport;
import com.app.basarnas.modules.dashboard.DashboardFragment;
import com.app.basarnas.modules.report.detail.DetailFragmentBuilder;
import com.app.basarnas.modules.report.post.PostFragment;
import com.app.basarnas.utility.DividerItemDecoration;
import com.app.basarnas.utility.FragmentStack;
import com.app.basarnas.utility.ItemClickSupport;

import butterknife.BindView;
import butterknife.OnClick;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;

public class ListFragment extends BaseLceRefreshFragment<SwipeRefreshLayout, RequestReport, XListView, ListPresenter>
        implements XListView, FragmentStack.OnBackPressedHandlingFragment {

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.contentView) SwipeRefreshLayout contentView;
    @BindView(R.id.errorView) TextView errorView;
    @BindView(R.id.loadingView) CircularProgressBar loadingView;
    @BindView(R.id.fab) FloatingActionButton fab;
    private ReportAdapterAdapter adapter;

    public ListFragment() {

    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override public ListPresenter createPresenter() {
        return new ListPresenter(getContext());
    }

    @Override protected int getLayoutRes() {
        return R.layout.fragment_list;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ReportAdapterAdapter();
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.LIST_VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override public void onItemClicked(RecyclerView recyclerView, final int position, View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mListener.gotoPage(new DetailFragmentBuilder(String.valueOf(position)).build(), false, null);
                    }
                }, 100);

            }
        });

        loadData(false);
    }

    @Override protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return e.getMessage();
    }

    @Override public void setData(RequestReport data) {
        adapter.setItems(data.getReports());
    }

    @Override public void loadData(boolean pullToRefresh) {
        presenter.loadData(pullToRefresh);
    }

    @Override public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_send).setVisible(false);
    }

    @Override public boolean onBackPressed() {
        mListener.gotoPage(new DashboardFragment(), true, "dashboard");
        return true;
    }

    @Override public void resetAdapter() {
        adapter.clear();
    }

    @OnClick(R.id.fab) public void onViewClicked() {
        mListener.gotoPage(new PostFragment(), false, null);
    }
}