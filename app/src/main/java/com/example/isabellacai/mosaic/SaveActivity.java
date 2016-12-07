package com.example.isabellacai.mosaic;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SaveActivity extends AppCompatActivity {
    private String filename = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        Intent intent = getIntent();
        filename = intent.getStringExtra("filename");
        try {
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File f=new File(directory, filename+".jpg");
            Bitmap bm = BitmapFactory.decodeStream(new FileInputStream(f));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveAndReturn(View view) {
        EditText name = (EditText)findViewById(R.id.name);
        String typedInName = name.getText().toString();
        if (typedInName.equals("")) typedInName = "Anonymous";
        DateFormat df = new SimpleDateFormat("MMM d, yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        GlobalVariables.getInstance().mosaics.add(new Mosaic(filename, typedInName, date));
        GlobalVariables.getInstance().mosaicNumber++;
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("saved", true);
        startActivity(intent);
    }
}
