package com.example.greatbook.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by MDove on 2017/8/11.
 */

/**
 * 本地记录的实体类
 */
@Entity
public class LocalRecord{
    @Id
    @Property(nameInDb = "proid")
    private Long id;
    private String time;
    private String title;
    private String content;
    //对应的账号信息，用于网络同步
    private String belongId;
    //0：默认（总的）；1：代表鸡汤；2：代表短句；3：代表段子
    private int type;
    //自己的分组
    private String group;
    public String getGroup() {
        return this.group;
    }
    public void setGroup(String group) {
        this.group = group;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
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
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1621823000)
    public LocalRecord(Long id, String time, String title, String content,
            String belongId, int type, String group) {
        this.id = id;
        this.time = time;
        this.title = title;
        this.content = content;
        this.belongId = belongId;
        this.type = type;
        this.group = group;
    }
    @Generated(hash = 1667100475)
    public LocalRecord() {
    }
}
