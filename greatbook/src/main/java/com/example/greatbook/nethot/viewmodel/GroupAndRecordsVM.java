package com.example.greatbook.nethot.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.greatbook.model.leancloud.LLocalRecord;
import com.example.greatbook.nethot.model.DiscoveryTopGroup;
import com.example.greatbook.nethot.model.GroupRecords;
import com.example.greatbook.utils.NetUtil;

import java.util.List;

/**
 * Created by MDove on 2017/8/24.
 */

public class GroupAndRecordsVM {
    public ObservableBoolean loadingRecords = new ObservableBoolean();
    public ObservableBoolean isRecordsEmpty = new ObservableBoolean();
    public ObservableBoolean isNetErr = new ObservableBoolean();
    public ObservableField<GroupRecords> groupAndRecordObservable = new ObservableField<>();

    private DiscoveryTopGroup discoveryTopGroup;

    public GroupAndRecordsVM(DiscoveryTopGroup discoveryTopGroup) {
        if (NetUtil.isNetworkAvailable()){
            isNetErr.set(false);
        }else{
            isNetErr.set(true);
        }
        isRecordsEmpty.set(false);
        this.discoveryTopGroup = discoveryTopGroup;
        GroupRecords groupRecords = new GroupRecords();
        groupAndRecordObservable.set(groupRecords);
    }

    public void initRecords() {
        if (NetUtil.isNetworkAvailable()) {
            isNetErr.set(false);
            loadingRecords.set(true);
            AVQuery<LLocalRecord> query = AVQuery.getQuery(LLocalRecord.class);
            query.whereEqualTo("belongId", discoveryTopGroup.belongId);
            query.whereEqualTo("groupId", discoveryTopGroup.groupId);
            query.findInBackground(new FindCallback<LLocalRecord>() {
                @Override
                public void done(List<LLocalRecord> list, AVException e) {
                    if (e == null) {
                        if (!list.isEmpty()) {
                            GroupRecords groupRecords = new GroupRecords();
                            groupRecords.data = list;
                            groupAndRecordObservable.set(groupRecords);
                            isRecordsEmpty.set(false);
                            loadingRecords.set(false);
                        } else {
                            isRecordsEmpty.set(true);
                            loadingRecords.set(false);
                        }
                    } else {
                        loadingRecords.set(false);
                    }
                }
            });
        } else {
            isNetErr.set(true);
        }
    }
}
