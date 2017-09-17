package com.example.greatbook.base;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by MDove on 2017/9/16.
 */

public abstract class SimpleTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
    @Override
    public void afterTextChanged(Editable s) {
    }
}
