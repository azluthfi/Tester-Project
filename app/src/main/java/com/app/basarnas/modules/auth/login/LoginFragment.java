package com.app.basarnas.modules.auth.login;

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
import com.app.basarnas.modules.auth.register.RegisterFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginFragment extends BaseMvpFragment<XLoginView, LoginPresenter>
        implements XLoginView {

    @BindView(R.id.ivPhone) ImageView ivPhone;
    @BindView(R.id.etPhone) TextInputEditText etPhone;
    @BindView(R.id.ivPassword) ImageView ivPassword;
    @BindView(R.id.etPassword) TextInputEditText etPassword;
    @BindView(R.id.btnLogin) Button btnLogin;
    @BindView(R.id.tvNavigationRegister) TextView tvNavigationRegister;
    @BindView(R.id.layAuthNavigation) RelativeLayout layAuthNavigation;

    public LoginFragment() {

    }

    @Override public LoginPresenter createPresenter() {
        return new LoginPresenter(getContext());
    }

    @Override protected int getLayoutRes() {
        return R.layout.fragment_login;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etPhone.setText("087758402241");
        etPassword.setText("password");

        String textFirst = "Belum memiliki akun? ";
        String textEvent = "DAFTAR";
        SpannableString spannableString = new SpannableString(textFirst + textEvent);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                mListener.gotoPage(new RegisterFragment(), false, null);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), textFirst.length(), spannableString.length(), 0);
        spannableString.setSpan(clickableSpan, textFirst.length(), spannableString.length(), 0);
        tvNavigationRegister.setMovementMethod(LinkMovementMethod.getInstance());
        tvNavigationRegister.setText(spannableString);
    }

    @OnClick(R.id.btnLogin) public void onLoginClick() {

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

        if (TextUtils.isEmpty(etPassword.getText())){
            etPassword.requestFocus();
            etPassword.setError("Password wajib diisi");
            return;
        }
        presenter.requestLogin(etPhone.getText().toString(), etPassword.getText().toString());
    }

    @Override public void gotoMain() {
        mListener.successLogin();
    }
}