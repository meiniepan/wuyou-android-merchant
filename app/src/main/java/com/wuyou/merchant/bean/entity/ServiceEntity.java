package com.wuyou.merchant.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by solang on 2018/2/8.
 */

public class ServiceEntity implements Parcelable {
    public String service_id;
    public String service_name;
    public float price;
    public String category_id;
    public String category_name;

    public ServiceEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.service_id);
        dest.writeString(this.service_name);
        dest.writeFloat(this.price);
        dest.writeString(this.category_id);
        dest.writeString(this.category_name);
    }

    protected ServiceEntity(Parcel in) {
        this.service_id = in.readString();
        this.service_name = in.readString();
        this.price = in.readFloat();
        this.category_id = in.readString();
        this.category_name = in.readString();
    }

    public static final Creator<ServiceEntity> CREATOR = new Creator<ServiceEntity>() {
        @Override
        public ServiceEntity createFromParcel(Parcel source) {
            return new ServiceEntity(source);
        }

        @Override
        public ServiceEntity[] newArray(int size) {
            return new ServiceEntity[size];
        }
    };
}
