package com.example.greatbook.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.sql.Time;
import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by MDove on 17/9/18.
 */
@Entity
public class ContentCommit {
    @Id
    @Property(nameInDb = "proid")
    public Long id;
    public String essayId;

    public Date time;
    public String commitTips;
    public String commitContet;
    public String belongUserId;
    public String getBelongUserId() {
        return this.belongUserId;
    }
    public void setBelongUserId(String belongUserId) {
        this.belongUserId = belongUserId;
    }
    public String getCommitContet() {
        return this.commitContet;
    }
    public void setCommitContet(String commitContet) {
        this.commitContet = commitContet;
    }
    public String getCommitTips() {
        return this.commitTips;
    }
    public void setCommitTips(String commitTips) {
        this.commitTips = commitTips;
    }
    public Date getTime() {
        return this.time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    public String getEssayId() {
        return this.essayId;
    }
    public void setEssayId(String essayId) {
        this.essayId = essayId;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1546889303)
    public ContentCommit(Long id, String essayId, Date time, String commitTips,
            String commitContet, String belongUserId) {
        this.id = id;
        this.essayId = essayId;
        this.time = time;
        this.commitTips = commitTips;
        this.commitContet = commitContet;
        this.belongUserId = belongUserId;
    }
    @Generated(hash = 1711378026)
    public ContentCommit() {
    }
}
