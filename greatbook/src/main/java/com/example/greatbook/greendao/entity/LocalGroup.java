package com.example.greatbook.greendao.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by MDove on 2017/8/11.
 */

//类似于歌单
@Entity
public class LocalGroup implements Serializable{
    @Id
    @Property(nameInDb = "proid")
    public Long id;
    //创建时间
    public Date time;
    //创建标题
    public String title;
    //创建内容
    public String content;
    //对应的账号信息，用于网络同步
    public String belongId;
    //是否设置为常用文集
    public boolean isUserd;
    public String groupPhotoPath;
    public int groupLocalPhotoPath;
    public String bgColor;


    

    @Generated(hash = 1799253374)
    public LocalGroup(Long id, Date time, String title, String content, String belongId,
            boolean isUserd, String groupPhotoPath, int groupLocalPhotoPath, String bgColor) {
        this.id = id;
        this.time = time;
        this.title = title;
        this.content = content;
        this.belongId = belongId;
        this.isUserd = isUserd;
        this.groupPhotoPath = groupPhotoPath;
        this.groupLocalPhotoPath = groupLocalPhotoPath;
        this.bgColor = bgColor;
    }

    @Generated(hash = 648436687)
    public LocalGroup() {
    }

    protected LocalGroup(Parcel in) {
        title = in.readString();
        content = in.readString();
        belongId = in.readString();
        isUserd = in.readByte() != 0;
        groupPhotoPath = in.readString();
        groupLocalPhotoPath = in.readInt();
        bgColor = in.readString();
    }

    public String getBgColor() {
        return this.bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public int getGroupLocalPhotoPath() {
        return this.groupLocalPhotoPath;
    }

    public void setGroupLocalPhotoPath(int groupLocalPhotoPath) {
        this.groupLocalPhotoPath = groupLocalPhotoPath;
    }

    public String getGroupPhotoPath() {
        return this.groupPhotoPath;
    }

    public void setGroupPhotoPath(String groupPhotoPath) {
        this.groupPhotoPath = groupPhotoPath;
    }

    public boolean getIsUserd() {
        return this.isUserd;
    }

    public void setIsUserd(boolean isUserd) {
        this.isUserd = isUserd;
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
}
