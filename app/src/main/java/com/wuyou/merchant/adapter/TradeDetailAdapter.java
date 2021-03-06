package com.wuyou.merchant.adapter;

import com.gs.buluo.common.utils.TribeDateUtils;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.TradeEntity;
import com.wuyou.merchant.util.CommonUtil;
import com.gs.buluo.common.widget.recyclerHelper.BaseHolder;
import com.gs.buluo.common.widget.recyclerHelper.BaseQuickAdapter;

import java.util.Date;

/**
 * Created by DELL on 2018/4/4.
 */

public class TradeDetailAdapter extends BaseQuickAdapter<TradeEntity, BaseHolder> {
    public TradeDetailAdapter(int res) {
        super(res);
    }

    private int type = 1;     //1 订单 2 合约

    public TradeDetailAdapter(int type, int res) {
        super(res);
        this.type = type;
    }

    @Override
    protected void convert(BaseHolder helper, TradeEntity item) {
        if (type == 1) {
            if (item.confirmations > 0) {
                helper.setText(R.id.tv_status, "已确认");
            } else {
                helper.setText(R.id.tv_status, "未确认");
            }
        } else {
            if (item.confirmations >= 6) {
                helper.setText(R.id.tv_status, "已确认");
            } else {
                helper.setText(R.id.tv_status, "未确认");
            }
        }
        helper.setText(R.id.tv_trade_hash, item.txid)
                .setText(R.id.tv_trade_time, TribeDateUtils.dateFormat(new Date(item.time * 1000)))
                .setText(R.id.tv_confirm_time, TribeDateUtils.dateFormat(new Date(item.timereceived * 1000)))
                .setText(R.id.tv_trade_sum, CommonUtil.formatPrice(item.amount))
                .setText(R.id.tv_trade_fee, item.fee+"")
                .setText(R.id.tv_confirm_node, item.confirmations + "")
                .setText(R.id.tv_trade_from, item.from)
                .setText(R.id.tv_trade_to, item.to);
    }
}
