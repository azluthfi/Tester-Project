package com.app.basarnas.modules.dashboard;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.TextPaint;
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
import com.app.basarnas.modules.auth.LoginActivity;
import com.app.basarnas.modules.report.post.PostFragment;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import butterknife.BindView;
import butterknife.OnClick;

@FragmentWithArgs
public class DashboardFragment extends BaseMvpFragment<IDashboardView, DashboardPresenter>
        implements IDashboardView {

    @BindView(R.id.ivLogo) ImageView ivLogo;
    @BindView(R.id.tvName) TextView tvName;
    @BindView(R.id.tvEmergencyNumber) TextView tvEmergencyNumber;
    @BindView(R.id.layAreaNumber) RelativeLayout layAreaNumber;
    @BindView(R.id.ivCallArea) ImageView ivCallArea;
    @BindView(R.id.tvEmergencyNumberNational) TextView tvEmergencyNumberNational;
    @BindView(R.id.layNationalNumber) RelativeLayout layNationalNumber;
    @BindView(R.id.ivCallNational) ImageView ivCallNational;
    @BindView(R.id.btnReport) Button btnReport;
    @BindView(R.id.tvNotAuth) TextView tvNotAuth;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override public DashboardPresenter createPresenter() {
        return new DashboardPresenter(getContext());
    }

    @Override protected int getLayoutRes() {
        return R.layout.fragment_dashboard;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.checkIsAuth();
    }

    @Override public void isAuth() {
        tvNotAuth.setVisibility(View.GONE);
    }

    @Override public void isNotUth() {
        String textFirst = getString(R.string.text_not_auth) + " ";
        String textBtnLogin = "disini!";
        SpannableString spannableString = new SpannableString(textFirst + textBtnLogin);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                goToLogin(LoginActivity.EXTRA_TYPE_LOGIN);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), textFirst.length(), spannableString.length(), 0);
        spannableString.setSpan(clickableSpan, textFirst.length(), spannableString.length(), 0);
        tvNotAuth.setMovementMethod(LinkMovementMethod.getInstance());
        tvNotAuth.setText(spannableString);
        tvNotAuth.setVisibility(View.VISIBLE);
    }

    @Override public void goToLogin(String type) {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.putExtra(LoginActivity.EXTRA_TYPE, type);
        startActivity(intent);
    }

    @Override public void goToReport() {
        mListener.gotoPage(new PostFragment(), false, null);
    }

    @OnClick(R.id.btnReport) public void onReportClicked() {
        presenter.onEventReport();
    }
}
