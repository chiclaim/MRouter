package com.chiclaim.modularization.business.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/7/27.
 */

public class Address implements Parcelable {

    private String province;
    private String city;
    private String detail;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.detail);
    }

    public Address() {
    }

    protected Address(Parcel in) {
        this.province = in.readString();
        this.city = in.readString();
        this.detail = in.readString();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    @Override
    public String toString() {
        return "Address{" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
