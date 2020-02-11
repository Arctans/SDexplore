package com.example.parcelservice;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {

    private int id;
    private String name;
    private String pass;

    public Person(Integer id,String name,String pass){
        super();
        this.id = id;
        this.name = name;
        this.pass = pass;
    }
    public int hashCode()
    {
        return 1;
    }



    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        //if(!(obj instanceof(Person)))
        if (getClass() != obj.getClass())
            return false;

        Person other = (Person) obj;
        if(this.name.equals(other.name))
            return true;
        else
            return false;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.pass);
    }
    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source.readInt(),source.readString(),source.readString());
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
