package com.example.greatbook.middle.presenter;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.avos.avoscloud.AVUser;
import com.example.greatbook.R;
import com.example.greatbook.databinding.ActivityEssayListBinding;
import com.example.greatbook.greendao.entity.Essay;
import com.example.greatbook.middle.activity.PrefectEssayActivity;
import com.example.greatbook.middle.presenter.contract.EssayListContract;
import com.example.greatbook.model.leancloud.User;

import java.util.List;

/**
 * Created by MDove on 17/9/18.
 */

public class EssayListActivity extends AppCompatActivity implements EssayListContract.View {
    private ActivityEssayListBinding binding;
    private EssayListPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_essay_list);
        presenter = new EssayListPresenter();
        binding.btnAddEssay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVUser currentUser = User.getCurrentUser(AVUser.class);
                if (currentUser != null) {
                    presenter.addEssay(currentUser.getObjectId());
                }
            }
        });
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void initEssayListSuc(List<Essay> data) {

    }

    @Override
    public void addEssaySuc(long essayId) {
        PrefectEssayActivity.startPrefectEssay(this,essayId);
    }
}
