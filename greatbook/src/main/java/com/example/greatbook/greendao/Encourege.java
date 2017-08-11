package com.example.greatbook.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by MDove on 2017/8/11.
 */

/**
 * 鸡汤实体类
 */
@Entity
public class Encourege {
    @Id
    @Property(nameInDb = "proid")
    private Long id;
    private String time;
    private String content;
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
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
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
    @Generated(hash = 456274693)
    public Encourege(Long id, String time, String content, int type, String group) {
        this.id = id;
        this.time = time;
        this.content = content;
        this.type = type;
        this.group = group;
    }
    @Generated(hash = 1877958648)
    public Encourege() {
    }
}
