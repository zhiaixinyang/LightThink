package com.example.greatbook.middle.activity;

import android.app.Activity;
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
import com.example.greatbook.utils.LogUtils;

/**
 * Created by MDove on 2017/9/17.
 *
 * 协同Activity
 */

public class PrefectEssayActivity extends AppCompatActivity {
    private ActivityPrefectEssayBinding binding;
    //当前行未修改内容
    private SparseArray<String> originText;
    private SparseArray<String> laterText;
    private int curLine = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_prefect_essay);

        originText = new SparseArray<>();
        laterText = new SparseArray<>();

        binding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.etContent.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int line = getCurrentCursorLine(binding.etContent);
                LogUtils.d("beforeTextChanged!!line:" + line + "text:" + s.toString() + "start:" + start + "after:" + after + "count:" + count);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int line = getCurrentCursorLine(binding.etContent);
                LogUtils.d("onTextChanged!!line:" + line + "text:" + s.toString() + "start:" + start + "before:" + before + "count:" + count);
            }
            @Override
            public void afterTextChanged(Editable s) {
                LogUtils.d("afterTextChanged!!:"+s.toString());

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
}
