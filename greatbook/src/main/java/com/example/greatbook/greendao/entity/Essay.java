package com.example.greatbook.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by MDove on 17/9/18.
 */
@Entity
public class Essay {
    @Id
    @Property(nameInDb = "proid")
    public Long id;
    public String content;
    public String belongId;
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
    @Generated(hash = 379078881)
    public Essay(Long id, String content, String belongId) {
        this.id = id;
        this.content = content;
        this.belongId = belongId;
    }
    @Generated(hash = 2009741210)
    public Essay() {
    }
}
