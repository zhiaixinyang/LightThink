package com.example.greatbook.local.presenter;

import com.example.greatbook.App;
import com.example.greatbook.greendao.ContentCommitDao;
import com.example.greatbook.greendao.EssayDao;
import com.example.greatbook.greendao.entity.ContentCommit;
import com.example.greatbook.greendao.entity.Essay;
import com.example.greatbook.local.presenter.contract.PrefectEssayContract;
import com.example.greatbook.utils.LogUtils;
import com.example.greatbook.utils.ToastUtil;

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

        if (id > 0) {
            //清除连表查询缓存，不清除，取不到新插入的数据
            mEssay.resetContentCommits();
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
        mEssay.resetContentCommits();
        List<ContentCommit> data = mEssay.getContentCommits();
        if (data != null) {
            mView.showCommitLog(data);
        } else {
            mView.showCommitLogEmpty();
        }
    }

    @Override
    public void saveEssay(String content) {
        if (mEssay != null) {
            mEssay.content = content;
            mEssayDao.update(mEssay);
            mView.saveEssaySuc(mEssay);
        } else {
            ToastUtil.toastShort("保存内容失败");
        }
    }

    @Override
    public void deleteEssay(Essay essay) {
        mEssayDao.delete(essay);
    }

    @Override
    public void backCommits(String contentCommit) {
        if (mEssay != null && mEssayDao != null) {
            mEssay.content = contentCommit;
            mEssayDao.update(mEssay);
            mView.backCommits(mEssay);
        }
    }
}
