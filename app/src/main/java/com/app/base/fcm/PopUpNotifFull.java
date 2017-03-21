package com.app.base.fcm;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.app.base.R;
import com.app.base.models.DataNotification;
import com.app.base.utility.Config;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopUpNotifFull extends Activity {

    @BindView(R.id.webView3)
    WebView webView3;
    @BindView(R.id.closeTxt)
    TextView closeTxt;

    private DataNotification dataNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pop_up_notif_full);
        ButterKnife.bind(this);
        setFinishOnTouchOutside(false);

        if (getIntent().getExtras() != null){
            String jsonDataNotification = getIntent().getExtras().getString(Config.JSON_DATA_NOTIFICATION);
            dataNotification = new Gson().fromJson(jsonDataNotification, new TypeToken<DataNotification>() {
            }.getType());

            initView();
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        //web setting
        WebSettings ws = webView3.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setDefaultTextEncodingName("utf-8");

        webView3.setWebViewClient(new WebViewClient());
        webView3.loadData(dataNotification.getText(),"text/html; charset=utf-8", null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView3.canGoBack()) {
            webView3.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }
}
