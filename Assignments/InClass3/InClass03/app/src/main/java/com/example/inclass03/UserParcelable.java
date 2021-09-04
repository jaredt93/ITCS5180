package com.example.inclass03;

import android.os.Parcel;
import android.os.Parcelable;

// intents data passing using Parcelable objects
public class UserParcelable implements Parcelable {
    String name;
    int age;

    public UserParcelable(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public UserParcelable() {

    }

    protected UserParcelable(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    public static final Creator<UserParcelable> CREATOR = new Creator<UserParcelable>() {
        @Override
        public UserParcelable createFromParcel(Parcel in) {
            return new UserParcelable(in);
        }

        @Override
        public UserParcelable[] newArray(int size) {
            return new UserParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeInt(this.age);
    }
}
