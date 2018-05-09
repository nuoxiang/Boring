package think.boring;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.github.mzule.activityrouter.annotation.Module;
import com.github.mzule.activityrouter.annotation.Modules;

import think.common.router.RouterList;

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
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
