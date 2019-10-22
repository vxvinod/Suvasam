package com.example.suvasam.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Events implements Parcelable {

    public int id;
    public String name;
    public String imageUrl;
    public String description;
    public Boolean fav;

//    public Events(String name, String date) {
//        this.name = name;
//        this.date = date;
//    }

    protected Events(Parcel in) {
        id = in.readInt();
        name = in.readString();
        imageUrl = in.readString();
        description = in.readString();
        byte tmpFav = in.readByte();
        fav = tmpFav == 0 ? null : tmpFav == 1;
        date = in.readString();
    }

    public static final Creator<Events> CREATOR = new Creator<Events>() {
        @Override
        public Events createFromParcel(Parcel in) {
            return new Events(in);
        }

        @Override
        public Events[] newArray(int size) {
            return new Events[size];
        }
    };

    public Boolean getFav() {
        return fav;
    }

    public void setFav(Boolean fav) {
        this.fav = fav;
    }

    public Events() {

    }

    public String date;

    public Events(int id, String name, String imageUrl, String description, String date, Boolean fav) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.date = date;
        this.fav = fav;
    }

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(imageUrl);
        dest.writeString(description);
        dest.writeByte((byte) (fav == null ? 0 : fav ? 1 : 2));
        dest.writeString(date);
    }
}
