package com.example.sdbrowser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ItemViewHolder> {
    private static final String TAG=MyRecyclerViewAdapter.class.getSimpleName();
    private File[] files;
    private Context context;
    private static final String ImageAction = "android.intent.action.show.Image";
    public MyRecyclerViewAdapter(File[] files,Context context){
        this.files = files;
        this.context = context;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,null);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view,context);

        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.fileName.setText(files[position].getName());
        if(files[position].isDirectory()){
            holder.imageView.setImageResource(R.drawable.format_folder);
        }else{
            holder.imageView.setImageResource(R.drawable.format_text);
        }
    }

    @Override
    public int getItemCount() {
        return files.length;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "ItemViewHolder";
        public TextView fileName;
        public ImageView imageView;
        private MainActivity activity;
        public ItemViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            activity=(MainActivity)context;
            fileName = itemView.findViewById(R.id.filename);
            imageView = itemView.findViewById(R.id.imageType);


            itemView.setOnClickListener(view->{
                int position = getAdapterPosition();
                Log.d(TAG, "ItemViewHolder: "+position);

                if(files[position].isDirectory()){
                    File[] tmp = files[position].listFiles();
                    activity.setCurrent(tmp,files[position]);
                    activity.inflateRecyclerView(tmp);
                }else if (files[position].isFile()){
                    String mFileName = files[position].getName();
                    String extName = mFileName.substring(mFileName.lastIndexOf('.')+1);
                    Log.d(TAG, "ItemViewHolder: extname "+extName);
                    if(extName.equals("jpg")||extName.equals("png")||extName.equals("gif")){
                        Intent intent = new Intent();
                        intent.putExtra("filename",files[position].getAbsolutePath());
                        intent.setAction(ImageAction);
                        activity.startActivityForResult(intent,0x33);
                    }

                    return ;
                }
            });
        }
    }
}
