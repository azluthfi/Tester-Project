package com.app.basarnas.modules.auth.register;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.basarnas.R;
import com.app.basarnas.base.BaseMvpFragment;
import com.app.basarnas.modules.auth.login.LoginFragment;
import com.app.basarnas.modules.auth.verification.VerificationFragmentBuilder;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterFragment extends BaseMvpFragment<XRegisterView, RegisterPresenter>
        implements XRegisterView {

    @BindView(R.id.ivName) ImageView ivName;
    @BindView(R.id.etName) TextInputEditText etName;
    @BindView(R.id.ivPhone) ImageView ivPhone;
    @BindView(R.id.tvCodeArea) TextView tvCodeArea;
    @BindView(R.id.etPhone) TextInputEditText etPhone;
    @BindView(R.id.ivID) ImageView ivID;
    @BindView(R.id.etID) TextInputEditText etID;
    @BindView(R.id.ivAddress) ImageView ivAddress;
    @BindView(R.id.etAddress) TextInputEditText etAddress;
    @BindView(R.id.ivPassword) ImageView ivPassword;
    @BindView(R.id.etPassword) TextInputEditText etPassword;
    @BindView(R.id.btnRegister) Button btnRegister;
    @BindView(R.id.tvNavigationLogin) TextView tvNavigationLogin;
    @BindView(R.id.layAuthNavigation) RelativeLayout layAuthNavigation;

    public RegisterFragment() {

    }

    @Override public RegisterPresenter createPresenter() {
        return new RegisterPresenter(getContext());
    }

    @Override protected int getLayoutRes() {
        return R.layout.fragment_register;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String textFirst = "Sudah memiliki akun? ";
        String textEvent = "LOGIN";
        SpannableString spannableString = new SpannableString(textFirst + textEvent);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                mListener.gotoPage(new LoginFragment(), false, null);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), textFirst.length(), spannableString.length(), 0);
        spannableString.setSpan(clickableSpan, textFirst.length(), spannableString.length(), 0);
        tvNavigationLogin.setMovementMethod(LinkMovementMethod.getInstance());
        tvNavigationLogin.setText(spannableString);
    }

    @OnClick(R.id.btnRegister) public void onRegisterClicked() {
        if (TextUtils.isEmpty(etName.getText())){
            etName.requestFocus();
            etName.setError("Nama wajib diisi");
            return;
        }
        if (TextUtils.isEmpty(etPhone.getText())){
            etPhone.requestFocus();
            etPhone.setError("No Handphone wajib diisi");
            return;
        }

        if (etPhone.getText().length() < 10){
            etPhone.requestFocus();
            etPhone.setError("No Handphone tidak sesuai");
            return;
        }
        if (TextUtils.isEmpty(etAddress.getText())){
            etAddress.requestFocus();
            etAddress.setError("Alamat wajib diisi");
            return;
        }

        if (TextUtils.isEmpty(etPassword.getText())){
            etPassword.requestFocus();
            etPassword.setError("Password wajib diisi");
            return;
        }

        presenter.requestRegister(
                etName.getText().toString(),
                etPhone.getText().toString(),
                etAddress.getText().toString(),
                etID.getText().toString(),
                etPassword.getText().toString()
        );
    }

    @Override public void goToVerification(String phone) {
        mListener.gotoPage(new VerificationFragmentBuilder(phone).build(), false, null);
    }
}