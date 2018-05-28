package think.common.service;

import com.alibaba.android.arouter.facade.template.IProvider;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author think
 * @date 2018/5/14 下午3:10
 */

public interface IOkHttpService extends IProvider {

    /**
     * 统一Client
     * 没有添加log打印和interceptors
     *
     * @return
     */
    OkHttpClient getBaseOkHttpClient();

    /**
     * 带log
     *
     * @param level
     * @param interceptors
     * @return
     */
    OkHttpClient getOkHttpClient(HttpLoggingInterceptor.Level level, Interceptor... interceptors);

    /**
     * 可设置响应时间
     *
     * @param readTimeOut
     * @param writeTimeOut
     * @param connectTimeOut
     * @param level
     * @param interceptors
     * @return
     */
    OkHttpClient getOkHttpClient(final int readTimeOut, final int writeTimeOut, final int connectTimeOut, HttpLoggingInterceptor.Level level, Interceptor... interceptors);
}
