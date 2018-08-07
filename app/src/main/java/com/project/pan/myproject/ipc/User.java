package com.project.pan.myproject.developArt.ipc;

import android.content.Intent;
import android.os.Binder;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author: panrongfu
 * @date: 2018/7/26 15:25
 * @describe:
 */

public class User implements Parcelable {

    String name;
    int age;
    int userId;

    public User() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
        dest.writeInt(this.userId);
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
        this.userId = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
