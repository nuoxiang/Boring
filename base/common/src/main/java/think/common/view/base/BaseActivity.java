package think.common.view.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import think.common.R;
import think.common.util.DialogUtil;
import think.common.util.KeyBoardUtil;

/**
 * @author think
 * @date 2018/1/15 下午5:22
 */

public abstract class BaseActivity<T extends IPresenter> extends AppCompatActivity implements IView, LifecycleProvider<ActivityEvent> {
    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    private T presenter;
    private Unbinder mUnBinder;
    private QMUITipDialog loadingDialog;

    @Override
    @CallSuper
    final protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(ActivityEvent.CREATE);

        if (isPortrait()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        onCreated(savedInstanceState);

        if (getLayout() != 0) {
            setContentView(getLayout());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.app_color_blue));
        }

        this.presenter = newPresenter();
        if (presenter != null) {
            presenter.attachView(this, lifecycleSubject);
        }

        mUnBinder = ButterKnife.bind(this);
        initView(savedInstanceState);
    }

    /**
     * 是否强制竖屏
     *
     * @return
     */
    protected boolean isPortrait() {
        return true;
    }

    /**
     * 在设置布局前须重写
     *
     * @param savedInstanceState
     */
    protected void onCreated(Bundle savedInstanceState) {

    }

    /**
     * 返回布局id
     *
     * @return
     */
    @LayoutRes
    protected abstract int getLayout();

    /**
     * 创建Presenter
     *
     * @return
     */
    protected abstract T newPresenter();

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

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
        finish();
        overridePendingTransition(R.anim.slide_still, R.anim.slide_out_right);
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

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    @CallSuper
    protected void onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
    }

    @Override
    @CallSuper
    protected void onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
        hideProgress();
        if (presenter != null) {
            presenter.detachView();
        }
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
    }

    @Override
    public void onBackPressed() {
        back();
    }

    @Override
    public void showProgress() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            return;
        }
        loadingDialog = DialogUtil.newLoadingDialog(this);
        loadingDialog.setCancelable(false);
        loadingDialog.show();
    }

    @Override
    public void hideProgress() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showNotice(int type, String notice) {
        switch (type) {
            case ERROR_NETWORK:
                DialogUtil.showText(this, QMUITipDialog.Builder.ICON_TYPE_FAIL, "网络错误");
                break;
            default:
                DialogUtil.showText(this, QMUITipDialog.Builder.ICON_TYPE_FAIL, notice);
                break;
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    @NonNull
    @CheckResult
    public Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null && manager != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }
}
