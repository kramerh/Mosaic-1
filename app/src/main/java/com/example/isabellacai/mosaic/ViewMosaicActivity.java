package com.example.isabellacai.mosaic;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import stanford.androidlib.SimpleActivity;

public class ViewMosaicActivity extends SimpleActivity {
    private Bitmap bm = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mosaic);
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", -1);
        Mosaic chosenMosaic = GlobalVariables.getInstance().mosaics.get(position);
        ImageView mosaicImg = findImageView(R.id.MosaicImage);
        if (position < GlobalVariables.getInstance().startOfInitials) {
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File f = new File(directory, chosenMosaic.getMosaicSource());
            bm = BitmapFactory.decodeFile(f.getPath());
            mosaicImg.setImageBitmap(bm);
        } else {
            mosaicImg.setImageDrawable(getResources().getDrawable(GlobalVariables.getInstance().initDrawables[position - GlobalVariables.getInstance().startOfInitials]));
        }
        TextView creator = findTextView(R.id.mosaicAuthor);
        TextView date = findTextView(R.id.dateCreated);
        Typeface source = Typeface.createFromAsset(getAssets(), "fonts/sourcesanspro.ttf");
        creator.setTypeface(source);
        date.setTypeface(source);
        creator.setText("Mosaic created by " + chosenMosaic.getCreator());
        date.setText(chosenMosaic.getTimestamp());
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
