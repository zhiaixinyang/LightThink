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

    public AVObject getBelong() {
        return getAVObject("belong");
    }

    public void setBelong(LLocalRecord belong) {
        put("belong",belong);
    }

    public String getBelingId() {
        return getString("belingId");
    }

    public void setBelingId(String belingId) {
        put("belingId",belingId);
    }

    public String getContent() {
        return getString("content");
    }

    public void setContent(String content) {
        put("content",content);
    }

    public Date getTime() {
        return getDate("time");
    }

    public void setTime(Date time) {
        put("time",time);
    }
}
