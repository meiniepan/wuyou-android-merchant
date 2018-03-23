package com.wuyou.merchant.mvp.circle;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.gs.buluo.common.utils.ToastUtils;
import com.gs.buluo.common.utils.TribeDateUtils;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.ContractEntity;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.CircleApis;
import com.wuyou.merchant.view.activity.BaseActivity;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by solang on 2018/3/20.
 */

public class MarketContractDetailActivity extends BaseActivity {

    String id;
    @BindView(R.id.tv_contract_name)
    TextView tvContractName;
    @BindView(R.id.tv_contract_code)
    TextView tvContractCode;
    @BindView(R.id.tv_contract_category)
    TextView tvContractCategory;
    @BindView(R.id.tv_contract_create_time)
    TextView tvContractCreateTime;
    @BindView(R.id.tv_contract_end_time)
    TextView tvContractEndTime;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_company_address)
    TextView tvCompanyAddress;
    @BindView(R.id.tv_company_phone)
    TextView tvCompanyPhone;
    @BindView(R.id.tv_server_sum)
    TextView tvServerSum;
    @BindView(R.id.tv_server_scale)
    TextView tvServerScale;
    private String ownerId;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_market_contract_detail;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        id = getIntent().getStringExtra(Constant.CONTRACT_ID);
        initData(id);
    }

    private void initData(String id) {
        showLoadingDialog();
        CarefreeRetrofit.getInstance().createApi(CircleApis.class)
                .getContractDetail(id, QueryMapBuilder.getIns().put("shop_id", CarefreeApplication.getInstance().getUserInfo().getUid()).buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<ContractEntity>>() {
                    @Override
                    public void onSuccess(BaseResponse<ContractEntity> response) {
                        ownerId = response.data.shop.shop_id;
                        initUI(response.data);
                    }

                });
    }

    private void initUI(ContractEntity data) {
        String b_time = TribeDateUtils.dateFormat(new Date(Long.parseLong(data.created_at) * 1000));
        String e_time = TribeDateUtils.dateFormat(new Date(Long.parseLong(data.end_at) * 1000));
        tvContractName.setText(data.contract_name);
        tvContractCode.setText(data.contract_number);
        tvContractCategory.setText(data.service.service_name);
        tvContractCreateTime.setText(b_time);
        tvContractEndTime.setText(e_time);

        tvCompanyName.setText(data.shop.shop_name);
        tvCompanyAddress.setText(data.shop.contact_address);
        tvCompanyPhone.setText(data.shop.mobile);

        tvServerSum.setText(data.total_amount);
        tvServerScale.setText(data.divided_amount);
    }


    @OnClick({ R.id.btn_join})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btn_join:
                CarefreeRetrofit.getInstance().createApi(CircleApis.class)
                        .joinContract(
                                QueryMapBuilder.getIns().put("owner_id", ownerId)
                                        .put("member_id", CarefreeApplication.getInstance().getUserInfo().getUid())
                                        .put("contract_id", id)
                                        .buildPost())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<BaseResponse>() {
                            @Override
                            public void onSuccess(BaseResponse response) {
                                ToastUtils.ToastMessage(getCtx(), "加入成功");
                            }

                        });
                break;
        }
    }
}
