package com.example.greatbook.middle.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;

import com.example.greatbook.R;
import com.example.greatbook.base.SimpleTextWatcher;
import com.example.greatbook.databinding.ActivityPrefectEssayBinding;
import com.example.greatbook.middle.presenter.EssayListActivity;
import com.example.greatbook.middle.presenter.PrefectEssayPresenter;
import com.example.greatbook.middle.presenter.contract.PrefectEssayContract;
import com.example.greatbook.utils.LogUtils;
import com.example.greatbook.utils.ToastUtil;

/**
 * Created by MDove on 2017/9/17.
 * <p>
 * 协同Activity
 */

public class PrefectEssayActivity extends AppCompatActivity implements PrefectEssayContract.View {
    private ActivityPrefectEssayBinding binding;
    //当前行未修改内容
    private SparseArray<String> originText;
    private SparseArray<String> laterText;
    public static final String ESSAY_ID = "essay_id";
    private PrefectEssayPresenter presenter;

    public static void startPrefectEssay(Context context, long essayId) {
        Intent start = new Intent(context, PrefectEssayActivity.class);
        start.putExtra(ESSAY_ID, essayId);
        context.startActivity(start);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_prefect_essay);

        long essayId = getIntent().getLongExtra(ESSAY_ID, 0);
        if (essayId >0){
            presenter=new PrefectEssayPresenter(essayId);
        }else{
            finish();
        }

        originText = new SparseArray<>();
        laterText = new SparseArray<>();

        binding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    private int getCurrentCursorLine(EditText editText) {
        int selectionStart = Selection.getSelectionStart(editText.getText());
        Layout layout = editText.getLayout();

        if (selectionStart != -1) {
            return layout.getLineForOffset(selectionStart) + 1;
        }
        return -1;
    }

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    public void insertContentCommitSuc() {

    }

    @Override
    public void saveEssaySuc() {

    }
}
