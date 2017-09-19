package com.example.greatbook.local.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.nethot.model.DiscoveryRecord;
import com.example.greatbook.nethot.model.DiscoveryTopGroup;

import java.util.List;

/**
 * Created by MDove on 2017/8/13.
 */

public interface MiddleDiscoveryContract {
    interface Presenter extends BasePresenter<MiddleDiscoveryContract.View> {
        void initDiscoveryTop();
        void initDiscoveryRecord();
    }

    interface View extends BaseView {
        void initDiscoveryGroupError(String error);
        void initDiscoveryRecordError(String error);
        void initDiscoveryGroupSuc(List<DiscoveryTopGroup> topGroups);
        void initDiscoveryRecordSuc(List<DiscoveryRecord> topGroups);
    }
}
