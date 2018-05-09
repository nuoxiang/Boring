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
import android.view.LayoutInflater;
import android.widget.FrameLayout;

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

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView, LifecycleProvider<ActivityEvent> {
    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    private T presenter;
    private Unbinder mUnBinder;
    private QMUITipDialog loadingDialog;
    private QMUITopBar mTopBar;
    private FrameLayout mContent;

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
            setContentView(R.layout.include_base_layout);
            mTopBar = findViewById(R.id.topbar);
            mContent = findViewById(R.id.fl_content);
            mContent.addView(LayoutInflater.from(this).inflate(getLayout(), null));
            mUnBinder = ButterKnife.bind(this, mContent);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.app_color_blue));
        }

        this.presenter = newPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }

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

    /**
     * 返回topbar
     *
     * @return
     */
    public QMUITopBar getTopBar() {
        return mTopBar;
    }

    public void setTopBarTitle(String title, boolean isLeftBack) {
        getTopBar().setTitle(title);
        if (isLeftBack) {
            getTopBar().addLeftBackImageButton().setOnClickListener(v -> {
                back();
            });
        }
    }

    public void setTopBarTitle(String title) {
        setTopBarTitle(title, true);
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
}
