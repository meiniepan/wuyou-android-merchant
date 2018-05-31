package com.wuyou.merchant.mvp.order;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.wuyou.merchant.R;
import com.wuyou.merchant.view.fragment.BaseFragment;

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
        fragment1 = new OrderStatusFragment();
        fragment1.setOrderState(1);
        fragment2 = new OrderStatusFragment();
        fragment2.setOrderState(2);
        fragment3 = new OrderStatusFragment();
        fragment3.setOrderState(3);
        fragment4 = new OrderStatusFragment();
        fragment4.setOrderState(0);
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
                }else if (position == 2) {

                    fragment = fragment3;
                }else if (position == 3) {

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

//  设置监听,注意:如果设置了setOnTabSelectedListener,则setupWithViewPager不会生效
//  因为setupWithViewPager内部也是通过设置该监听来触发ViewPager的切换的.
//  mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//   @Override
//   public void onTabSelected(TabLayout.Tab tab) {
//   }
//
//   @Override
//   public void onTabUnselected(TabLayout.Tab tab) {
//
//   }
//
//   @Override
//   public void onTabReselected(TabLayout.Tab tab) {
//
//   }
//  });
//  那我们如果真的需要监听tab的点击或者ViewPager的切换,则需要手动配置ViewPager的切换,例如:
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //切换ViewPager
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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
