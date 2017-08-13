package com.example.greatbook.middle.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.example.greatbook.App;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.greendao.LocalGroupDao;
import com.example.greatbook.greendao.entity.LocalGroup;
import com.example.greatbook.middle.adapter.SetGroupsAdapter;
import com.example.greatbook.middle.presenter.contract.SetGroupsContract;
import com.example.greatbook.model.leancloud.LLocalGroup;
import com.example.greatbook.utils.RxUtil;
import com.example.greatbook.utils.ToastUtil;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by MDove on 2017/8/13.
 */

public class SetGroupsPresenter extends RxPresenter<SetGroupsContract.View> implements SetGroupsContract.Presenter {
    private LocalGroupDao localGroupDao;
    public SetGroupsPresenter(SetGroupsContract.View view){
        mView=view;
        localGroupDao= App.getDaoSession().getLocalGroupDao();
    }
    @Override
    public void deleteLocalGroup(final LocalGroup localGroup) {
        localGroupDao.delete(localGroup);
        mView.deleteLocalGroupReturn("删除完毕");
        deleteLocalGroupToNet(localGroup);
    }

    @Override
    public void deleteLocalGroupToNet(final LocalGroup localGroup) {
        Subscription subscription=Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                AVQuery<LLocalGroup> query=AVQuery.getQuery(LLocalGroup.class);
                query.whereEqualTo("groupId",localGroup.getId());
                query.findInBackground(new FindCallback<LLocalGroup>() {
                    @Override
                    public void done(List<LLocalGroup> list, AVException e) {
                        if (e==null&&!list.isEmpty()){
                            list.get(0).deleteInBackground(new DeleteCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e==null){
                                        subscriber.onNext("服务器删除成功");
                                    }else{
                                        subscriber.onNext("服务器删除失败："+e.getMessage());

                                    }
                                }
                            });
                        }else{
                            subscriber.onNext("服务器删除失败");

                        }
                    }
                });
            }
        }).compose(RxUtil.<String>rxSchedulerHelper())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        mView.deleteLocalGroupToNetReturn(s);
                    }
                });
        addSubscrebe(subscription);
    }

    @Override
    public void setUserdGroups(List<LocalGroup> groups,int pos) {
        //遍历当前Groups中所有设置为常用的个数，超过5个，则回调满
        int userdNum=0;
        for (LocalGroup group:groups){
            if (group.isUserd()){
                ++userdNum;
            }
        }
        LocalGroup localGroup=groups.get(pos);
        if (userdNum>=5 && ! localGroup.getIsUserd()){
            mView.userdGroupsIsOver();
        }else {
            if (localGroup.getIsUserd()) {
                if (userdNum>1) {
                    localGroup.setUserd(false);
                    localGroupDao.update(localGroup);
                    groups.remove(localGroup);
                    groups.add(groups.size(), localGroup);
                    mView.returnSetUserdGroups(groups);
                }else{
                    ToastUtil.toastShort("至少要选择一个常用文集");
                }
            } else {
                localGroup.setUserd(true);
                localGroupDao.update(localGroup);
                groups.remove(localGroup);
                groups.add(0, localGroup);
                mView.returnSetUserdGroups(groups);
            }
        }
    }

    @Override
    public void initLocalGroup() {
        if (localGroupDao!=null) {
            mView.initLocalGroup(localGroupDao.loadAll());
        }
    }
}