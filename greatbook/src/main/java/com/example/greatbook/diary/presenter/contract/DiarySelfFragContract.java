package com.example.greatbook.diary.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.greendao.entity.DiarySelf;

import java.util.List;

/**
 * Created by MDove on 2017/10/2.
 */

public interface DiarySelfFragContract {
    interface Presenter extends BasePresenter<DiarySelfFragContract.View> {
        void initDiartSelf();

        void addDiarySelf(String content);
    }

    interface View extends BaseView {
        void showDiarySelf(List<DiarySelf> data);

        void diarySelfEmpty();

        void addDiarySelfSuc();
    }
}
