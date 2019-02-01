package com.project.pan.myproject.ipc.customBinder;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author: panrongfu
 * @date: 2018/11/7 20:20
 * @describe:
 */

public class Book implements Parcelable {
    public Book() {
    }

    protected Book(Parcel in) {
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
