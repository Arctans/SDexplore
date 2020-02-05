package com.example.sdbrowser;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class ImageShowActvity extends Activity {
    private static final String TAG = "ImageShowActvity";
    private static final String MainActivityAction = "android.intent.action.MAINACTIVITY";
    private String filename;
    private ImageView imageView;
    private int width ;
    private int height ;
    private GestureDetector detector;
    private Matrix matrix;
    private float currentScale = 1.0f;
    private Bitmap bitmap;
    private float prevDist;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_show);
        Intent intent = getIntent();
        filename = intent.getStringExtra("filename");


        detector = new GestureDetector(this,new MySimpleOnGestureListener());
        matrix = new Matrix();
        imageView = findViewById(R.id.show);
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        bitmap = BitmapFactory.decodeFile(filename);
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        imageView.setImageBitmap(bitmap);
    }
    public void imageClick(View v){
       int id = v.getId();
       switch (id){
           case R.id.back:
               Intent intent = new Intent();
               intent.setAction(MainActivityAction);
               startActivity(intent);
               break;
       }
    }
    private void zoomImage(float scale){
        matrix.reset();

        matrix.setScale(scale,scale,160f,200f);
        Bitmap bitmap2 = Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);
        imageView.setImageBitmap(bitmap2);
    }
    private float calSpace(MotionEvent event){
        float dist;
        float x = event.getX(0)-event.getX(1);
        float y = event.getY(0)-event.getY(0);
        return dist = (float) Math.sqrt(x*x+y*y);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: "+event.getPointerCount());
        if(event.getPointerCount()==2){
            switch (event.getActionMasked()){
                case MotionEvent.ACTION_POINTER_DOWN:
                    prevDist = calSpace(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    float curDist = calSpace(event);
                    zoomImage(curDist/prevDist);
                    prevDist=curDist;
                    break;
            }
        }else{
            detector.onTouchEvent(event);
        }
        return true;
    }

    public class MySimpleOnGestureListener extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d(TAG, "onFling: "+width+"heigit "+height+" velocityX "+velocityX);
            float vx = velocityX>4000?4000f:velocityX;
            vx = velocityX<-4000?-4000f:velocityX;
            currentScale += currentScale*vx/4000.0f;
            currentScale = currentScale > 0.01?currentScale:0.01f;
            matrix.reset();
//                matrix.setScale(10,10);
            matrix.setScale(currentScale,currentScale,160f,200f);
//                BitmapDrawable tmp = (BitmapDrawable)imageView.getDrawable();
//                if(!tmp.getBitmap().isRecycled()){
//                    tmp.getBitmap().recycle();
//                }
            Bitmap bitmap1 = Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);
            imageView.setImageBitmap(bitmap1);
            return true;
        }

    }

}
