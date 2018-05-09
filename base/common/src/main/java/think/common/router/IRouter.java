package think.common.router;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import think.router.lib.IPath;

/**
 * @author think
 * @date 2018/1/15 上午11:46
 * <p>
 * 用法：
 * 第一个参数类型 可为 {@link Context} {@link Activity} {@link Fragment}
 * 变量名必须为 context
 * <p>
 * 需要requestCode 时必须有个参数为 int requestCode 第一个参数必须为
 * {@link Activity} {@link Fragment}
 * <p>
 * example : void aaa(Context context,String aaa);
 * <p>
 * void aaa(Activity context,String aaa,int requestCode);
 * <p>
 * void aaa(Fragment context,String aaa,int requestCode);
 */

public interface IRouter {

    /**
     * 首页
     *
     * @param context
     */
    @IPath(RouterList.Main.MAIN)
    void toMain(Context context);
}
