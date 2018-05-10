package think.boring.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.think.common.lib.router.Routers;

import think.common.util.ARouterUtil;

/**
 * @author think
 * @date 2018/1/15 下午5:35
 */

public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouterUtil.navigation(this, Routers.App.MAIN);
        finish();
    }
}
