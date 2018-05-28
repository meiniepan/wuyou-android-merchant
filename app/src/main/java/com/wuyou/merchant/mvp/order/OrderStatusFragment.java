package com.wuyou.merchant.mvp.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.gs.buluo.common.widget.StatusLayout;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.OrderStatusRvAdapter;
import com.wuyou.merchant.bean.entity.OrderBeanDetail;
import com.wuyou.merchant.bean.entity.OrderInfoEntity;
import com.wuyou.merchant.bean.entity.OrderInfoListEntity;
import com.wuyou.merchant.util.MyRecyclerViewScrollListener;
import com.wuyou.merchant.view.fragment.BaseFragment;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseQuickAdapter;
import com.wuyou.merchant.view.widget.recyclerHelper.NewRefreshRecyclerView;
import com.wuyou.merchant.view.widget.recyclerHelper.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by solang on 2018/1/31.
 */

public class OrderStatusFragment extends BaseFragment<OrderContract.View, OrderContract.Presenter> implements OrderContract.View {
    @BindView(R.id.rv_orders)
    NewRefreshRecyclerView recyclerView;
    @BindView(R.id.sl_list_layout)
    StatusLayout statusLayout;
    @BindView(R.id.rl_to_top)
    View toTop;
    OrderStatusRvAdapter adapter;
    List<OrderInfoEntity> data = new ArrayList();
    private String orderState;

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_order_before;
    }

    @Override
    protected OrderContract.Presenter getPresenter() {
        return new OrderPresenter();
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        statusLayout.setErrorAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusLayout.showProgressView();
                adapter.clearData();
                fetchDatas();
            }
        });
        adapter = new OrderStatusRvAdapter(getActivity(), R.layout.item_order_before, data);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
            intent.putExtra(Constant.ORDER_ID,adapter.getItem(position).order_id);
//            intent.putExtra(Constant.DIVIDE_ORDER_FROM,1);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
        final MyRecyclerViewScrollListener scrollListener = new MyRecyclerViewScrollListener(getActivity(), toTop);
//        recyclerView.getRecyclerView().addOnScrollListener(scrollListener);
        recyclerView.getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.loadMore(CarefreeApplication.getInstance().getUserInfo().getUid(), "2");
            }
        }, recyclerView.getRecyclerView());
        recyclerView.setRefreshAction(new OnRefreshListener() {
            @Override
            public void onAction() {
                scrollListener.setRefresh();
                adapter.clearData();
                fetchDatas();
            }
        });
        toTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.getRecyclerView().smoothScrollToPosition(0);
            }
        });
    }

    @Override
    public void showError(String message, int res) {
        recyclerView.setRefreshFinished();
        statusLayout.showErrorView(message);
    }

    @Override
    public void getSuccess(OrderInfoListEntity data) {
        recyclerView.setRefreshFinished();
        adapter.setNewData(data.list);
        statusLayout.showContentView();
        if (data.has_more.equals("0")) {
            adapter.loadMoreEnd(true);
        }
        if (adapter.getData().size() == 0) {
            statusLayout.showEmptyView(getString(R.string.order_empty));
        }
    }

    @Override
    public void getMore(OrderInfoListEntity data) {
        adapter.addData(data.list);
        if (data.has_more.equals("0")) {
            adapter.loadMoreEnd(true);
        }
    }

    @Override
    public void loadMoreError(int code) {
        adapter.loadMoreFail();
    }

    @Override
    public void getOrderDetailSuccess(OrderBeanDetail bean) {

    }


    @Override
    public void loadData() {
        statusLayout.showProgressView();
        fetchDatas();
    }

    private void fetchDatas() {
        if (orderState.equals("4"))
            orderState = "0";
        mPresenter.getOrders(CarefreeApplication.getInstance().getUserInfo().getUid(), orderState);
    }
    public void setOrderState(int orderState) {
        this.orderState = orderState+"";
    }
}