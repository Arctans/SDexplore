// IPet.aidl
package com.example.parcelservice;
import com.example.parcelservice.Pet;
import com.example.parcelservice.Person;
// Declare any non-default types here with import statements

interface IPet {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
    List<Pet> getPets(in Person owner);
}
