package com.example.greatbook.middle.presenter;

import com.example.greatbook.App;
import com.example.greatbook.greendao.ContentCommitDao;
import com.example.greatbook.greendao.entity.ContentCommit;
import com.example.greatbook.middle.presenter.contract.PrefectEssayContract;

/**
 * Created by MDove on 17/9/18.
 */

public class PrefectEssayPresenter implements PrefectEssayContract.Presenter {
    private PrefectEssayContract.View view;
    private ContentCommitDao contentCommitDao;

    public PrefectEssayPresenter(Long essayId) {
        contentCommitDao = App.getDaoSession().getContentCommitDao();
    }


    @Override
    public void attachView(PrefectEssayContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void insertContentCommit(ContentCommit contentCommit) {

    }

    @Override
    public void saveEssay(String content) {

    }
}
