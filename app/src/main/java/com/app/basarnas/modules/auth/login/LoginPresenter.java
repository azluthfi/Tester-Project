package com.app.basarnas.modules.auth.login;

import android.content.Context;

import com.app.basarnas.base.BasePresenter;
import com.app.basarnas.utility.Config;

public class LoginPresenter extends BasePresenter<XLoginView>
        implements XLoginPresenter {

    public LoginPresenter(Context context) {
        super(context);
    }

    @Override public void requestLogin(String phone, String password) {
        prefs.savePreferences(Config.CUSTOMER_PHONE, phone);
        prefs.savePreferences(Config.CUSTOMER_ADDRESS, "Jl. Kendali Sada No 123, Pringgolayan, CondongCatur, Sleman, Yogyakarta");
        prefs.savePreferences(Config.CUSTOMER_NAME, "Luthfi Aziz");
        prefs.savePreferences(Config.CUSTOMER_IDENTITY, "123456789");
        if (isViewAttached()){
            getView().gotoMain();
        }
    }
}