package com.example.greatbook.local.presenter;

import com.example.greatbook.App;
import com.example.greatbook.greendao.EssayDao;
import com.example.greatbook.greendao.entity.Essay;
import com.example.greatbook.local.presenter.contract.EssayListContract;
import com.example.greatbook.model.leancloud.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 17/9/18.
 */

public class EssayListPresenter implements EssayListContract.Presenter {
    private EssayListContract.View view;
    private EssayDao essayDao;
    private List<Essay> data;

    public EssayListPresenter() {
        essayDao = App.getDaoSession().getEssayDao();
        data = new ArrayList<>();
    }

    @Override
    public void attachView(EssayListContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
        data = null;
    }

    @Override
    public void initEssayList() {
        data = essayDao.loadAll();
        if (!data.isEmpty()) {
            view.initEssayListSuc(data);
        }
    }

    @Override
    public void addEssay(User user) {
        Essay essay = new Essay();
        essay.belongId = user.getObjectId();
        essay.belongUserAccount = user.getUsername();
        essay.content = "";
        long insertId = essayDao.insert(essay);

        if (insertId > 0) {
            view.addEssaySuc(insertId);
        }
    }
}
