package com.wuyou.merchant.bean.entity;

import java.util.List;

/**
 * Created by solang on 2018/2/8.
 */

public class ResponseListEntity<T> {
    public String has_more;
    public String members_number;
    public List<T> list;
}
