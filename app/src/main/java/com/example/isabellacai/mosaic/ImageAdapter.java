package com.example.isabellacai.mosaic;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.squareup.picasso.Picasso;

import java.io.File;

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
            imageView.setLayoutParams(new GridView.LayoutParams(332, 300));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setBackgroundColor(Color.parseColor("#fff1ef"));
            imageView.setAdjustViewBounds(true);
            imageView.setMaxHeight(350);
            imageView.setMaxWidth(350);
        } else {
            imageView = (ImageView) convertView;
        }
        //CHECK IF THIS WORKS
        if (position >= GlobalVariables.getInstance().startOfInitials){
            Picasso.with(mContext).load(GlobalVariables.getInstance().initDrawables[position - GlobalVariables.getInstance().startOfInitials]).into(imageView);
        } else {
                ContextWrapper cw = new ContextWrapper((Application)mContext.getApplicationContext());
                File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
                File f=new File(directory, GlobalVariables.getInstance().mosaics.get(position).getMosaicSource());

                Bitmap bm = decodeSampledBitmapFromResource(f.getPath(), 332, 300);
                imageView.setImageBitmap(bm);
        }
        return imageView;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    public static Bitmap decodeSampledBitmapFromResource(String pathname,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathname, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathname, options);
    }

        }