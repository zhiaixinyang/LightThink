package com.example.greatbook.middle.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.middle.model.LocalRecordRLV;

import java.util.List;

/**
 * Created by MDove on 2017/8/12.
 */

public interface MiddleLocalAddContract {
    interface Presenter extends BasePresenter<MiddleLocalAddContract.View> {
        void initLocalRecord();
    }

    interface View extends BaseView {
        void initLocalRecordError(String error);
        void initLocalRecordSuc(List<LocalRecordRLV> records);
    }
}
