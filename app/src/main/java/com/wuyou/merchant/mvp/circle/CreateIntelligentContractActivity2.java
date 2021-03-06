package com.wuyou.merchant.mvp.circle;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gs.buluo.common.network.ApiException;
import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.gs.buluo.common.utils.ToastUtils;
import com.gs.buluo.common.widget.LoadingDialog;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.ContractEntity;
import com.wuyou.merchant.bean.entity.ContractInfoEntity;
import com.wuyou.merchant.bean.entity.ContractPayEntity;
import com.wuyou.merchant.bean.entity.ServiceEntity;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.CircleApis;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.view.activity.BaseActivity;
import com.wuyou.merchant.view.activity.MainActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Solang on 2018/3/19.
 */

public class CreateIntelligentContractActivity2 extends BaseActivity {

    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.et_sum)
    EditText etSum;
    @BindView(R.id.btn_new_address)
    Button btnNewAddress;
    @BindView(R.id.ll_time_service)
    LinearLayout llTimeService;
    @BindView(R.id.ll_deduct_scale)
    LinearLayout llDeductScale;
    @BindView(R.id.tv_address_input_1)
    EditText etInput1;
    @BindView(R.id.tv_address_input_2)
    EditText etInput2;
    @BindView(R.id.tv_address_input_3)
    EditText etInput3;
    @BindView(R.id.et_time)
    EditText etTime;
    @BindView(R.id.et_time2)
    EditText etTime2;
    @BindView(R.id.create_contract_project_name)
    TextView tvServeName;
    @BindView(R.id.create_contract_pay_type)
    TextView tvPayType;
    ContractEntity entity;
    String imagePath2;
    private int n = 1;
    private String serviceIndex = "1";
    private ServiceEntity serviceEntity;
    private ContractPayEntity payEntity;

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_create_intelligent_contract_2;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        entity = getIntent().getParcelableExtra(Constant.CONTRACT_ENTITY);
        imagePath2 = getIntent().getStringExtra(Constant.IMAGE1_URL_2);
    }

    @OnClick({R.id.tv_commit, R.id.ll_time_service, R.id.ll_deduct_scale, R.id.btn_new_address, R.id.create_contract_project_choose, R.id.create_contract_pay_type_choose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_commit:
                doCommit();
                break;
            case R.id.ll_time_service:
                break;
            case R.id.ll_deduct_scale:
                break;
            case R.id.create_contract_pay_type_choose:
                Intent intent = new Intent(getCtx(), ContractPayChooseActivity.class);
                startActivityForResult(intent, Constant.IntentRequestCode.CHOOSE_PAY_TYPE);
                break;
            case R.id.create_contract_project_choose:
                choseServiceCategory();
                break;
            case R.id.btn_new_address:
                if (n < 3) {
                    if (n == 1) {
                        etInput2.setVisibility(View.VISIBLE);

                    } else if (n == 2) {
                        etInput3.setVisibility(View.VISIBLE);
                    }
                    n = n + 1;
                }
                break;
        }
    }

    private void choseServiceCategory() {
        Intent intent = new Intent(getCtx(), ServeChooseActivity.class);
        intent.putExtra(Constant.SHOP_ID, entity.shop_id);
        startActivityForResult(intent, Constant.IntentRequestCode.CHOOSE_SERVICE_CATEGORY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.IntentRequestCode.CHOOSE_SERVICE_CATEGORY) {

                serviceEntity = data.getParcelableExtra(Constant.SERVE_BEAN);
                tvServeName.setText(serviceEntity.service_name);
                etSum.setText(CommonUtil.formatPrice(serviceEntity.price));
            } else if (requestCode == Constant.IntentRequestCode.CHOOSE_PAY_TYPE) {
                payEntity = data.getParcelableExtra(Constant.PAY_TYPE);
                tvPayType.setText(payEntity.type_name);
            }
        }
    }

    private void doCommit() {
        if (serviceEntity == null) {
            ToastUtils.ToastMessage(getCtx(), "请选择服务项目");
            return;
        }
        if (payEntity == null) {
            ToastUtils.ToastMessage(getCtx(), "请选择支付方式");
            return;
        }
        List addressList = new ArrayList();
        addressList.add(etInput1.getText().toString());
        if (n == 2) addressList.add(etInput2.getText().toString());
        if (n == 3) addressList.add(etInput3.getText().toString());
        ContractInfoEntity cEntity = new ContractInfoEntity();
        cEntity.communities = addressList;
        cEntity.time_limit = etTime.getText().toString();
        cEntity.penalized_proportion = etTime2.getText().toString();

        Gson gson = new Gson();
        String sInfomation = gson.toJson(cEntity);
        entity.information = sInfomation;
        if (TextUtils.isEmpty(etInput1.getText())
                || TextUtils.isEmpty(etTime.getText().toString())
                || TextUtils.isEmpty(etTime2.getText().toString())) {
            ToastUtils.ToastMessage(getCtx(), "请完善资料");
            return;
        }
        String newPath = Constant.AUTH_IMG_PATH_1;
        File file = new File(newPath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("license", file.getName(), requestFile);
        MultipartBody.Part body2 = null;
        if (imagePath2 !=null){
            File file2 = new File(Constant.AUTH_IMG_PATH_2);
            RequestBody requestFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
            body2 = MultipartBody.Part.createFormData("other_image", file2.getName(), requestFile2);
        }
        LoadingDialog.getInstance().show(getCtx(), "创建中...", false);
        CarefreeRetrofit.getInstance().createApi(CircleApis.class)
                .createContract(body,body2, QueryMapBuilder.getIns()
                        .put("contact_address", entity.contact_address)
                        .put("contract_name", entity.contract_name)
                        .put("end_at", entity.end_at + "")
                        .put("information", entity.information.toString())
                        .put("price", etSum.getText().toString().trim())
                        .put("mobile", entity.mobile)
                        .put("shop_id", entity.shop_id)
                        .put("shop_name", entity.shop_name)
                        .put("service_id", serviceEntity.service_id)
                        .put("pay_type", payEntity.type_id)
                        .buildPost())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        ToastUtils.ToastMessage(getCtx(), "合约创建成功！");
                        Intent intent = new Intent(getCtx(), MainActivity.class);
                        intent.putExtra(Constant.MAIN_ACTIVITY_FROM_WHERE, Constant.MAIN_ACTIVITY_FROM_CREATE_CONTRACT);
                        startActivity(intent);
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        ToastUtils.ToastMessage(getCtx(), "合约创建失败！");
                    }
                });
    }
}
