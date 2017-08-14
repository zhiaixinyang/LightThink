package com.example.greatbook.ui.main.presenter;

import android.content.Intent;
import android.content.SharedPreferences;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.example.greatbook.App;
import com.example.greatbook.MySharedPreferences;
import com.example.greatbook.R;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.greendao.LocalGroupDao;
import com.example.greatbook.greendao.LocalRecordDao;
import com.example.greatbook.greendao.entity.LocalGroup;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.ui.main.activity.MainActivity;
import com.example.greatbook.ui.main.presenter.contract.LoginContract;
import com.example.greatbook.utils.RxUtil;
import com.example.greatbook.utils.ToastUtil;

import java.util.Date;
import java.util.Observable;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by MDove on 2017/8/14.
 */

public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {
    private LocalGroupDao localGroupDao;
    private LocalRecordDao localRecordDao;

    public LoginPresenter(LoginContract.View view) {
        mView = view;
        localGroupDao = App.getDaoSession().getLocalGroupDao();
        localRecordDao = App.getDaoSession().getLocalRecordDao();
    }

    @Override
    public void login(final String username, final String password) {
        final Subscription subscription= rx.Observable.create(new rx.Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                new User().logInInBackground(username, password, new LogInCallback<AVUser>() {
                    @Override
                    public void done(AVUser avUser, AVException e) {
                        if (e == null) {
                            //首次登陆初始化本地数据库
                            initDB();
                            SharedPreferences sharedPreferences = MySharedPreferences.getFristActivityInstance();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("count", 1);
                            editor.commit();
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
                        switch (s){
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

    }

    @Override
    public void initDB() {
        //第一次登陆往本地数据库初始化一些数据
        localGroupDao.deleteAll();
        localRecordDao.deleteAll();

        LocalGroup localGroupJok = new LocalGroup();
        localGroupJok.setTitle("我的本地段子集");
        localGroupJok.setTime(new Date());
        localGroupJok.setUserd(true);
        localGroupJok.setBelongId(AVUser.getCurrentUser().getObjectId());
        localGroupJok.setContent("随手记录让我一笑的段子。");
        localGroupDao.insert(localGroupJok);

        LocalGroup localGroupEncourage = new LocalGroup();
        localGroupEncourage.setTitle("我的本地鸡汤集");
        localGroupEncourage.setTime(new Date());
        localGroupEncourage.setBelongId(AVUser.getCurrentUser().getObjectId());
        localGroupEncourage.setContent("随手记录让我燃起来的鸡汤。");
        localGroupEncourage.setUserd(true);
        localGroupDao.insert(localGroupEncourage);

        LocalGroup localGroupShortEssay = new LocalGroup();
        localGroupShortEssay.setTitle("我的本地清新集");
        localGroupShortEssay.setTime(new Date());
        localGroupShortEssay.setUserd(true);
        localGroupShortEssay.setBelongId(AVUser.getCurrentUser().getObjectId());
        localGroupShortEssay.setContent("随手记录让我静心的短句。");
        localGroupDao.insert(localGroupShortEssay);
    }
}
