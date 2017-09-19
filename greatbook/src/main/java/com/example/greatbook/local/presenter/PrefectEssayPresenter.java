package com.example.greatbook.local.presenter;

import com.example.greatbook.App;
import com.example.greatbook.greendao.ContentCommitDao;
import com.example.greatbook.greendao.EssayDao;
import com.example.greatbook.greendao.entity.ContentCommit;
import com.example.greatbook.greendao.entity.Essay;
import com.example.greatbook.local.presenter.contract.PrefectEssayContract;

import java.util.List;

/**
 * Created by MDove on 17/9/18.
 */

public class PrefectEssayPresenter implements PrefectEssayContract.Presenter {
    private PrefectEssayContract.View mView;
    private ContentCommitDao mContentCommitDao;
    private EssayDao mEssayDao;
    private Long mEssayId;
    private Essay mEssay;

    public PrefectEssayPresenter() {
        mContentCommitDao = App.getDaoSession().getContentCommitDao();
        mEssayDao = App.getDaoSession().getEssayDao();
    }


    @Override
    public void attachView(PrefectEssayContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void insertContentCommit(ContentCommit contentCommit) {
        long id = mContentCommitDao.insert(contentCommit);
        if (id>0){
            mView.insertContentCommitSuc();
        }
    }

    @Override
    public void initEssay(Long essayId) {
        mEssayId = essayId;
        mEssay = mEssayDao.queryBuilder().where(EssayDao.Properties.Id.eq(essayId)).list().get(0);
        mView.showInitEssay(mEssay);
    }

    @Override
    public void showCommitLog(String userId) {
        List<ContentCommit> data = mContentCommitDao.queryBuilder()
                .where(ContentCommitDao.Properties.BelongUserId.eq(userId)
                        , ContentCommitDao.Properties.EssayId.eq(mEssayId))
                .list();
        if (data != null) {
            mView.showCommitLog(data);
        }
    }

    @Override
    public void saveEssay(String content) {

    }
}
