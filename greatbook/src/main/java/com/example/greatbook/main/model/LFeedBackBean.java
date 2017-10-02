package com.example.greatbook.main.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by MDove on 2017/8/10.
 */
@AVClassName("LFeedBackBean")
public class LFeedBackBean extends AVObject {
    private String belongId;
    private String content;
    private int like;


    public String getBelongId() {
        return getString("belongId");
    }

    public void setBelongId(String belongId) {
        put("belongId",belongId);
    }

    public String getContent() {
        return getString("content");
    }

    public void setContent(String content) {
       put("content",content);
    }

    public int getLike() {
        return getInt("like");
    }

    public void setLike(int like) {
       put("like",like);
    }
}
