package com.wuyou.merchant.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import com.gs.buluo.common.utils.TribeDateUtils;
import com.gs.buluo.common.widget.recyclerHelper.BaseHolder;
import com.gs.buluo.common.widget.recyclerHelper.BaseQuickAdapter;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.TradeEntity;
import com.wuyou.merchant.bean.entity.TradeItemEntity;
import com.wuyou.merchant.util.CommonUtil;

import java.util.Date;

/**
 * Created by solang on 2018/2/5.
 */

public class TradeListRvAdapter extends BaseQuickAdapter<TradeItemEntity, BaseHolder> {

    private int type = 0; //订单 1  合约 2

    public TradeListRvAdapter(int layoutResId) {
        super(layoutResId);
    }

    public TradeListRvAdapter(int type, int res) {
        super(res);
        this.type = type;
    }

    @Override
    protected void convert(BaseHolder helper, TradeItemEntity item) {
        String time = TribeDateUtils.SDF7.format(new Date(item.pay_time * 1000));
        helper.setText(R.id.item_wallet_record_number, item.order_number)
                .setText(R.id.item_wallet_record_time, time);

        TextView textView = helper.getView(R.id.textView2);
        if (!TextUtils.isEmpty(item.buyer)) {
            textView.setText("付款方:");
            helper.setText(R.id.item_wallet_record_id, item.buyer);
        } else if (item.type == 0) {//签约者
            textView.setText("收款方:");
            helper.setText(R.id.item_wallet_record_id, item.shop_name);
        } else {
            textView.setText("付款方:");
            helper.setText(R.id.item_wallet_record_id, item.merchant_name);
        }

        if (type == 1) {
            helper.setText(R.id.textView3, "订单总金额:");
            float total = 0;
            for (TradeEntity entity : item.transactions) {
                total += entity.amount;
            }
            helper.setText(R.id.item_wallet_record_account, CommonUtil.formatPrice(total));
        } else {
            helper.setText(R.id.textView3, "合约总金额:").setText(R.id.item_wallet_record_account, CommonUtil.formatPrice(item.total_amount));
        }


    }
}
