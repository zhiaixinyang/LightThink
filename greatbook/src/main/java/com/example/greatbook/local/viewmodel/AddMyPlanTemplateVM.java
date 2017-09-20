package com.example.greatbook.local.viewmodel;

import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.greatbook.base.SimpleTextWatcher;
import com.example.greatbook.utils.LogUtils;
import com.example.greatbook.utils.StringUtils;

/**
 * Created by MDove on 2017/9/16.
 */

public class AddMyPlanTemplateVM {
    public ObservableField<Integer> bgColor=new ObservableField<>();
    public ObservableField<Integer> textColor=new ObservableField<>();
    public ObservableField<Integer> detailColor=new ObservableField<>();
    public ObservableField<String> title1=new ObservableField<>();
    public ObservableField<String> title2=new ObservableField<>();
    public ObservableField<String> title3=new ObservableField<>();
    public ObservableField<String> tips=new ObservableField<>();

    public TextWatcher watcher1 = new SimpleTextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!StringUtils.isEquals(title1.get(), s.toString())) {
                title1.set(s.toString());
            }
        }
    };
    public TextWatcher watcher2 = new SimpleTextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!StringUtils.isEquals(title2.get(), s.toString())) {
                title2.set(s.toString());
            }
        }
    };
    public TextWatcher watcher3 = new SimpleTextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!StringUtils.isEquals(title3.get(), s.toString())) {
                title3.set(s.toString());
            }
        }
    };
}
