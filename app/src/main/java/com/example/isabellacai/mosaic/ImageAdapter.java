package com.example.isabellacai.mosaic;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by isabellacai on 11/27/16.
 */

//change this so that it gets images from the arraylist

class ImageAdapter extends BaseAdapter {
    private Context mContext;

    ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return GlobalVariables.getInstance().mosaics.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) { //if we want to customize what returns on click
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.WRAP_CONTENT, GridView.LayoutParams.WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(10, 10, 10, 10);
            imageView.setAdjustViewBounds(true);
            imageView.setMaxHeight(400);
            imageView.setMaxWidth(400);
        } else {
            imageView = (ImageView) convertView;
        }
        //CHECK IF THIS WORKS
        Bitmap bm = null;
        if (position < GlobalVariables.getInstance().initDrawables.length){
            bm = BitmapFactory.decodeResource(mContext.getResources(), GlobalVariables.getInstance().initDrawables[position]);
            //check if file exists already???
            saveImage(bm, "m"+position);
        } else {
            try {
                ContextWrapper cw = new ContextWrapper((Application)mContext.getApplicationContext());
                File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
                File f=new File(directory, "m"+position+".jpg");
                bm = BitmapFactory.decodeStream(new FileInputStream(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        imageView.setImageBitmap(bm);


        //imageView.setImageResource(GlobalVariables.getInstance().mosaics.get(position).getMosaicSource());
        return imageView;
    }

    private void saveImage(Bitmap bm, String filename){
        ContextWrapper cw = new ContextWrapper((Application)mContext.getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        filename = filename + ".jpg";
        // Create imageDir
        File path=new File(directory, filename);
        if (!path.exists()) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(path);
                // Use the compress method on the BitMap object to write image to the OutputStream
                bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }




        }