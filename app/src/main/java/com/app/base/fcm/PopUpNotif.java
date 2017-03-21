package com.app.base.fcm;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.app.base.R;
import com.app.base.models.DataNotification;
import com.app.base.modules.main.MainActivity;
import com.app.base.utility.Config;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PopUpNotif extends Activity {

    @BindView(R.id.textMessage)
    WebView textMessage;
    @BindView(R.id.positiveButton)
    Button positiveButton;

    private NotificationManager mNotificationManager;
    private DataNotification dataNotification;
    private String jsonDataNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pop_up_notif);
        ButterKnife.bind(this);
        setFinishOnTouchOutside(false);

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (getIntent().getExtras() != null) {
            jsonDataNotification = getIntent().getExtras().getString(Config.JSON_DATA_NOTIFICATION);
            dataNotification = new Gson().fromJson(jsonDataNotification, new TypeToken<DataNotification>() {
            }.getType());

            initView();
        } else {
            positiveButton.setVisibility(View.GONE);
        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        WebSettings ws = textMessage.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setDefaultTextEncodingName("utf-8");

        textMessage.setWebViewClient(new WebViewClient());
        textMessage.loadData(dataNotification.getText(), "text/html; charset=utf-8", null);
    }

    private void launch_activity() {
        Intent i = new Intent();
        i.putExtra(Config.JSON_DATA_NOTIFICATION, jsonDataNotification);
        i.setAction(Intent.ACTION_MAIN);
        i.setClass(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    @OnClick({R.id.negativeButton, R.id.positiveButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.negativeButton:
                mNotificationManager.cancel(Config.NOTIFICATION_ID);
                finish();
                break;
            case R.id.positiveButton:
                mNotificationManager.cancel(Config.NOTIFICATION_ID);
                launch_activity();
                break;
        }
    }
}
