package com.example.greatbook.middle.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.model.DiscoveryTopGroup;
import com.example.greatbook.model.LocalRecordRLV;

import java.util.List;

/**
 * Created by MDove on 2017/8/13.
 */

public interface MiddleDiscoveryContract {
    interface Presenter extends BasePresenter<MiddleDiscoveryContract.View> {
        void initDiscoveryTop();
    }

    interface View extends BaseView {
        void initDiscoveryTopError(String error);
        void initDiscoveryTopSuc(List<DiscoveryTopGroup> topGroups);
    }
}
