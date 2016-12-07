package com.example.isabellacai.mosaic;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import stanford.androidlib.SimpleActivity;

public class ViewMosaicActivity extends SimpleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mosaic);
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", -1);
        Mosaic chosenMosaic = GlobalVariables.getInstance().mosaics.get(position);
        ImageView mosaicImg = findImageView(R.id.MosaicImage);
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        try{
            File f=new File(directory, "m"+position+".jpg");
            Bitmap bm = BitmapFactory.decodeStream(new FileInputStream(f));
            mosaicImg.setImageBitmap(bm);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //mosaicImg.setImageResource(chosenMosaic.getMosaicSource());

//        ImageView origImg = findImageView(R.id.OriginalImage);
//        origImg.setImageResource(chosenMosaic.getOriginalSource());

        TextView creator = findTextView(R.id.mosaicAuthor);
        TextView date = findTextView(R.id.dateCreated);
//        TextView artist = findTextView(R.id.originalArtist);
//        TextView title = findTextView(R.id.originalTitle);

        creator.setText("Mosaic created by " + chosenMosaic.getCreator());
        date.setText(chosenMosaic.getTimestamp());
//        artist.setText(chosenMosaic.getOriginalArtist());
//        title.setText(chosenMosaic.getTitle());


    }

    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
