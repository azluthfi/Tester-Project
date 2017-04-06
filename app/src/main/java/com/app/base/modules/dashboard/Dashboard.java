package com.app.base.modules.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.base.R;
import com.app.base.base.BaseMvpFragment;
import com.app.base.listeners.AlertLitener;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import butterknife.BindView;

@FragmentWithArgs
public class Dashboard extends BaseMvpFragment<IDashboardView, DashboardPresenter> implements IDashboardView, AlertLitener {

    @Arg String mParam5;
    @Arg String mParam1;
    @Arg String mParam2;
    @BindView(R.id.asdsad) TextView asdsad;

    public Dashboard() {
        // Required empty public constructor
    }

    @Override public DashboardPresenter createPresenter() {
        return new DashboardPresenter();
    }

    @Override public void onCancel(String action) {

    }

    @Override public void onSubmit(String action) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this); // read @Arg fields
        setAlertListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

}
