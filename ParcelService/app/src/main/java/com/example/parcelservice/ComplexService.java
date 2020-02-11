package com.example.parcelservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;

public class ComplexService extends Service {
    private static final String TAG = "ComplexService";
    private PetBinder petBinder;
    private static Map<Person,List<Pet>> pets = new HashMap<Person,List<Pet>>();

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
    }

    static {
        ArrayList<Pet>  list1 = new ArrayList<Pet>();
        list1.add(new Pet("dog1",22));
        list1.add(new Pet("cat1",22.3));
        Person p1 = new Person(1,"zhangshan","zhangshan");
        pets.put(p1, list1);

        ArrayList<Pet>  list2 = new ArrayList<Pet>();
        list2.add(new Pet("dog2",17));
        list2.add(new Pet("cat2",5.3));
        Person p2 = new Person(2,"xiaoli","xiaoli");
        pets.put(p2, list2);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return new PetBinder();
    }

    public class PetBinder extends IPet.Stub{

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public List<Pet> getPets(Person owner) throws RemoteException {
            Log.d(TAG, "getPets: "+owner.toString());
            return pets.get(owner);
        }
    }
}
