package com.example.greatbook.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by Mdove on 17/9/15.
 */

public class Picture implements Parcelable, Comparable<Picture> {
    public String path;
    public Long dateAdded = 0l;

    protected Picture(Parcel in) {
        path = in.readString();
    }

    public Picture() {
    }

    public static final Creator<Picture> CREATOR = new Creator<Picture>() {
        @Override
        public Picture createFromParcel(Parcel in) {
            return new Picture(in);
        }

        @Override
        public Picture[] newArray(int size) {
            return new Picture[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
    }

    @Override
    public int compareTo(@NonNull Picture o) {
        return o.dateAdded.compareTo(dateAdded);
    }
}
