package com.project.pan.myproject.ipc;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author: panrongfu
 * @date: 2018/7/26 17:12
 * @describe:
 */

public class Book implements Parcelable {
    public String author;
    public String price;
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.author);
        dest.writeString(this.price);
    }

    public Book() {
    }

    protected Book(Parcel in) {
        this.author = in.readString();
        this.price = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
