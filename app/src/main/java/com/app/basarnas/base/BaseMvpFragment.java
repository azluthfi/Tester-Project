package com.app.basarnas.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.basarnas.listeners.AlertListener;
import com.app.basarnas.listeners.FragmentInteractionListener;
import com.app.basarnas.utility.CommonUtilities;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by j3p0n on 1/2/2017.
 */

public abstract class BaseMvpFragment<V extends MvpView, P extends MvpPresenter<V>> extends MvpFragment<V, P> {

    protected FragmentInteractionListener mListener;
    private SweetAlertDialog pDialog;
    private Unbinder unbinder;
    public AlertListener alertListener;

    @LayoutRes protected abstract int getLayoutRes();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutRes(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setAlertListener(AlertListener alertListener) {
        this.alertListener = alertListener;
    }

//    public abstract void showConfirmDialog(int alertType, String title, String content, String confirmLabel, String cancelLabel, String action);

    /**
     * @param alertType    {@link SweetAlertDialog} Alert type Ex: SweetAlertDialog.WARNING_TYPE
     * @param title        Judul popup
     * @param content      Isi popup
     * @param confirmLabel Positive button label
     * @param cancelLabel  Negative button label
     * @param action       Callback action
     */
    public void showConfirmDialog(int alertType, String title, String content, String confirmLabel, String cancelLabel, final String action) {
        final SweetAlertDialog alert = CommonUtilities.buildAlert(getActivity(), alertType, title, content, confirmLabel, cancelLabel);
        alert.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.cancel();
                if (alertListener != null) {
                    alertListener.onCancel(action);
                }
            }
        });
        alert.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.cancel();
                if (alertListener != null) {
                    alertListener.onSubmit(action);
                }
            }
        });
    }

    public void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void showLoading(String content, boolean cancelable) {
        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(content);
        pDialog.setCancelable(cancelable);
        pDialog.show();
    }

    public void hideLoading() {
        if (pDialog != null) {
            pDialog.dismiss();
        }
    }

    public void hideLoading(SwipeRefreshLayout swipeRefreshLayout, ProgressBar progress) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        progress.setVisibility(View.GONE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionListener) {
            mListener = (FragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
