package com.app.basarnas.modules.auth.register;

import android.content.Context;

import com.app.basarnas.base.BasePresenter;

public class RegisterPresenter extends BasePresenter<XRegisterView>
        implements XRegisterPresenter {

    public RegisterPresenter(Context context) {
        super(context);
    }

    @Override public void requestRegister(String name, String phone, String address, String id, String password) {
        getView().goToVerification(phone);
    }
}