package think.common.view.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qmuiteam.qmui.widget.QMUITopBar;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import think.common.R;
import think.common.util.KeyBoardUtil;

/**
 * @author think
 * @date 2018/1/16 下午4:07
 */

public abstract class BaseFragment<T extends IPresenter> extends Fragment implements IView, LifecycleProvider<FragmentEvent> {
    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

    private T presenter;

    private View view;
    private boolean isFirst = true;

    private Unbinder mUnBinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getLayout(), container, false);
        }
        mUnBinder = ButterKnife.bind(this, view);
        initFirst();
        if (isFirst && getUserVisibleHint()) {
            initView();
            isFirst = false;
        }
        return view;
    }

    /**
     * 初始化view
     */
    protected void initFirst() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirst && view != null) {
            initView();
            isFirst = false;
        }
        if (!getIsFirst()) {
            if (isVisibleToUser) {
                onFragmentResume();
            } else {
                onFragmentPause();
            }
        }
    }

    /**
     * fragment可见时的onResume 包括在viewpager中的状态 页面首次绘制不调用
     */
    protected void onFragmentResume() {

    }

    /**
     * fragment不可见时的onPause 包括在viewpager中的状态 页面首次绘制不调用
     */
    protected void onFragmentPause() {

    }

    public boolean getIsFirst() {
        return isFirst;
    }

    @Override
    @NonNull
    @CheckResult
    public final Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject);
    }

    @Override
    @CallSuper
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE);

        this.presenter = newPresenter();
        if (presenter != null) {
            presenter.attachView(this, lifecycleSubject);
        }
    }

    /**
     * 创建Presenter
     *
     * @return
     */
    protected abstract T newPresenter();

    /**
     * 返回布局id
     *
     * @return
     */
    @LayoutRes
    protected abstract int getLayout();

    /**
     * 初始化
     */
    protected abstract void initView();

    /**
     * 得到 presenter
     *
     * @return
     */
    protected T getPresenter() {
        return presenter;
    }

    /**
     * 特殊页面需拦截返回处理重写
     */
    protected void back() {
        KeyBoardUtil.closeKeyboard(this);
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.slide_still, R.anim.slide_out_right);
    }

    @Override
    @CallSuper
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
    }

    @Override
    @CallSuper
    public void onStart() {
        super.onStart();
        lifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    @CallSuper
    public void onResume() {
        super.onResume();
        lifecycleSubject.onNext(FragmentEvent.RESUME);
        if (!getIsFirst() && getUserVisibleHint()) {
            onFragmentResume();
        }
    }

    @Override
    @CallSuper
    public void onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
        if (!getIsFirst() && getUserVisibleHint()) {
            onFragmentPause();
        }
    }

    @Override
    @CallSuper
    public void onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

    @Override
    @CallSuper
    public void onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        super.onDestroyView();
    }

    @Override
    @CallSuper
    public void onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
        mUnBinder.unbind();
    }

    @Override
    @CallSuper
    public void onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();
    }

    @Override
    public void showProgress() {
        Activity activity = getActivity();
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).showProgress();
        }
    }

    @Override
    public void hideProgress() {
        Activity activity = getActivity();
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).hideProgress();
        }
    }

    @Override
    public void showNotice(int type, String notice) {
        Activity activity = getActivity();
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).showNotice(type, notice);
        }
    }

    public void setTopBarTitle(QMUITopBar topbar, String title, boolean isLeftBack) {
        topbar.setTitle(title);
        if (isLeftBack) {
            topbar.addLeftBackImageButton().setOnClickListener(v -> {
                back();
            });
        }
    }

    public void setTopBarTitle(QMUITopBar topbar, String title) {
        topbar.setTitle(title);
    }
}
