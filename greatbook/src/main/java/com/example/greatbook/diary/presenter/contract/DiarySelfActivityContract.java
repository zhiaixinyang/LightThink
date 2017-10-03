package com.example.greatbook.diary.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.greendao.entity.DiarySelf;

import java.util.List;

/**
 * Created by MDove on 2017/10/3.
 */

public interface DiarySelfActivityContract {
    interface Presenter extends BasePresenter<DiarySelfActivityContract.View> {
        void showAllDiartSelf();

        void deleteDiarySelf(DiarySelf diarySelf);
    }

    interface View extends BaseView {
        void showAllDiarySelf(List<DiarySelf> data);

        void diarySelfEmpty();

        void deleteDiarySelfSuc();
    }
}
