package com.app.base.base;

import android.content.Context;

import com.app.base.utility.Preferences;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by j3p0n on 3/22/2017.
 */

public class BasePresenter<V extends MvpView> extends MvpBasePresenter<V> {
    protected Context context;
    protected Preferences prefs;

    public BasePresenter(Context context) {
        if (prefs == null) {
            prefs = new Preferences(context);
        }
        this.context = context;
    }

    public Preferences getPrefs() {
        return prefs;
    }


}
