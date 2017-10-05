package com.example.greatbook.model.leancloud;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;

/**
 * Created by MBENBEN on 2016/4/16.
 */
public class User extends AVUser {
    private String name;
    private String signature;
    private AVFile avatar;
    private int charm;
    private int fans;
    private int money;
    private int words;
    private int level;

    public int getLevel() {
        return getInt("level");
    }

    public void setLevel(int level) {
        put("level", level);
    }

    public int getWords() {
        return getInt("words");
    }

    public void setWords(int words) {
        put("words", words);
    }

    public int getCharm() {
        return getInt("charm");
    }

    public void setCharm(int charm) {
        put("charm", charm);
    }

    public int getFans() {
        return getInt("fans");
    }

    public void setFans(int fans) {
        put("fans", fans);
    }

    public int getMoney() {
        return getInt("money");
    }

    public void setMoney(int money) {
        put("money", money);
    }

    public void setName(String name) {
        put("name", name);
    }

    public String getName() {

        return getString("name");
    }

    public AVFile getAvatar() {
        return getAVFile("avatar");
    }

    public void setAvatar(AVFile avatar) {
        put("avatar", avatar);
    }


    public String getSignature() {
        return getString("signature");
    }

    public void setSignature(String signature) {
        put("signature", signature);
    }
}
