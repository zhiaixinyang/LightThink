package com.example.greatbook.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by MDove on 2017/10/15.
 */
@Entity
public class MyCollecting {
    @Id
    @Property(nameInDb = "proid")
    public Long id;
    
    public String belongUserId;
    public String groupObjectId;
    public String getGroupObjectId() {
        return this.groupObjectId;
    }
    public void setGroupObjectId(String groupObjectId) {
        this.groupObjectId = groupObjectId;
    }
    public String getBelongUserId() {
        return this.belongUserId;
    }
    public void setBelongUserId(String belongUserId) {
        this.belongUserId = belongUserId;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1273074771)
    public MyCollecting(Long id, String belongUserId, String groupObjectId) {
        this.id = id;
        this.belongUserId = belongUserId;
        this.groupObjectId = groupObjectId;
    }
    @Generated(hash = 1373532468)
    public MyCollecting() {
    }
    
}
