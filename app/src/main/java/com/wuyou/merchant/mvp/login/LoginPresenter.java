package com.wuyou.merchant.mvp.login;

import com.gs.buluo.common.network.ApiException;
import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.bean.UserInfo;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.UserApis;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018\1\29 0029.
 */

public class LoginPresenter extends LoginContract.Presenter {
    private String token;

    @Override
    void doLogin(String phone, String captcha) {
        CarefreeRetrofit.getInstance().createApi(UserApis.class)
                .doLogin(QueryMapBuilder.getIns().put("mobile", phone).put("captcha", captcha).buildPost())
                .subscribeOn(Schedulers.io())
                .flatMap(userInfoBaseResponse -> {
                    token = userInfoBaseResponse.data.getToken();
                    CarefreeDaoSession.setCurrentForm(phone);
                    CarefreeDaoSession.getInstance().setUserInfo(userInfoBaseResponse.data);
                    return CarefreeRetrofit.getInstance().createApi(UserApis.class)
                            .getUserInfo(userInfoBaseResponse.data.getUid(), QueryMapBuilder.getIns().buildGet());
                })
                .doOnNext(userInfoBaseResponse -> {
                    UserInfo data = userInfoBaseResponse.data;
                    data.setMid(CarefreeDaoSession.getInstance().getUserInfo().getMid());
                    data.setToken(token);
                    CarefreeDaoSession.getInstance().updateUserInfo(data);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<UserInfo>>() {
                    @Override
                    public void onSuccess(BaseResponse<UserInfo> userInfoBaseResponse) {
                        if (isAttach()) mView.loginSuccess();
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        if (isAttach()) mView.showError(e.getDisplayMessage(), e.getCode());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        CarefreeDaoSession.getInstance().clearUserInfo();
                    }
                });
    }

    @Override
    void doLoginPassword(String userName, String psw) {
        CarefreeRetrofit.getInstance().createApi(UserApis.class)
                .doLogin(QueryMapBuilder.getIns().put("username", userName).put("password", psw).buildPost())
                .subscribeOn(Schedulers.io())
                .flatMap(userInfoBaseResponse -> {
                    token = userInfoBaseResponse.data.getToken();
                    CarefreeDaoSession.setCurrentForm(userName);
                    CarefreeDaoSession.getInstance().setUserInfo(userInfoBaseResponse.data);
                    return CarefreeRetrofit.getInstance().createApi(UserApis.class)
                            .getUserInfo(userInfoBaseResponse.data.getUid(), QueryMapBuilder.getIns().buildGet());
                })
                .doOnNext(userInfoBaseResponse -> {
                    UserInfo data = userInfoBaseResponse.data;
                    data.setMid(CarefreeDaoSession.getInstance().getUserInfo().getMid());
                    data.setToken(token);
                    CarefreeDaoSession.getInstance().updateUserInfo(data);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<UserInfo>>() {
                    @Override
                    public void onSuccess(BaseResponse<UserInfo> userInfoBaseResponse) {
//                        CarefreeDaoSession.getInstance().setUserInfo(userInfoBaseResponse.data);
                        if (isAttach()) mView.loginSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (CarefreeDaoSession.isLogin()) CarefreeDaoSession.getInstance().clearUserInfo();
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        if (isAttach()) mView.showError(e.getDisplayMessage(), e.getCode());
                    }
                });
    }

    @Override
    void getVerifyCode(String phone) {
        CarefreeRetrofit.getInstance().createApi(UserApis.class)
                .getVerifyCode(QueryMapBuilder.getIns().put("mobile", phone).buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<UserInfo>>() {
                    @Override
                    public void onSuccess(BaseResponse<UserInfo> userInfoBaseResponse) {
                        if (isAttach()) mView.getVerifySuccess();
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        if (isAttach()) mView.showError(e.getDisplayMessage(), e.getCode());
                    }
                });
    }
}
