package com.example.greatbook.local.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.greendao.entity.ContentCommit;
import com.example.greatbook.greendao.entity.Essay;
import com.example.greatbook.local.model.EssayListItem;
import com.example.greatbook.model.leancloud.User;

import java.util.List;
import java.util.Map;

/**
 * Created by MDove on 17/9/18.
 */

public interface EssayListContract {
    interface Presenter extends BasePresenter<EssayListContract.View> {
        void initEssayList();
        void addEssay(User user);
    }

    interface View extends BaseView {
        void initEssayListSuc(List<EssayListItem> data);
        void initEssayListEmpty();
        void addEssaySuc(long essayId);
    }
}
