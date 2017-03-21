package com.app.base.app;


import android.content.Context;

import com.app.base.apis.ApiService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.app.base.utility.Config.BASE_URL;

/**
 * Created by j3p0n on 12/30/2016.
 */

public class RestClient {
    private final Context mContext;
    private ApiService apiService;

    RestClient(Context applicationContext) {
        mContext = applicationContext;
        Retrofit.Builder retrofit = new Retrofit.Builder()
                .client(getRequestHeader())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());


        apiService = retrofit.baseUrl(BASE_URL).build().create(ApiService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }

    private OkHttpClient getRequestHeader() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(600, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();
        return httpClient;
    }
}
