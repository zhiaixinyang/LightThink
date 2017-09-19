package com.example.greatbook.nethot.model;


import java.io.Serializable;
import java.util.Date;

/**
 * Created by MDove on 2017/8/14.
 */

public class DiscoveryRecord  implements Serializable{
    public String id;
    public Date time;
    public String title;
    public String content;
    //用于表示自己（评论时需要依赖）
    public String objectId;
    //对应的账号信息，用于网络同步
    public String belongId;
    //0：默认（总的）；1：代表鸡汤；2：代表短句；3：代表段子
    public int type;
    //自己的分组id
    public Long groupId;

    public int likeNum;
}
