package think.common.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.github.mzule.activityrouter.router.Routers;

/**
 * @author think
 * @date 2018/1/13 下午2:08
 */

public class RouterUtil {
    private static final String HOST = "bor://";
    private static IRouter iRouter = new IRouterImp();

    public static IRouter getRouter() {
        return iRouter;
    }

    /**
     * 普通路由
     *
     * @param context
     * @param routerUrl
     */
    public static void router(Context context, String routerUrl) {
        Routers.open(context, HOST + routerUrl);
    }

    /**
     * 带请求值路由
     *
     * @param activity
     * @param routerUrl
     * @param requestCode
     */
    public static void routerRequest(Activity activity, String routerUrl, int requestCode) {
        Routers.openForResult(activity, HOST + routerUrl, requestCode);
    }

    /**
     * 通过路由地址拿intent
     *
     * @param context
     * @param routerUrl
     * @return
     */
    public static Intent getIntent(Context context, String routerUrl) {
        return Routers.resolve(context, HOST + routerUrl);
    }
}
