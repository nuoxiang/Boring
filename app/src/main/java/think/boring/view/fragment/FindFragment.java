package think.boring.view.fragment;

import think.boring.R;
import think.common.view.base.BaseFragment;
import think.common.view.base.BasePresenter;

/**
 * @author think
 * @date 2018/1/17 下午5:24
 */

public class FindFragment extends BaseFragment {

    @Override
    protected BasePresenter newPresenter() {
        return null;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected boolean canDragBack() {
        return false;
    }
}
