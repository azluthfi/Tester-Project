package com.app.basarnas.modules.auth.login;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

public interface XLoginPresenter extends MvpPresenter<XLoginView> {

    void requestLogin(String phone, String password);
}