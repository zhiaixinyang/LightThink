package com.example.greatbook.middle.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.middle.presenter.contract.MiddleDiscoveryContract;
import com.example.greatbook.model.DiscoveryTopGroup;
import com.example.greatbook.model.leancloud.LLocalGroup;
import com.example.greatbook.utils.LogUtils;
import com.example.greatbook.utils.RxUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by MDove on 2017/8/13.
 */

public class MiddleDiscoveryPresenter extends RxPresenter<MiddleDiscoveryContract.View> implements MiddleDiscoveryContract.Presenter {
    public MiddleDiscoveryPresenter(MiddleDiscoveryContract.View view){
        mView=view;
    }
    @Override
    public void initDiscoveryTop() {
        Subscription subscription= Observable.create(new Observable.OnSubscribe<List<LLocalGroup>>() {
            @Override
            public void call(final Subscriber<? super List<LLocalGroup>> subscriber) {
                AVQuery<LLocalGroup> query=AVQuery.getQuery(LLocalGroup.class);
                query.limit(10);
                query.addDescendingOrder("attentionNum");
                query.findInBackground(new FindCallback<LLocalGroup>() {
                    @Override
                    public void done(List<LLocalGroup> list, AVException e) {
                        if (e==null&&!list.isEmpty()){
                            subscriber.onNext(list);
                        }else{
                            LogUtils.d("MDove",e.getMessage());
                            mView.initDiscoveryTopError("数据获取失败，请注意网络问题");
                        }
                    }
                });
            }
        }).compose(RxUtil.<List<LLocalGroup>>rxSchedulerHelper())
                .subscribe(new Action1<List<LLocalGroup>>() {
                    @Override
                    public void call(List<LLocalGroup> lLocalGroups) {
                        List<DiscoveryTopGroup> data=new ArrayList<>();
                        for (LLocalGroup lLocalGroup:lLocalGroups){
                            DiscoveryTopGroup topGroup=new DiscoveryTopGroup();
                            topGroup.attentionNum=lLocalGroup.getAttentionNum();
                            topGroup.belongId=lLocalGroup.getBelongId();
                            topGroup.content=lLocalGroup.getContent();
                            topGroup.groupId=lLocalGroup.getGroupId();
                            topGroup.groupTitle=lLocalGroup.getGroupTitle();
                            topGroup.time=lLocalGroup.getTime();
                            data.add(topGroup);
                        }

                        mView.initDiscoveryTopSuc(data);
                    }
                });
        addSubscrebe(subscription);

    }
}
