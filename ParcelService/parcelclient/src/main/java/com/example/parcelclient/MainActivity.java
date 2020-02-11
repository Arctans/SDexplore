package com.example.parcelclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.parcelservice.IPet;
import com.example.parcelservice.Person;
import com.example.parcelservice.Pet;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG =MainActivity.class.getSimpleName();
    private ServiceConnection serviceConnection;
    private IPet petService;
    private EditText editText;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.show);
        editText = findViewById(R.id.name);

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                petService = IPet.Stub.asInterface(service);
                Log.d(TAG, "onServiceConnected: "+petService);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                petService = null;
            }
        };

        (findViewById(R.id.get)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                if(name != null ){
                    try {
                        Log.d(TAG, "onClick:name "+name);
                        List<Pet> petList = petService.getPets(new Person(1,name,name));
                        ArrayAdapter<Pet> adapter = new ArrayAdapter<>(MainActivity.this,
                                    android.R.layout.simple_list_item_1,petList);
//                        Log.d(TAG, "onClick: "+petList);
                        listView.setAdapter(adapter);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        startBinderService();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unbindService(serviceConnection);
    }

    private void startBinderService(){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.ComplexService");
        intent.setPackage("com.example.parcelservice");
        Log.d(TAG, "startBinderService n: ");
        boolean flag = bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
        Log.d(TAG, "startBinderService: "+flag);
    }
}
