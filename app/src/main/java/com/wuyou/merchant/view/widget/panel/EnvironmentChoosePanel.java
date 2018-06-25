package com.wuyou.merchant.view.widget.panel;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.gs.buluo.common.network.TokenEvent;
import com.gs.buluo.common.utils.DensityUtils;
import com.gs.buluo.common.utils.SharePreferenceManager;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.view.widget.CustomNestRadioGroup;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * Created by hjn on 2017/1/3.
 */

public class EnvironmentChoosePanel extends Dialog {
    public EnvironmentChoosePanel(Context context) {
        super(context, R.style.bottom_dialog);
        initView();
    }

    private void initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.env_choose_board, null);
        setContentView(rootView);
        ButterKnife.bind(this);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = DensityUtils.dip2px(getContext(), 300);
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);

        CustomNestRadioGroup radioGroup = rootView.findViewById(R.id.env_group);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> setEnv(checkedId));

        findViewById(R.id.env_login).setOnClickListener(v -> dismiss());
    }


    public void setEnv(int env) {
        switch (env) {
            case R.id.env_dev:
                Constant.BASE_URL = "https://develop.api.iwantmei.com/merchants/v1/";
                break;
            case R.id.env_test:
                Constant.BASE_URL = "https://stage.api.iwantmei.com/merchants/v1/";
                break;
            case R.id.env_online:
                Constant.BASE_URL = "https://api.iwantmei.com/merchants/v1/";
                break;
        }
        SharePreferenceManager.getInstance(getContext()).setValue(Constant.SP_BASE_URL, Constant.BASE_URL);
    }
}
