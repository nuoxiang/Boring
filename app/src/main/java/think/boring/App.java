package think.boring;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.github.mzule.activityrouter.annotation.Module;
import com.github.mzule.activityrouter.annotation.Modules;

import think.bmob.engine.BmobEngine;
import think.common.engine.EngineManger;
import think.common.engine.IBmobEngine;
import think.common.engine.IRongIMEngine;
import think.common.router.RouterList;
import think.rongim.engine.RongIMEngine;

/**
 * @author think
 * @date 2018/1/15 下午4:44
 */
@Module(RouterList.Module.MAIN)
@Modules({RouterList.Module.MAIN})
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        EngineManger.DEBUG = true;
        EngineManger.getInstance().setContext(this);

        registerEngine();
    }

    private void registerEngine() {
        EngineManger.getInstance().addEngine(IBmobEngine.class, new BmobEngine());
        EngineManger.getInstance().addEngine(IRongIMEngine.class, new RongIMEngine());

        EngineManger.getInstance().register();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
