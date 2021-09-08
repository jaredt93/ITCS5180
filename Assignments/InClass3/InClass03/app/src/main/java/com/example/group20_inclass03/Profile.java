package com.example.group20_inclass03;

import android.os.Parcel;
import android.os.Parcelable;

public class Profile implements Parcelable {

    String name;
    String email;
    String id;
    String department;

    public Profile(String name, String email, String id, String department) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.department = department;
    }

    public Profile() {

    }

    protected Profile(Parcel in) {
        name = in.readString();
        email = in.readString();
        id = in.readString();
        department = in.readString();
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(id);
        parcel.writeString(department);
    }
}
