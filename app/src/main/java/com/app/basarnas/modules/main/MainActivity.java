package com.app.basarnas.modules.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.basarnas.R;
import com.app.basarnas.listeners.FragmentInteractionListener;
import com.app.basarnas.models.EventLogin;
import com.app.basarnas.modules.auth.LoginActivity;
import com.app.basarnas.modules.dashboard.DashboardFragment;
import com.app.basarnas.modules.information.AboutFragment;
import com.app.basarnas.modules.report.list.ListFragment;
import com.app.basarnas.modules.report.post.PostFragment;
import com.app.basarnas.utility.Config;
import com.app.basarnas.utility.FragmentStack;
import com.app.basarnas.utility.Preferences;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentInteractionListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.contentView) FrameLayout contentView;
    @BindView(R.id.nav_view) NavigationView navView;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    View header;
    LinearLayout layIsAuth;
    LinearLayout layNotAuth;
    Button btnLogin;
    ImageView ivAvatar;
    TextView tvName;
    TextView tvPhone;

    private FragmentStack fragmentStack;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ActionBar actionBar;
    private Preferences prefs;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        fragmentStack = new FragmentStack(this, getSupportFragmentManager(), R.id.contentView);
        prefs = new Preferences(this);
        setupToolbar();
        setupDrawer();

        navView.setNavigationItemSelectedListener(this);

        header = navView.getHeaderView(0);
        if (header != null) {
            layIsAuth = (LinearLayout) header.findViewById(R.id.layIsAuth);
            layNotAuth = (LinearLayout) header.findViewById(R.id.layNotAuth);
            ivAvatar = (ImageView) header.findViewById(R.id.ivAvatar);
            tvName = (TextView) header.findViewById(R.id.tvName);
            tvPhone = (TextView) header.findViewById(R.id.tvPhone);
            btnLogin = (Button) header.findViewById(R.id.btnLogin);
        }
        checkIsAuth();
        fragmentStack.push(new DashboardFragment(), "dashboard");
    }

    private void checkIsAuth() {
        if (prefs.getPreferencesString(Config.CUSTOMER_PHONE) != null) {
            setIsAuth();
        } else {
            setNotAuth();
        }
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        toolbar.setTitle(getString(R.string.app_name));
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.app_name));
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
        }
    }

    private void setIsAuth() {
        if (header != null) {
            layIsAuth.setVisibility(View.VISIBLE);
            layNotAuth.setVisibility(View.GONE);
            tvName.setText(prefs.getPreferencesString(Config.CUSTOMER_NAME));
            tvPhone.setText(prefs.getPreferencesString(Config.CUSTOMER_PHONE));
            navView.getMenu().findItem(R.id.nav_exit).setVisible(true);
            navView.getMenu().findItem(R.id.nav_report).setVisible(true);
        }

    }

    private void setNotAuth() {
        if (header != null) {
            layIsAuth.setVisibility(View.GONE);
            layNotAuth.setVisibility(View.VISIBLE);
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.putExtra(LoginActivity.EXTRA_TYPE, LoginActivity.EXTRA_TYPE_LOGIN);
                    startActivity(intent);
                }
            });
            navView.getMenu().findItem(R.id.nav_exit).setVisible(false);
            navView.getMenu().findItem(R.id.nav_report).setVisible(false);
        }
    }

    private void setupDrawer() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (fragmentStack.size() > 1) {
                fragmentStack.back();
                toggleShowUpNavigation();
            } else {
                if (!fragmentStack.back()) {
                    finish();
                }

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_home:
                gotoPage(new DashboardFragment(), false, null);
                break;
            case R.id.nav_info:
                gotoPage(new AboutFragment(), false, null);
                break;
            case R.id.nav_report:
                gotoPage(new ListFragment(), false, null);
                break;
            case R.id.nav_exit:
                logOut();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logOut() {
        prefs.clearAllPreferences();
        checkIsAuth();
        EventLogin eventLogin = new EventLogin("logout");
        EventBus.getDefault().post(eventLogin);
    }

    @Override public void gotoPage(Fragment page, boolean shouldReplace, String tagName) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        int countBefore = fragmentStack.size();
        if (shouldReplace) {
            fragmentStack.push(page, tagName);
        } else {
            fragmentStack.pushAdd(page, tagName);
        }

        //ubah back icon ke burger icon pada NavDraw
        if (page instanceof DashboardFragment) {
            if (countBefore > 1) {
                toggleShowUpNavigation();
            }
        } else {
            toggleShowUpNavigation();
        }
    }

    @Override public void back() {
        fragmentStack.back();
    }

    @Override public void successLogin() {
        // just use on login activity
    }

    private void toggleShowUpNavigation() {
        actionBar = getSupportActionBar();

        if (fragmentStack.peek() instanceof DashboardFragment) {
            setupToolbar();
            setupDrawer();
        } else {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0) {
                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                    super.onDrawerSlide(drawerView, slideOffset);
                }
            };
            drawerLayout.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
            actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            drawerLayout.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();
        }
    }

    @Override protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventLogin(EventLogin eventLogin) {
        checkIsAuth();
        if (eventLogin.getType().equals(LoginActivity.EXTRA_TYPE_REPORT)) {
            Toast.makeText(this, "Report", Toast.LENGTH_SHORT).show();
            gotoPage(new PostFragment(), false, null);
        }
    }
}
