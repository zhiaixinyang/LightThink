package com.example.greatbook.local.model.leancloud;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by MDove on 2017/8/24.
 */
@AVClassName("LRecordLike")
public class LRecordLike extends AVObject {
    public String belongId;
    public String belongUserId;

    public String getBelongId() {
        return getString("belongId");
    }

    public void setBelongId(String belongId) {
        put("belongId",belongId);
    }

    public String getBelongUserId() {
        return getString("belongUserId");
    }

    public void setBelongUserId(String belongUserId) {
        put("belongUserId",belongUserId);
    }
}
