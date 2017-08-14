package com.example.greatbook.model.leancloud;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

import java.util.Date;

/**
 * Created by MDove on 2017/8/11.
 */
@AVClassName("LLocalRecord")
public class LLocalRecord extends AVObject{
    private Date time;
    private String title;
    private String content;
    //对应的账号信息，用于网络同步
    private String belongId;
    private String belongLocalId;
    //0：默认（总的）；1：代表鸡汤；2：代表短句；3：代表段子
    private int type;
    //自己的分组
    private Long groupId;
    private String groupTitle;

    public String getBelongLocalId() {
        return getString("belongLocalId");
    }

    public void setBelongLocalId(String belongLocalId) {
        put("belongLocalId",belongLocalId);
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

    public int getType() {
        return getInt("type");
    }

    public void setType(int type) {
        put("type",type);
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
}
