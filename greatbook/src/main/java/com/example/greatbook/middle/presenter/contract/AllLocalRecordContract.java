package com.example.greatbook.middle.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.greendao.entity.LocalGroup;
import com.example.greatbook.greendao.entity.LocalRecord;
import com.example.greatbook.model.LocalRecordRLV;

import java.util.List;

/**
 * Created by MDove on 2017/8/13.
 */

public interface AllLocalRecordContract {
    interface Presenter extends BasePresenter<AllLocalRecordContract.View> {
        void initLocalRecords();
        void deleteLocalRecord(LocalRecordRLV localRecord);
        void deleteLocalRecordToNet(LocalRecordRLV localRecord);
    }

    interface View extends BaseView {
        void initLocalRecords(List<LocalRecordRLV> records);
        void deleteLocalGroupToNetReturn(String returnStr);
        void deleteLocalGroupReturn(String returnStr);
    }
}
