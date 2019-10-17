package com.example.suvasam.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Donate implements Parcelable {
    private int id;
    private String name;
    private String donated;
    private int donationAmt;
    private long lat;
    private long lng;
    private int plantsCount;

    public Donate() {
    }

    protected Donate(Parcel in) {
        id = in.readInt();
        name = in.readString();
        donated = in.readString();
        donationAmt = in.readInt();
        lat = in.readLong();
        lng = in.readLong();
        plantsCount = in.readInt();
    }

    public static final Creator<Donate> CREATOR = new Creator<Donate>() {
        @Override
        public Donate createFromParcel(Parcel in) {
            return new Donate(in);
        }

        @Override
        public Donate[] newArray(int size) {
            return new Donate[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDonated() {
        return donated;
    }

    public void setDonated(String donated) {
        this.donated = donated;
    }

    public int getDonationAmt() {
        return donationAmt;
    }

    public void setDonationAmt(int donationAmt) {
        this.donationAmt = donationAmt;
    }

    public long getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public long getLng() {
        return lng;
    }

    public void setLng(long lng) {
        this.lng = lng;
    }

    public int getPlantsCount() {
        return plantsCount;
    }

    public void setPlantsCount(int plantsCount) {
        this.plantsCount = plantsCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(donated);
        dest.writeInt(donationAmt);
        dest.writeLong(lat);
        dest.writeLong(lng);
        dest.writeInt(plantsCount);

    }


}
