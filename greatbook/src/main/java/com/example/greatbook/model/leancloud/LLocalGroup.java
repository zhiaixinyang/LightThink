package com.example.greatbook.model.leancloud;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

import java.util.Date;

/**
 * Created by MDove on 2017/8/13.
 */
@AVClassName("LLocalGroup")
public class LLocalGroup extends AVObject{
    private Date time;
    private String content;
    //对应的账号信息，用于网络同步
    private String belongId;
    //自己的分组
    private Long groupId;
    private String groupTitle;
    private int attentionNum;
    private boolean isUserd;

    public boolean isUserd() {
        return getBoolean("isUserd");
    }

    public void setUserd(boolean userd) {
        put("isUserd",userd);
    }

    public Date getTime() {
        return getDate("time");
    }

    public void setTime(Date time) {
        put("time",time);
    }

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        put("title",title);
    }

    public String getContent() {
        return getString("content");
    }

    public void setContent(String content) {
        put("content",content);
    }

    public String getBelongId() {
        return getString("belongId");
    }

    public void setBelongId(String belongId) {
        put("belongId",belongId);
    }


    public Long getGroupId() {
        return getLong("groupId");
    }

    public void setGroupId(Long groupId) {
        put("groupId",groupId);
    }

    public String getGroupTitle() {
        return getString("groupTitle");
    }

    public void setGroupTitle(String groupTitle) {
        put("groupTitle", groupTitle);
    }

    public int getAttentionNum() {
        return getInt("attentionNum");
    }

    public void setAttentionNum(int attentionNum) {
        put("attentionNum",attentionNum);
    }
}
