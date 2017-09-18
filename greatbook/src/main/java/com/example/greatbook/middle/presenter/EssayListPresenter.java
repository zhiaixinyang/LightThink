package com.example.greatbook.middle.presenter;

import com.example.greatbook.App;
import com.example.greatbook.greendao.EssayDao;
import com.example.greatbook.greendao.entity.Essay;
import com.example.greatbook.middle.presenter.contract.EssayListContract;

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
    public void addEssay(String belongId) {
        Essay essay = new Essay();
        essay.belongId = belongId;
        essay.content = "";
        long insertId = essayDao.insert(essay);

        if (insertId > 0) {
            view.addEssaySuc(insertId);
        }
    }
}
