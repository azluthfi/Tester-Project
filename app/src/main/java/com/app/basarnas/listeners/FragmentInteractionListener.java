package com.app.basarnas.listeners;

import android.support.v4.app.Fragment;

/**
 * Created by j3p0n on 1/10/2017.
 */
public interface FragmentInteractionListener {
    void gotoPage(Fragment page, boolean shouldReplace, String tagName);
    void back();
    void successLogin();
}
