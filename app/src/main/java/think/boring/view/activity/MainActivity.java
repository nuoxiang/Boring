package think.boring.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import com.github.mzule.activityrouter.annotation.Router;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import think.boring.R;
import think.boring.view.fragment.FindFragment;
import think.boring.view.fragment.HomeFragment;
import think.boring.view.fragment.MeFragment;
import think.common.router.RouterList;
import think.common.view.base.BaseActivity;
import think.common.view.base.BasePresenter;

/**
 * @author think
 */
@Router(RouterList.Main.MAIN)
public class MainActivity extends BaseActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    QMUITabSegment tabs;

    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected BasePresenter newPresenter() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTopBarTitle("....", false);

        initTabs();

        addFragments();
    }

    private void initTabs() {
        int normalColor = QMUIResHelper.getAttrColor(this, R.attr.qmui_config_color_gray_6);
        int selectColor = QMUIResHelper.getAttrColor(this, R.attr.qmui_config_color_blue);
        tabs.setDefaultNormalColor(normalColor);
        tabs.setDefaultSelectedColor(selectColor);

        QMUITabSegment.Tab homeTab = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.drawable.icon_tab_home),
                ContextCompat.getDrawable(getContext(), R.drawable.icon_tab_home_light),
                "home", true);
        QMUITabSegment.Tab findTab = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.drawable.icon_tab_find),
                ContextCompat.getDrawable(getContext(), R.drawable.icon_tab_find_light),
                "find", true);
        QMUITabSegment.Tab meTab = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.drawable.icon_tab_me),
                ContextCompat.getDrawable(getContext(), R.drawable.icon_tab_me_light),
                "me", true);

        tabs.addTab(homeTab).addTab(findTab).addTab(meTab);
    }

    private void addFragments() {
        Fragment homeFragment = new HomeFragment();
        Fragment findFragment = new FindFragment();
        Fragment meFragment = new MeFragment();

        fragmentList.add(homeFragment);
        fragmentList.add(findFragment);
        fragmentList.add(meFragment);

        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myFragmentPagerAdapter);
        tabs.setupWithViewPager(viewPager, false);
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
