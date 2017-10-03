package com.example.greatbook.diary.presenter;

import com.avos.avoscloud.AVUser;
import com.example.greatbook.App;
import com.example.greatbook.diary.presenter.contract.DiarySelfFragContract;
import com.example.greatbook.greendao.DiarySelfDao;
import com.example.greatbook.greendao.entity.DiarySelf;
import com.example.greatbook.model.leancloud.User;

import java.util.Date;
import java.util.List;

/**
 * Created by MDove on 2017/10/2.
 */

public class DiarySelfFragPresenter implements DiarySelfFragContract.Presenter {
    private DiarySelfFragContract.View mView;
    private List<DiarySelf> mData;
    private DiarySelfDao mDiarySelfDao;
    private User mUser;

    public DiarySelfFragPresenter() {
        mDiarySelfDao = App.getDaoSession().getDiarySelfDao();
        mUser = AVUser.getCurrentUser(User.class);
    }

    @Override
    public void attachView(DiarySelfFragContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void initDiartSelf() {
        mData = mDiarySelfDao
                .queryBuilder()
                .orderDesc(DiarySelfDao.Properties.Time)
                .list();

        if (mData != null && !mData.isEmpty()) {
            mView.showDiarySelf(mData);
        } else {
            mView.diarySelfEmpty();
        }
    }

    @Override
    public void addDiarySelf(String content) {
        if (mUser != null) {
            DiarySelf diarySelf = new DiarySelf();
            diarySelf.content = content;
            diarySelf.belongUserAccount = mUser.getUsername();
            Date time = new Date();
            diarySelf.time = time;
            long insert = mDiarySelfDao.insert(diarySelf);
            if (insert > -1) {
                mView.addDiarySelfSuc();
            }
        }
    }
}
