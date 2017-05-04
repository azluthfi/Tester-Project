package com.app.basarnas.modules.auth.register;

import com.hannesdorfmann.mosby3.mvp.MvpView;

public interface XRegisterView extends MvpView {

    public void goToVerification(String phone);
}