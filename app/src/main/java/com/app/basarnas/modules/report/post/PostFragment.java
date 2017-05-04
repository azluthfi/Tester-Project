package com.app.basarnas.modules.report.post;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.basarnas.R;
import com.app.basarnas.base.BaseMvpFragment;
import com.app.basarnas.modules.report.list.ListFragment;
import com.app.basarnas.utility.CommonUtilities;

import butterknife.BindView;

public class PostFragment extends BaseMvpFragment<XPostView, PostPresenter>
        implements XPostView {

    @BindView(R.id.etLocation) TextInputEditText etLocation;
    @BindView(R.id.ivLocation) ImageView ivLocation;
    @BindView(R.id.ivGallery) ImageView ivGallery;
    @BindView(R.id.etReport) EditText etReport;
    @BindView(R.id.layReport) LinearLayout layReport;

    public PostFragment() {

    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override public PostPresenter createPresenter() {
        return new PostPresenter(getContext());
    }

    @Override protected int getLayoutRes() {
        return R.layout.fragment_post;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etReport.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etReport, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_send).setVisible(true);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_send:
                mListener.gotoPage(new ListFragment(), false, null);
                CommonUtilities.hideSoftKeyboard(getActivity());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}