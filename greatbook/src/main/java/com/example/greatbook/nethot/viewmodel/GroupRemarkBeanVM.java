package com.example.greatbook.nethot.viewmodel;

import android.databinding.ObservableField;

/**
 * Created by MDove on 2017/10/15.
 */

public class GroupRemarkBeanVM {
    public ObservableField<String> content = new ObservableField<>();
    public ObservableField<String> time = new ObservableField<>();
    public ObservableField<String> nick = new ObservableField<>();
    public String belongId;
    public ObservableField<String> avatarPath = new ObservableField<>();

}
