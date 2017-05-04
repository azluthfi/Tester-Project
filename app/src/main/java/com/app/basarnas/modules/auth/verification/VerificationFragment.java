package com.app.basarnas.modules.auth.verification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.basarnas.R;
import com.app.basarnas.base.BaseMvpFragment;
import com.app.basarnas.listeners.AlertListener;
import com.app.basarnas.modules.auth.login.LoginFragment;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

@FragmentWithArgs
public class VerificationFragment extends BaseMvpFragment<XVerificationView, VerificationPresenter>
        implements XVerificationView, AlertListener {

    @Arg String phone;
    @BindView(R.id.ivVerification) ImageView ivVerification;
    @BindView(R.id.etVerification) TextInputEditText etVerification;
    @BindView(R.id.btnVerification) Button btnVerification;
    @BindView(R.id.tvSubhead) TextView tvSubhead;

    public VerificationFragment() {

    }

    @Override public VerificationPresenter createPresenter() {
        return new VerificationPresenter(getContext());
    }

    @Override protected int getLayoutRes() {
        return R.layout.fragment_verification;
    }


    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvSubhead.setText(getString(R.string.text_subhead_verification) + " " + phone);
        setAlertListener(this);
    }

    @OnClick(R.id.btnVerification) public void onVerificationClicked() {
        if (etVerification.getText().length() == 6){
            showConfirmDialog(SweetAlertDialog.SUCCESS_TYPE, "Berhasil", "Kode Verifikasi benar, silahkan login", "Login", null, "login");
        }else{
            etVerification.requestFocus();
            etVerification.setError("Kode Verifikasi tidak sesuai");
        }
    }

    @Override public void onCancel(String action) {

    }

    @Override public void onSubmit(String action) {
        mListener.gotoPage(new LoginFragment(), false, null);
    }
}