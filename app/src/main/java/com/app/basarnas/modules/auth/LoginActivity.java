package com.app.basarnas.modules.auth;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.basarnas.R;
import com.app.basarnas.listeners.FragmentInteractionListener;
import com.app.basarnas.models.EventLogin;
import com.app.basarnas.modules.auth.login.LoginFragment;
import com.app.basarnas.utility.FragmentStack;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity
        implements FragmentInteractionListener {
    public static final String EXTRA_TYPE = "extra_type";
    public static final String EXTRA_TYPE_REPORT = "report";
    public static final String EXTRA_TYPE_LOGIN = "login";
    @BindView(R.id.backdrop) ImageView backdrop;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar) AppBarLayout appbar;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.contentView) FrameLayout contentView;

    private String type = EXTRA_TYPE_LOGIN;
    private FragmentStack fragmentStack;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        fragmentStack = new FragmentStack(this, getSupportFragmentManager(), R.id.contentView);

        if (getIntent().getExtras() != null) {
            type = getIntent().getExtras().getString(EXTRA_TYPE);
        }

        setupToolbar();
        fragmentStack.push(new LoginFragment(), null);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        collapsingToolbar.setTitle(getString(R.string.emergency_number));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) > 200) {
                    invalidateOptionsMenu();
                } else {
                    invalidateOptionsMenu();
                }
            }
        });
    }

    @Override public void onBackPressed() {
//        super.onBackPressed();
        if (fragmentStack.size() > 1) {
            fragmentStack.back();
            toggleShowUpNavigation();
        } else {
            if (!fragmentStack.back()) {
                finish();
            }

        }
    }

    @Override public void gotoPage(Fragment page, boolean shouldReplace, String tagName) {
        int countBefore = fragmentStack.size();
        if (shouldReplace) {
            fragmentStack.push(page, tagName);
        } else {
            fragmentStack.pushAdd(page, tagName);
        }

        //ubah back icon ke burger icon pada NavDraw
        if (page instanceof LoginFragment) {
            if (countBefore > 1) {
                toggleShowUpNavigation();
            }
        } else {
            toggleShowUpNavigation();
        }
    }

    private void toggleShowUpNavigation() {
//        if (actionBar != null) {
//            if (fragmentStack.size() > 1) {
//                actionBar.setHomeButtonEnabled(true);
//                actionBar.setDisplayHomeAsUpEnabled(true);
//                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                    @Override public void onClick(View v) {
//                        back();
//                    }
//                });
//            } else {
//                actionBar.setDisplayHomeAsUpEnabled(false);
//            }
//        }
    }

    @Override public void back() {
        fragmentStack.back();
    }

    @Override public void successLogin() {
        Toast.makeText(this, "Anda berhasil masuk.", Toast.LENGTH_SHORT).show();
        EventLogin eventLogin = new EventLogin(type);
        EventBus.getDefault().post(eventLogin);
        finish();
    }
}
