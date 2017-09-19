package com.example.greatbook.nethot.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by MDove on 2017/8/13.
 */

//用于发现页的顶部文集推荐的bean
public class DiscoveryTopGroup implements Serializable{
    public Date time;
    public String title;
    public String content;
    //对应的账号信息，用于网络同步
    public String belongId;
    //自己的分组
    public Long groupId;
    public String groupTitle;
    public int attentionNum;
    public String groupPhotoPath;
    public String avatarPath;
    public String objectId;
}
