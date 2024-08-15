package com.gc.expressOL.data;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.gc.expressOL.bean.UserBean;

/**
 * Created by gc on 2017/12/26.
 */

public class SpHelper {

    public static final String TAG_SP_STATE = "state";
    public static final String TAG_SP_NAME = "name";
    public static final String TAG_SP_ORDER_NUM = "orderNum";
    public static final String TAG_SP_ORDER_DATA = "orderDATA";


    private static final String TAG_SP_USER = "userData";

    public String getCurrentAccount() {
        UserBean userBean = GsonUtils.fromJson(SPStaticUtils.getString(TAG_SP_USER), UserBean.class);
        return ObjectUtils.isEmpty(userBean) ? "" : userBean.getNickname();
    }

    public void insetData(String nickname, String name, String phone) {
        UserBean userBean = new UserBean(nickname, name, phone);
        SPStaticUtils.put(TAG_SP_USER, GsonUtils.toJson(userBean));
    }

    public void deleteData() {
        SPStaticUtils.remove(TAG_SP_USER);
    }
}
