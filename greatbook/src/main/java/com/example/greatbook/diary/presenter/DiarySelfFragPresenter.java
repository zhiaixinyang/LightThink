package com.example.greatbook.diary.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.example.greatbook.App;
import com.example.greatbook.MySharedPreferences;
import com.example.greatbook.diary.model.LDiarySelf;
import com.example.greatbook.diary.presenter.contract.DiarySelfFragContract;
import com.example.greatbook.greendao.DiarySelfDao;
import com.example.greatbook.greendao.entity.DiarySelf;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.utils.NetUtil;
import com.example.greatbook.utils.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by MDove on 2017/10/2.
 */

public class DiarySelfFragPresenter implements DiarySelfFragContract.Presenter {
    private DiarySelfFragContract.View mView;
    private List<DiarySelf> mData;
    private List<DiarySelf> mNoSaveNetData;
    private DiarySelfDao mDiarySelfDao;
    private User mUser;

    public DiarySelfFragPresenter() {
        mDiarySelfDao = App.getDaoSession().getDiarySelfDao();
        mUser = AVUser.getCurrentUser(User.class);
        mNoSaveNetData = new ArrayList<>();
        mData = new ArrayList<>();
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
            //统计累计书写字数
            MySharedPreferences.putCurWords(StringUtils.isEmpty(content) ? 0 : content.length());

            DiarySelf diarySelf = new DiarySelf();
            diarySelf.content = content;
            diarySelf.belongUserAccount = mUser.getUsername();
            Date time = new Date();
            diarySelf.time = time;
            addDiarySelfToNet(diarySelf);
            long insert = mDiarySelfDao.insert(diarySelf);
            if (insert > -1) {
                mView.addDiarySelfSuc();
            }
        }
    }

    @Override
    public void addDiarySelfToNet(final DiarySelf diarySelf) {
        if (NetUtil.isNetworkAvailable()) {
            LDiarySelf lDiarySelf = new LDiarySelf();
            lDiarySelf.setBelongUserAccount(diarySelf.belongUserAccount);
            lDiarySelf.setContent(diarySelf.content);
            lDiarySelf.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    //上传失败
                    if (e != null) {
                        mNoSaveNetData.add(diarySelf);
                    } else {
                        //上传成功，从失败队列中移除
                        mNoSaveNetData.remove(diarySelf);
                    }
                }
            });
        } else {
            mView.addDiarySelfToNetErr("保持网络畅通，数据将传至云端");
            mNoSaveNetData.add(diarySelf);
        }

    }

    @Override
    public void retrySavaNet() {
        if (NetUtil.isNetworkAvailable()) {
            if (!mNoSaveNetData.isEmpty()) {
                for (DiarySelf diarySelf : mNoSaveNetData) {
                    addDiarySelfToNet(diarySelf);
                }
            }
        }else{
            mView.showError("保持网络畅通，数据将传至云端");
        }
    }
}
