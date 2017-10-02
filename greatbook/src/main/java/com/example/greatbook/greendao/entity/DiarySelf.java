package com.example.greatbook.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by MDove on 2017/10/2.
 */
@Entity
public class DiarySelf {
    @Id
    public Long id;
    public String content;
    public String belongId;
    public String belongUserAccount;
    public Date time;
    public Date getTime() {
        return this.time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    public String getBelongUserAccount() {
        return this.belongUserAccount;
    }
    public void setBelongUserAccount(String belongUserAccount) {
        this.belongUserAccount = belongUserAccount;
    }
    public String getBelongId() {
        return this.belongId;
    }
    public void setBelongId(String belongId) {
        this.belongId = belongId;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 709615935)
    public DiarySelf(Long id, String content, String belongId,
            String belongUserAccount, Date time) {
        this.id = id;
        this.content = content;
        this.belongId = belongId;
        this.belongUserAccount = belongUserAccount;
        this.time = time;
    }
    @Generated(hash = 2031544505)
    public DiarySelf() {
    }
}
