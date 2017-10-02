package com.example.greatbook.local.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.greendao.entity.LocalRecord;
import com.example.greatbook.local.model.LocalRecordRLV;

import java.util.List;

/**
 * Created by MDove on 2017/10/2.
 */

public interface LocalRecordSmallContract {
    interface Presenter extends BasePresenter<LocalRecordSmallContract.View> {
        void initLocalRecords();
        void deleteLocalRecord(LocalRecordRLV localRecord);
        void deleteLocalRecordToNet(LocalRecordRLV localRecord);
        void updateLocalRecord(LocalRecordRLV localRecord, String title, String content);
        void updateLocalRecordToNet(LocalRecord localRecord);
    }

    interface View extends BaseView {
        void initLocalRecords(List<LocalRecordRLV> records);
        void deleteLocalGroupReturn(String returnStr);
        void deleteLocalGroupToNetReturn(String returnStr);
        void updateLocalRecordReturn(String returnStr);
        void updateLocalRecordToNetReturn(String returnStr);
    }
}
