package com.example.greatbook.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

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
    private Date time;
    //创建标题
    private String title;
    //创建内容
    private String content;
    //对应的账号信息，用于网络同步
    private String belongId;
    //是否设置为常用文集
    private boolean isUserd;
    private String groupPhotoPath;
    private int groupLocalPhotoPath;
    private String bgColor;


    

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

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getGroupPhotoPath() {
        return groupPhotoPath;
    }

    public void setGroupPhotoPath(String groupPhotoPath) {
        this.groupPhotoPath = groupPhotoPath;
    }

    public boolean isUserd() {
        return isUserd;
    }

    public void setUserd(boolean userd) {
        isUserd = userd;
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

    public boolean getIsUserd() {
        return this.isUserd;
    }

    public void setIsUserd(boolean isUserd) {
        this.isUserd = isUserd;
    }

    public int getGroupLocalPhotoPath() {
        return this.groupLocalPhotoPath;
    }

    public void setGroupLocalPhotoPath(int groupLocalPhotoPath) {
        this.groupLocalPhotoPath = groupLocalPhotoPath;
    }
}
