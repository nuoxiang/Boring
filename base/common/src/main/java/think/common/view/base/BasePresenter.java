package think.common.view.base;

import io.reactivex.subjects.BehaviorSubject;

/**
 * @author think
 * @date 2018/5/10 下午2:22
 */

public class BasePresenter<T extends IView> implements IPresenter<T> {
    protected T mView;
    protected BehaviorSubject behaviorSubject;

    @Override
    public void attachView(T view, BehaviorSubject behaviorSubject) {
        this.mView = view;
        this.behaviorSubject = behaviorSubject;
    }

    @Override
    public void detachView() {
        this.mView = null;
        this.behaviorSubject = null;
    }

    protected boolean checkNull() {
        return mView != null && behaviorSubject != null;
    }
}
