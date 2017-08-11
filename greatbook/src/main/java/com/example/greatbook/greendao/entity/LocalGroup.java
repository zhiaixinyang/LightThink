package com.example.greatbook.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by MDove on 2017/8/11.
 */

//类似于歌单
@Entity
public class LocalGroup {
    @Id
    @Property(nameInDb = "proid")
    private Long id;
    //创建时间
    private String time;
    //创建标题
    private String title;
    //创建内容
    private String content;
    //对应的账号信息，用于网络同步
    private String belongId;
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
    @Generated(hash = 1306992889)
    public LocalGroup(Long id, String time, String title, String content,
            String belongId) {
        this.id = id;
        this.time = time;
        this.title = title;
        this.content = content;
        this.belongId = belongId;
    }
    @Generated(hash = 648436687)
    public LocalGroup() {
    }
}
