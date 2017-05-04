package com.app.basarnas.modules.auth.register;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

public interface XRegisterPresenter extends MvpPresenter<XRegisterView> {

    void requestRegister(String name, String phone, String address, String id, String password);
}