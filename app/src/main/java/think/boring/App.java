package think.boring;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;

import think.common.constant.AppInit;

/**
 * @author think
 * @date 2018/1/15 下午4:44
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppInit.DEBUG = true;
        AppInit.CONTEXT = this;

        if (AppInit.DEBUG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }

        ARouter.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
