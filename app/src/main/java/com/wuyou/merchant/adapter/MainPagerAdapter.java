package com.wuyou.merchant.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wuyou.merchant.view.fragment.BaseFragment;

import java.util.List;


/**
 * Created by admin on 2016/11/1.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {

    List<Fragment> mFragments;
    public MainPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

}
