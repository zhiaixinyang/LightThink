package com.example.greatbook.middle.model;

/**
 * Created by MDove on 2017/8/12.
 */

import java.util.Date;

/**
 * 用于适配粘性Item
 */
public class LocalRecordRLV {
    public Long id;
    public Date time;
    public String title;
    public String content;
    //对应的账号信息，用于网络同步
    public String belongId;
    //0：默认（总的）；1：代表鸡汤；2：代表短句；3：代表段子
    public int type;
    //自己的分组id
    public Long groupId;
    public String groupTitle;
    public int iconLabelId;
}
