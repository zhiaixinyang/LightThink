package com.example.greatbook.middle.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.greendao.entity.LocalGroup;
import com.example.greatbook.greendao.entity.LocalRecord;
import com.example.greatbook.middle.model.LocalRecordRLV;

import java.util.List;

/**
 * Created by MDove on 2017/8/15.
 */

public interface AllLocalGroupContract {
    interface Presenter extends BasePresenter<AllLocalGroupContract.View> {
        void addLocalGroup(LocalGroup localGroup);
        void addLocalGroupToNet(LocalGroup localGroup);
    }

    interface View extends BaseView {
        void addLocalGroupSuc(String suc);
        void addLocalGroupErr(String err);
        void addLocalGroupToNetReturn(String returnStr);
    }
}
