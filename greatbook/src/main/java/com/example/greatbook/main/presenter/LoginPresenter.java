package com.example.greatbook.main.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.example.greatbook.App;
import com.example.greatbook.MySharedPreferences;
import com.example.greatbook.R;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.greendao.LocalGroupDao;
import com.example.greatbook.greendao.LocalRecordDao;
import com.example.greatbook.greendao.MyPlanTemplateDao;
import com.example.greatbook.greendao.entity.LocalGroup;
import com.example.greatbook.greendao.entity.MyPlanTemplate;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.main.presenter.contract.LoginContract;
import com.example.greatbook.utils.NetUtil;
import com.example.greatbook.utils.RxUtil;

import java.util.Date;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by MDove on 2017/8/14.
 */

public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {
    private Context context;

    public LoginPresenter(LoginContract.View view) {
        mView = view;

        context=App.getInstance().getContext();
    }

    @Override
    public void login(final String username, final String password) {
        if (NetUtil.isNetworkAvailable()) {
            final Subscription subscription = rx.Observable.create(new rx.Observable.OnSubscribe<String>() {
                @Override
                public void call(final Subscriber<? super String> subscriber) {
                    new User().logInInBackground(username, password, new LogInCallback<AVUser>() {
                        @Override
                        public void done(AVUser avUser, AVException e) {
                            if (e == null) {
                                MySharedPreferences.setLogin(true);
                                subscriber.onNext("0");
                            } else {
                                subscriber.onNext("1");
                            }
                        }
                    });
                }
            }).compose(RxUtil.<String>rxSchedulerHelper())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            switch (s) {
                                case "0":
                                    mView.loginSuc("登录成功");
                                    break;
                                case "1":
                                    mView.loginErr("登录失败：请注意账号密码是否匹配");
                                    break;
                            }
                        }
                    });
            addSubscrebe(subscription);

        }else{
            mView.loginErr(context.getResources().getString(R.string.net_err));
        }
    }
}
