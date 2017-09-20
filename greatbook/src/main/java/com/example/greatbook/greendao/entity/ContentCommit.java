package com.example.greatbook.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

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


    public long essayId;

    public Date time;
    public String commitTips;
    public String commitContent;
    public String originContent;
    public String belongUserId;
    public String belongUserAccount;

    @Generated(hash = 1591792913)
    public ContentCommit(Long id, long essayId, Date time, String commitTips,
            String commitContent, String originContent, String belongUserId,
            String belongUserAccount) {
        this.id = id;
        this.essayId = essayId;
        this.time = time;
        this.commitTips = commitTips;
        this.commitContent = commitContent;
        this.originContent = originContent;
        this.belongUserId = belongUserId;
        this.belongUserAccount = belongUserAccount;
    }
    @Generated(hash = 1711378026)
    public ContentCommit() {
    }

    public String getBelongUserId() {
        return this.belongUserId;
    }
    public void setBelongUserId(String belongUserId) {
        this.belongUserId = belongUserId;
    }
    public String getCommitContent() {
        return this.commitContent;
    }
    public void setCommitContent(String commitContent) {
        this.commitContent = commitContent;
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

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getEssayId() {
        return this.essayId;
    }
    public void setEssayId(Long essayId) {
        this.essayId = essayId;
    }
    public String getBelongUserAccount() {
        return this.belongUserAccount;
    }
    public void setBelongUserAccount(String belongUserAccount) {
        this.belongUserAccount = belongUserAccount;
    }
    public void setEssayId(long essayId) {
        this.essayId = essayId;
    }
    public String getOriginContent() {
        return this.originContent;
    }
    public void setOriginContent(String originContent) {
        this.originContent = originContent;
    }

}
