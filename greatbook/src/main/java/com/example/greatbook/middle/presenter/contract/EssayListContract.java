package com.example.greatbook.middle.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.greendao.entity.ContentCommit;
import com.example.greatbook.greendao.entity.Essay;

import java.util.List;

/**
 * Created by MDove on 17/9/18.
 */

public interface EssayListContract {
    interface Presenter extends BasePresenter<EssayListContract.View> {
        void initEssayList();
        void addEssay(String belongId);
    }

    interface View extends BaseView {
        void initEssayListSuc(List<Essay> data);
        void addEssaySuc(long essayId);
    }
}
