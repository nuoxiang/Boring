package think.common.view.base;

/**
 * @author think
 * @date 2018/1/15 下午5:18
 */

public interface BasePresenter<T extends BaseView> {
    /**
     * 绑定
     *
     * @param view
     */
    void attachView(T view);

    /**
     * 解绑
     */
    void detachView();
}
