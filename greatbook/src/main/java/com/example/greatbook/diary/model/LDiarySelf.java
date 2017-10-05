package com.example.greatbook.diary.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

import java.util.Date;

/**
 * Created by MDove on 2017/10/5.
 */

@AVClassName("LDiarySelf")
public class LDiarySelf extends AVObject{
    private String content;
    private String belongUserAccount;

    public String getContent() {
        return getString("content");
    }

    public void setContent(String content) {
        put("content",content);
    }

    public String getBelongUserAccount() {
        return getString("belongUserAccount");
    }

    public void setBelongUserAccount(String belongUserAccount) {
        put("belongUserAccount",belongUserAccount);
    }
}
