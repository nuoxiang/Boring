package think.common.http;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import think.common.service.IOkHttpService;
import think.common.service.IRetrofitService;

/**
 * @author think
 * @date 2018/5/15 上午10:28
 */

public class RetrofitHelper {
    private static HashMap<String, Object> clsMap = new HashMap<>();

    public synchronized static <T> T getService(Class<T> cls, Interceptor... interceptors) {
        String clsName = cls.getName();
        T t = (T) clsMap.get(clsName);

        if (t == null) {
            OkHttpClient client = ARouter.getInstance().navigation(IOkHttpService.class)
                    .getOkHttpClient(HttpLoggingInterceptor.Level.BODY, interceptors);

            t = ARouter.getInstance().navigation(IRetrofitService.class)
                    .create(cls, client);
            clsMap.put(clsName, t);
        }
        return t;
    }
}
