package com.example.greatbook.middle.model.leancloud;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by MDove on 2017/8/25.
 */

@AVClassName("LGroupFollowers")
public class LGroupFollowers extends AVObject{
    private String groupObjectId;
    private String belongUserId;
    private String followingUserId;

    public String getFollowingUserId() {
        return getString("followingUserId");
    }

    public void setFollowingUserId(String followingUserId) {
        put("followingUserId",followingUserId);
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
