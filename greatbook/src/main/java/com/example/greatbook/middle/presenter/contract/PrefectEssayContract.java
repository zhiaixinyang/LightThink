package com.example.greatbook.middle.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.greendao.entity.ContentCommit;

/**
 * Created by MDove on 17/9/18.
 */

public interface PrefectEssayContract {
    interface Presenter extends BasePresenter<PrefectEssayContract.View> {
        void insertContentCommit(ContentCommit contentCommit);
        void saveEssay(String content);
    }

    interface View extends BaseView {
        void insertContentCommitSuc();
        void saveEssaySuc();
    }
}
