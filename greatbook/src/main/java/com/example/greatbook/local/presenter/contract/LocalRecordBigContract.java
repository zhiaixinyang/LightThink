package com.example.greatbook.local.presenter.contract;

import android.content.Context;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.local.model.LocalRecordRLV;
import com.example.greatbook.local.model.MainMenuItemBean;

import java.util.List;

/**
 * Created by MDove on 2017/10/2.
 */

public interface LocalRecordBigContract {
    interface Presenter extends BasePresenter<LocalRecordBigContract.View> {
        void initLocalRecord();
    }

    interface View extends BaseView {

        void initLocalRecordSuc(List<LocalRecordRLV> records);

        void localRecordEmpty();
    }
}
