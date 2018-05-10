package think.common.view.base;

import io.reactivex.subjects.BehaviorSubject;

/**
 * @author think
 * @date 2018/1/15 下午5:18
 */

public interface IPresenter<T extends IView> {
    /**
     * 绑定
     *
     * @param view
     * @param behaviorSubject
     */
    void attachView(T view, BehaviorSubject behaviorSubject);

    /**
     * 解绑
     */
    void detachView();
}
