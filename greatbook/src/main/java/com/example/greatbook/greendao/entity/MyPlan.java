package com.example.greatbook.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by MDove on 2017/9/16.
 */
@Entity
public class MyPlan {
    @Id
    @Property(nameInDb = "proid")
    public Long id;
    public int bgColor;
    public String content;
    public int textColor;
    public int detailColor;
    public int textSize;


    @Generated(hash = 57971564)
    public MyPlan(Long id, int bgColor, String content, int textColor,
            int detailColor, int textSize) {
        this.id = id;
        this.bgColor = bgColor;
        this.content = content;
        this.textColor = textColor;
        this.detailColor = detailColor;
        this.textSize = textSize;
    }
    @Generated(hash = 44192092)
    public MyPlan() {
    }


    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getBgColor() {
        return this.bgColor;
    }
    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getTextSize() {
        return this.textSize;
    }
    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }
    public int getTextColor() {
        return this.textColor;
    }
    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
    public int getDetailColor() {
        return this.detailColor;
    }
    public void setDetailColor(int detailColor) {
        this.detailColor = detailColor;
    }
    
}
