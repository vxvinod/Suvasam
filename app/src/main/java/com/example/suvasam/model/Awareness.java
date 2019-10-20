package com.example.suvasam.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Awareness implements Parcelable {

    private String title;
    private String imageUrl;
    private int id;
    private String description;
    private String date;

    public Awareness() {

    }

    public Awareness(String title, String imageUrl, int id, String description, String date) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.id = id;
        this.description = description;
        this.date = date;
    }

    protected Awareness(Parcel in) {
        title = in.readString();
        imageUrl = in.readString();
        id = in.readInt();
        description = in.readString();
        date = in.readString();
    }

    public static final Creator<Awareness> CREATOR = new Creator<Awareness>() {
        @Override
        public Awareness createFromParcel(Parcel in) {
            return new Awareness(in);
        }

        @Override
        public Awareness[] newArray(int size) {
            return new Awareness[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(imageUrl);
        dest.writeInt(id);
        dest.writeString(description);
        dest.writeString(date);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


}
