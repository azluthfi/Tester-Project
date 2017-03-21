package com.app.base.app;

import android.app.Application;
import android.content.Context;


/**
 * Created by j3p0n on 12/3/2016.
 */
public class App extends Application {
    private static RestClient restClient;

    @Override
    public void onCreate() {
        super.onCreate();
        restClient = new RestClient(getApplicationContext());

    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }

    public static RestClient getRestClient() {
        return restClient;
    }
}
