package com.example.parcelservice;

import android.os.Parcel;
import android.os.Parcelable;

public class Pet implements Parcelable {

    private String name;
    private double weight;

    public Pet(){

    }
    public Pet(String name,double weight){
        this.name = name;
        this.weight = weight;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeDouble(this.weight);
    }
    @Override
    public String toString()
    {
        return "Pet [name=" + name + ", weight=" + weight + "]";
    }

    public static final Creator<Pet> CREATOR = new Creator<Pet>(){

        @Override
        public Pet createFromParcel(Parcel source) {
            return new Pet(source.readString(),source.readDouble());
        }

        @Override
        public Pet[] newArray(int size) {
            return new Pet[size];
        }
    };
}
