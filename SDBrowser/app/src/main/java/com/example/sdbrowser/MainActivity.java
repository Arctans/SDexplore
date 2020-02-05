package com.example.sdbrowser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int PERMISSION_READ = 0x123;
    private static final int PERMISSION_WRITE =0x456;
    private RecyclerView recyclerView;
    private TextView path,name;
    private ImageView imageView;
    private Button bt;
    private File[] currentFiles;
    private File currentParent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myRequestPermission();
        initUI();

    }
    private void initUI(){
        bt = findViewById(R.id.bt);
        path = findViewById(R.id.path);

        recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(new MyRecyclerViewAdapter());

    }
    private void myRequestPermission(){
        this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                MainActivity.PERMISSION_WRITE);
        this.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                            MainActivity.PERMISSION_READ);

    }
    public void setCurrent(File[] files,File currentParent){
        this.currentParent = currentParent;
        currentFiles = files;
    }
    public void inflateRecyclerView(File[] files){
        Log.d(TAG, "inflateRecyclerView: ");
        recyclerView.setAdapter(new MyRecyclerViewAdapter(files,MainActivity.this));
        path.setText(currentParent.getPath());
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MainActivity.PERMISSION_WRITE){
            Log.d(TAG, "onRequestPermissionsResult: gra "+grantResults.length);
            if((grantResults != null) && (grantResults[0]==PackageManager.PERMISSION_GRANTED)){
                File root = new File(Environment.getExternalStorageDirectory().getPath());
                if(root.exists()){
                    currentParent = root;
                    currentFiles = root.listFiles();
//                    for(int i=0;i<currentFiles.length;i++){
//                        Log.d(TAG, "onRequestPermissionsResult: "+currentFiles[i].getName());
//                    }
                    inflateRecyclerView(currentFiles);
                }

            }
        }
    }

    public void myOnclick(View view){
        int id = view.getId();
        switch (id){
            case R.id.bt:

                try{
                    if(!currentParent.getCanonicalPath().equals(Environment.
                            getExternalStorageDirectory().getPath())){
                        currentParent = currentParent.getParentFile();
                        currentFiles = currentParent.listFiles();
                        inflateRecyclerView(currentFiles);
                    }
                }catch (IOException e){

                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: ");
        inflateRecyclerView(currentFiles);
    }
}
