package com.example.isabellacai.mosaic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import stanford.androidlib.SimpleActivity;

public class ViewMosaicActivity extends SimpleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mosaic);
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", -1);

        ImageView img = findImageView(R.id.MosaicImage);
        img.setImageResource(GlobalVariables.getInstance().mosaics.get(position).getMosaicSource());

        TextView author = findTextView(R.id.mosaicAuthor);
        TextView date = findTextView(R.id.dateCreated);

        author.setText("Mosaic created by: " + GlobalVariables.getInstance().mosaics.get(position).getCreator());
        date.setText(GlobalVariables.getInstance().mosaics.get(position).getTimestamp());


    }

    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
