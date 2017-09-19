package com.example.greatbook.local.viewmodel;

import android.databinding.ObservableField;

/**
 * Created by MDove on 2017/8/24.
 */

public class GroupAndRecordsBeanVM {
    public ObservableField<String> content = new ObservableField<>();
    public ObservableField<String> time = new ObservableField<>();
    public ObservableField<String> title = new ObservableField<>();
    public String belongId;

}
