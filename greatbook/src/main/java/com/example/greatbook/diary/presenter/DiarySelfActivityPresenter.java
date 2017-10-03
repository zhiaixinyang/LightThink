package com.example.greatbook.diary.presenter;

import com.example.greatbook.App;
import com.example.greatbook.diary.presenter.contract.DiarySelfActivityContract;
import com.example.greatbook.greendao.DiarySelfDao;
import com.example.greatbook.greendao.entity.DiarySelf;

import java.util.List;

/**
 * Created by MDove on 2017/10/3.
 */

public class DiarySelfActivityPresenter implements DiarySelfActivityContract.Presenter {
    private DiarySelfActivityContract.View mView;
    private DiarySelfDao mDiarySelfDao;
    private List<DiarySelf> mData;

    public DiarySelfActivityPresenter(){
        mDiarySelfDao = App.getDaoSession().getDiarySelfDao();
    }

    @Override
    public void attachView(DiarySelfActivityContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void showAllDiartSelf() {
        mData = mDiarySelfDao.queryBuilder()
                .orderDesc(DiarySelfDao.Properties.Time)
                .list();
        if (mData != null){
            if(!mData.isEmpty()) {
                mView.showAllDiarySelf(mData);
            } else {
                mView.diarySelfEmpty();
            }
        }
    }

    @Override
    public void deleteDiarySelf(DiarySelf diarySelf) {
        if (diarySelf != null) {
            mDiarySelfDao.delete(diarySelf);
            mView.deleteDiarySelfSuc();
        }
    }
}
