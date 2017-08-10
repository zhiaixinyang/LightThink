package com.example.greatbook.hot.presenter;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.model.GrammarKindIndex;
import com.example.greatbook.presenter.contract.ContentExploreContract;

import java.util.List;

/**
 * Created by MDove on 2017/8/10.
 */

public interface HotContract {
    interface View extends BaseView {
    }
    interface Presenter extends BasePresenter<HotContract.View> {
    }
}
