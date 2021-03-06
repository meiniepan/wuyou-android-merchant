package com.wuyou.merchant.mvp.order;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.wuyou.merchant.R;
import com.wuyou.merchant.mvp.circle.CircleStatusFragment;
import com.wuyou.merchant.view.fragment.BaseFragment;
import com.wuyou.merchant.view.widget.panel.EnvironmentChoosePanel;

import butterknife.BindView;


/**
 * Created by Administrator on 2018\1\29 0029.
 */

public class MyOrderFragment extends BaseFragment {
    @BindView(R.id.tl_tab)
    TabLayout mTabLayout;
    @BindView(R.id.vp_pager)
    ViewPager mViewPager;
    String[] mTitle = {"待分单", "未开始", "进行中", "全部"};
    FragmentPagerAdapter fragmentPagerAdapter;
    private OrderStatusFragment fragment1;
    private OrderStatusFragment fragment2;
    private OrderStatusFragment fragment3;
    private OrderStatusFragment fragment4;


    @Override
    protected int getContentLayout() {
        return R.layout.fragment_order_my;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        //防止Activity被回收后Fragment状态不正确
        Bundle bundle1 = new Bundle();
        bundle1.putInt("h", 1);
        Bundle bundle2 = new Bundle();
        bundle2.putInt("h", 2);
        Bundle bundle3 = new Bundle();
        bundle3.putInt("h", 3);
        Bundle bundle4 = new Bundle();
        bundle4.putInt("h", 0);
        fragment1 = new OrderStatusFragment();
        fragment1.setArguments(bundle1);
        fragment2 = new OrderStatusFragment();
        fragment2.setArguments(bundle2);
        fragment3 = new OrderStatusFragment();
        fragment3.setArguments(bundle3);
        fragment4 = new OrderStatusFragment();
        fragment4.setArguments(bundle4);
        fragmentPagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            //此方法用来显示tab上的名字
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle[position % mTitle.length];
            }

            @Override
            public Fragment getItem(int position) {
                //创建Fragment并返回
                Fragment fragment = null;
                if (position == 0) {

                    fragment = fragment1;
                } else if (position == 1) {

                    fragment = fragment2;
                } else if (position == 2) {

                    fragment = fragment3;
                } else if (position == 3) {

                    fragment = fragment4;
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return mTitle.length;
            }
        };
        mViewPager.setAdapter(fragmentPagerAdapter);
        //将ViewPager关联到TabLayout上
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void showError(String message, int res) {

    }

    @Override
    public void loadData() {
        mViewPager.setCurrentItem(0);
    }

    public void loadDatas() {
//        ((OrderStatusFragment) fragmentPagerAdapter.getItem(0)).loadData();
        fragment1.loadData();
    }

    public void loadDataAll() {
        fragment4.loadData();
    }
}
