package com.think.common.service.impl;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.think.common.lib.router.Routers;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import think.common.constant.AppInit;
import think.common.service.IOkHttpService;

/**
 * @author think
 * @date 2018/5/14 下午3:20
 */
@Route(path = Routers.Service.OKHTTP)
public class OkHttpServiceImpl implements IOkHttpService {
    private OkHttpClient baseOkClient;

    @Override
    public OkHttpClient getBaseOkHttpClient() {
        return baseOkClient;
    }

    @Override
    public OkHttpClient getOkHttpClient(HttpLoggingInterceptor.Level level, Interceptor... interceptors) {
        return null;
    }

    @Override
    public OkHttpClient getOkHttpClient(int readTimeOut, int writeTimeOut, int connectTimeOut, HttpLoggingInterceptor.Level level, Interceptor... interceptors) {
        return null;
    }

    @Override
    public void init(Context context) {

    }

    private void setHttpLoggingInterceptor(OkHttpClient.Builder newBuilder, HttpLoggingInterceptor.Level level) {
        if (AppInit.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(level);
            newBuilder.addInterceptor(logging);
        }
    }
}
