package com.example.greatbook.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

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
    private Date timeDate;
    private String title;
    private String content;
    //对应的账号信息，用于网络同步
    private String belongId;
    //0：默认（总的）；1：代表鸡汤；2：代表短句；3：代表段子
    private int type;
    private Long groupId;
    //自己的分组
    private String groupTitle;
    @Generated(hash = 194057123)
    public LocalRecord(Long id, Date timeDate, String title, String content,
            String belongId, int type, Long groupId, String groupTitle) {
        this.id = id;
        this.timeDate = timeDate;
        this.title = title;
        this.content = content;
        this.belongId = belongId;
        this.type = type;
        this.groupId = groupId;
        this.groupTitle = groupTitle;
    }
    @Generated(hash = 1667100475)
    public LocalRecord() {
    }
    public String getGroupTitle() {
        return this.groupTitle;
    }
    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }
    public Long getGroupId() {
        return this.groupId;
    }
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
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
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Date getTimeDate() {
        return this.timeDate;
    }
    public void setTimeDate(Date timeDate) {
        this.timeDate = timeDate;
    }
   
}
