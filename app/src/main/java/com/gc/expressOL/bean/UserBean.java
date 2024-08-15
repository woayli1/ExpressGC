package com.gc.expressOL.bean;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/14
 */
public class UserBean {

    String nickname;
    String name;
    String phone;

    public UserBean() {
    }

    public UserBean(String nickname, String name, String phone) {
        this.nickname = nickname;
        this.name = name;
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
