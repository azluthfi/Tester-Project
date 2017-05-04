package com.app.basarnas.modules.splash;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.basarnas.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {
    public static final int STARTUP_DELAY = 300;
    public static final int ANIM_ITEM_DURATION = 1000;
    public static final int ITEM_DELAY = 300;

    //    @BindView(R.id.imageView2) ImageView imageView2;
//    @BindView(R.id.textViewEmergencyCall) TextView textViewEmergencyCall;
//    @BindView(R.id.floatingActionButton) FloatingActionButton floatingActionButton;
//    @BindView(R.id.buttonSkip) TextView buttonSkip;
//    @BindView(R.id.constraintLayout) ConstraintLayout constraintLayout;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.backdrop) ImageView backdrop;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.appbar) AppBarLayout appbar;

    private RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        rxPermissions = new RxPermissions(this);
        animate();

        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbar.setTitle(getString(R.string.emergency_number));

        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //  Vertical offset == 0 indicates appBar is fully expanded.
                if (Math.abs(verticalOffset) > 200) {
//                    appBarExpanded = false;
                    invalidateOptionsMenu();
                } else {
//                    appBarExpanded = true;
                    invalidateOptionsMenu();
                }
            }
        });

//        CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
//        p.setAnchorId(View.NO_ID);
//        fab.setLayoutParams(p);
//        fab.setVisibility(View.GONE);
    }

    private void animate() {
//        final Resources resources = constraintLayout.getResources();
//        final int translateInPixels = resources.getDimensionPixelSize(com.hannesdorfmann.mosby3.mvp.lce.R.dimen.lce_content_view_animation_translate_y);
//        // Not visible yet, so animate the view in
//        AnimatorSet set = new AnimatorSet();
//        ObjectAnimator contentFadeIn = ObjectAnimator.ofFloat(constraintLayout, View.ALPHA, 0f, 1f);
//        ObjectAnimator contentTranslateIn = ObjectAnimator.ofFloat(constraintLayout, View.TRANSLATION_Y,
//                translateInPixels, 0);
//
//        set.playTogether(contentFadeIn, contentTranslateIn);
//        set.setDuration(resources.getInteger(com.hannesdorfmann.mosby3.mvp.lce.R.integer.lce_content_view_show_animation_time));
//
//        set.addListener(new AnimatorListenerAdapter() {
//
//            @Override public void onAnimationStart(Animator animation) {
//                constraintLayout.setTranslationY(0);
//                constraintLayout.setVisibility(View.VISIBLE);
//            }
//
//            @Override public void onAnimationEnd(Animator animation) {
//                constraintLayout.setTranslationY(0);
//            }
//        });
//
//        set.start();
    }

//    @OnClick(R.id.buttonSkip) public void onSkip() {
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        startActivity(intent);
//        finish();
//    }

//    @OnClick(R.id.floatingActionButton) public void onCall() {
//        Intent callIntent = new Intent(Intent.ACTION_CALL);
//        callIntent.setData(Uri.parse("tel:" + getString(R.string.emergency_number_call)));
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            rxPermissions
//                    .request(Manifest.permission.CALL_PHONE)
//                    .subscribe(new Observer<Boolean>() {
//                        @Override public void onSubscribe(Disposable d) {
//                        }
//
//                        @Override public void onNext(Boolean aBoolean) {
//                            if (aBoolean) {
//                                SplashActivity.this.onCall();
//                            } else {
//                                SplashActivity.this.onCallPermissionsDenied();
//                            }
//
//                        }
//
//                        @Override public void onError(Throwable e) {
//                        }
//
//                        @Override public void onComplete() {
//                        }
//                    });
//            return;
//        }
//        startActivity(callIntent);
//    }

    private void onCallPermissionsDenied() {
        Toast.makeText(getApplicationContext(), "Permission denied, can't enable request call", Toast.LENGTH_SHORT).show();
    }
}
