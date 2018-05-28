package com.think.common.service.impl;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.think.common.lib.router.Routers;

import okhttp3.OkHttpClient;
import think.common.service.IRetrofitService;

/**
 * @author think
 * @date 2018/5/14 下午3:19
 */
@Route(path = Routers.Service.RETROFIT)
public class RetrofitServiceImpl implements IRetrofitService {
    @Override
    public <T> T create(Class<T> service, OkHttpClient okHttpClient) {
        return null;
    }

    @Override
    public <T> T create(Class<T> service, OkHttpClient okHttpClient, String baseUrl) {
        return null;
    }

    @Override
    public void init(Context context) {

    }
}
