package think.common.view.base;

import android.content.Context;

/**
 * @author think
 * @date 2018/1/15 下午5:14
 */

public interface BaseView {
    /**
     * 显示进度条
     */
    void showProgress();

    /**
     * 隐藏进度条
     */
    void hideProgress();

    /**
     * 显示错误信息
     *
     * @param type   错误类型
     * @param notice 显示的信息
     */
    void showNotice(int type, String notice);

    /**
     * 获得context
     *
     * @return
     */
    Context getContext();

    /**
     * 网络错误
     */
    int ERROR_NETWORK = 2111;
}
