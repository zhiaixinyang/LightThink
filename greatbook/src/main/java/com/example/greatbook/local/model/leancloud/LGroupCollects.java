package com.example.greatbook.local.model.leancloud;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by MDove on 2017/8/25.
 */

@AVClassName("LGroupCollects")
public class LGroupCollects extends AVObject{
    private String belongUserId;
    private String groupObjectId;
    private String collectingUserId;

    public String getCollectingUserId() {
        return getString("collectingUserId");
    }

    public void setCollectingUserId(String collectingUserId) {
        put("collectingUserId",collectingUserId);
    }

    public String getGroupObjectId() {
        return getString("groupObjectId");
    }

    public void setGroupObjectId(String groupObjectId) {
        put("groupObjectId",groupObjectId);
    }

    public String getBelongUserId() {
        return getString("belongUserId");
    }

    public void setBelongUserId(String belongUserId) {
        put("belongUserId",belongUserId);
    }

}
