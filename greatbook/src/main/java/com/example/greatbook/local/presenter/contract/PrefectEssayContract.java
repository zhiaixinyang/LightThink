package com.example.greatbook.local.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.greendao.entity.ContentCommit;
import com.example.greatbook.greendao.entity.Essay;

import java.util.List;

/**
 * Created by MDove on 17/9/18.
 */

public interface PrefectEssayContract {
    interface Presenter extends BasePresenter<PrefectEssayContract.View> {
        void insertContentCommit(ContentCommit contentCommit);

        void initEssay(Long essayId);

        void showCommitLog(String userId);

        void saveEssay(String content);
    }

    interface View extends BaseView {
        void insertContentCommitSuc();

        void saveEssaySuc();

        void showInitEssay(Essay essay);

        void showCommitLog(List<ContentCommit> data);
    }
}
