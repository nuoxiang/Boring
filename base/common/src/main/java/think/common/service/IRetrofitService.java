package think.common.service;

import com.alibaba.android.arouter.facade.template.IProvider;

import okhttp3.OkHttpClient;

/**
 * @author think
 * @date 2018/5/14 下午3:09
 */

public interface IRetrofitService extends IProvider {

    /**
     * 返回对应的api
     *
     * @param service
     * @param okHttpClient
     * @param <T>
     * @return
     */
    <T> T create(final Class<T> service, OkHttpClient okHttpClient);

    /**
     * 返回对应的api
     *
     * @param service
     * @param okHttpClient
     * @param baseUrl
     * @param <T>
     * @return
     */
    <T> T create(final Class<T> service, OkHttpClient okHttpClient, String baseUrl);
}
