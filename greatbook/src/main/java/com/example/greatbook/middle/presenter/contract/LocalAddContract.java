package com.example.greatbook.middle.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.greendao.entity.LocalGroup;
import com.example.greatbook.greendao.entity.LocalRecord;

import java.util.List;

/**
 * Created by MDove on 2017/8/11.
 */

public interface LocalAddContract {
    interface Presenter extends BasePresenter<LocalAddContract.View> {
        void sendContentToNet(LocalRecord localRecord);
        void addContentToLocal(LocalRecord localRecord);
        void addNewLocalGroup(LocalGroup localGroup);
        void addNewLocalGroupToNet(LocalGroup localGroup);
        void initLocalGroup();
    }

    interface View extends BaseView {
        void sendContentToNetSuc(String success);
        void sendContentToNetError(String error);
        void sendNewLocalGroupToNetReturn(String returnStr);
        void addContentToLocalSuc(String success);
        void addContentToLocalError(String error);
        void addNewLocalGroupSuc(String success);
        void initLocalGroup(List<LocalGroup> groups);
    }
}
