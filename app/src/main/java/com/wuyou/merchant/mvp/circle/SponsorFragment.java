package com.wuyou.merchant.mvp.circle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gs.buluo.common.widget.StatusLayout;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.DispatchMerchantListRvAdapter;
import com.wuyou.merchant.adapter.WorkersRvAdapter;
import com.wuyou.merchant.bean.entity.PartnerListEntity;
import com.wuyou.merchant.bean.entity.WorkerEntity;
import com.wuyou.merchant.bean.entity.WorkerListEntity;
import com.wuyou.merchant.view.activity.ServiceProviderDetailActivity;
import com.wuyou.merchant.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by solang on 2018/1/31.
 */

public class SponsorFragment extends BaseFragment<CircleContract.View, CircleContract.Presenter> implements CircleContract.View {
    @BindView(R.id.sl_list_layout)
    StatusLayout statusLayout;
    @BindView(R.id.rv_orders)
    RecyclerView recyclerView;
    @BindView(R.id.fl_add)
    View add;
    List<WorkerEntity> data = new ArrayList();
    DispatchMerchantListRvAdapter adapter;

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_sponsor;
    }

    @Override
    protected CircleContract.Presenter getPresenter() {
        return new CirclePresenter();
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        adapter = new DispatchMerchantListRvAdapter(getActivity(), R.layout.item_chose_merchant, data);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            Intent intent = new Intent(getActivity(), ServiceProviderDetailActivity.class);
            intent.putExtra(Constant.MERCHANT_ID, adapter.getItem(position).id);
            startActivity(intent);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void loadData() {
        statusLayout.showProgressView();
        fetchDatas();
    }

    private void fetchDatas() {
        mPresenter.getSponsorAlliance();
    }

    @Override
    public void showError(String message, int res) {
        statusLayout.showErrorView(message);
    }

    @Override
    public void getSuccess(WorkerListEntity data) {
        adapter.setNewData(data.list);
        statusLayout.showContentView();

        if (adapter.getData().size() == 0) {
            statusLayout.showEmptyView("没有订单");
        }
    }

    @Override
    public void getPartnerSuccess(PartnerListEntity data) {

    }

    @Override
    public void getMore(WorkerListEntity data) {

    }

    @Override
    public void loadMoreError(int code) {

    }


    @OnClick(R.id.fl_add)
    public void onViewClicked() {
    }


    @OnClick({R.id.fl_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_add:
                Intent intent  = new Intent(getActivity(),AddAllianceMerchantActivity.class);
                getActivity().startActivity(intent);
                break;

        }
    }
}
