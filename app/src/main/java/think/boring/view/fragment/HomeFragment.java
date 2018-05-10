package think.boring.view.fragment;

import android.widget.Button;

import butterknife.BindView;
import think.boring.R;
import think.common.view.base.BaseFragment;
import think.common.view.base.IPresenter;

/**
 * @author think
 * @date 2018/1/17 下午5:24
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.btn)
    Button btn;

    @Override
    protected IPresenter newPresenter() {
        return null;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {

    }
}
