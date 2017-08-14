package com.example.greatbook.middle.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.greendao.entity.LocalGroup;
import com.example.greatbook.greendao.entity.LocalRecord;

import java.util.List;

/**
 * Created by MDove on 2017/8/13.
 */

public interface SetGroupsContract {
    interface Presenter extends BasePresenter<SetGroupsContract.View> {
        void deleteLocalGroup(LocalGroup localGroup);
        void deleteLocalGroupToNet(LocalGroup localGroup);
        void setUserdGroups(List<LocalGroup> groups,int pos);
        void updateGroupMes(LocalGroup group, String title, String content);
        void updateGroupMesToNet(LocalGroup group);
        void initLocalGroup();
    }

    interface View extends BaseView {
        void deleteLocalGroupToNetReturn(String returnStr);
        void deleteLocalGroupReturn(String returnStr);
        void updateGroupMesReturn(String returnStr);
        void updateGroupMesToNetReturn(String returnStr);
        void initLocalGroup(List<LocalGroup> groups);
        void returnSetUserdGroups(List<LocalGroup> groups);
        void userdGroupsIsOver();
    }
}
