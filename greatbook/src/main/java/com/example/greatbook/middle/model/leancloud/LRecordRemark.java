package com.example.greatbook.middle.model.leancloud;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.example.greatbook.model.leancloud.LLocalRecord;

import java.util.Date;

/**
 * Created by MDove on 2017/8/21.
 */
@AVClassName("LRecordRemark")
public class LRecordRemark extends AVObject {

    public String getBelongId() {
        return getString("belongId");
    }

    public void setBelongId(String belongId) {
        put("belongId",belongId);
    }

    public String getBelongUserId() {
        return getString("belongUserId");
    }

    public void setBelongUserId(String belingId) {
        put("belongUserId",belingId);
    }

    public String getContent() {
        return getString("content");
    }

    public void setContent(String content) {
        put("content",content);
    }

}
