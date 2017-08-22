package com.example.greatbook.middle.viewmodel;

import android.databinding.ObservableField;

/**
 * Created by MDove on 2017/8/21.
 */

public class RecordRemarkBeanVM {
    public ObservableField<String> content=new ObservableField<>();
    public ObservableField<String> time=new ObservableField<>();
    public ObservableField<String> nick=new ObservableField<>();
    public String belongId;
    public ObservableField<String> avatarPath=new ObservableField<>();

}
