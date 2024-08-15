package com.gc.expressOL.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/15
 */
public class OrderListBean implements Parcelable {

    /**
     * 订单号
     */
    String auto_nums;
    /**
     * 收货者姓名
     */
    String names;
    /**
     * 收货者电话
     */
    String num;
    /**
     * 快递公司
     */
    String exc;
    /**
     * 取货号
     */
    String thn;
    /**
     * 送达地点
     */
    String des;
    /**
     * pay
     */
    String pay;
    /**
     * 送达时间
     */
    String time;
    /**
     * 接单者
     */
    String rc;
    /**
     * 发单者
     */
    String rs;
    /**
     * 订单状态
     */
    String stm;

    public OrderListBean() {
    }

    public OrderListBean(String auto_nums, String names, String num, String exc, String thn, String des, String pay, String time, String rc, String rs, String stm) {
        this.auto_nums = auto_nums;
        this.names = names;
        this.num = num;
        this.exc = exc;
        this.thn = thn;
        this.des = des;
        this.pay = pay;
        this.time = time;
        this.rc = rc;
        this.rs = rs;
        this.stm = stm;
    }

    protected OrderListBean(Parcel in) {
        auto_nums = in.readString();
        names = in.readString();
        num = in.readString();
        exc = in.readString();
        thn = in.readString();
        des = in.readString();
        pay = in.readString();
        time = in.readString();
        rc = in.readString();
        rs = in.readString();
        stm = in.readString();
    }

    public static final Creator<OrderListBean> CREATOR = new Creator<OrderListBean>() {
        @Override
        public OrderListBean createFromParcel(Parcel in) {
            return new OrderListBean(in);
        }

        @Override
        public OrderListBean[] newArray(int size) {
            return new OrderListBean[size];
        }
    };

    public String getAuto_nums() {
        return auto_nums;
    }

    public void setAuto_nums(String auto_nums) {
        this.auto_nums = auto_nums;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getExc() {
        return exc;
    }

    public void setExc(String exc) {
        this.exc = exc;
    }

    public String getThn() {
        return thn;
    }

    public void setThn(String thn) {
        this.thn = thn;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public String getRs() {
        return rs;
    }

    public void setRs(String rs) {
        this.rs = rs;
    }

    public String getStm() {
        return stm;
    }

    public void setStm(String stm) {
        this.stm = stm;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(auto_nums);
        parcel.writeString(names);
        parcel.writeString(num);
        parcel.writeString(exc);
        parcel.writeString(thn);
        parcel.writeString(des);
        parcel.writeString(pay);
        parcel.writeString(time);
        parcel.writeString(rc);
        parcel.writeString(rs);
        parcel.writeString(stm);
    }
}
