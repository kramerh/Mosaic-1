package com.example.isabellacai.mosaic;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SaveActivity extends AppCompatActivity {
    private String filename = "";
    private Bitmap bm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        TextView title = (TextView)findViewById(R.id.saveTitle);
        Button save = (Button)findViewById(R.id.saveButton);
        Button cancel = (Button)findViewById(R.id.cancelButton);
        EditText name = (EditText) findViewById(R.id.name);
        EditText comments = (EditText) findViewById(R.id.comments);

        Typeface source = Typeface.createFromAsset(getAssets(), "fonts/sourcesanspro.ttf");
        Typeface sourcebold = Typeface.createFromAsset(getAssets(), "fonts/sourcesansprobold.ttf");
        Typeface open = Typeface.createFromAsset(getAssets(), "fonts/opensansregular.ttf");
        title.setTypeface(sourcebold);
        save.setTypeface(sourcebold);
        cancel.setTypeface(sourcebold);
        name.setTypeface(open);
        comments.setTypeface(open);


        Intent intent = getIntent();
        filename = intent.getStringExtra("filename");

        ImageView img = (ImageView)findViewById(R.id.toSave);
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File f=new File(directory, filename);
        Picasso.with(this).load(f).into(img);
    }

    public void goHome(View view) {
        //put something here for the alert
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to cancel without saving?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        //bm.recycle();
                        Intent intent = new Intent(SaveActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing - return
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void goBackToCanvas(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to leave without saving?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(SaveActivity.this, CanvasActivity.class);
                        startActivity(intent);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing - return
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void saveAndReturn(View view) {
        //bm.recycle();
        EditText name = (EditText)findViewById(R.id.name);
        String typedInName = name.getText().toString();
        if (typedInName.equals("")) typedInName = "Anonymous";
        DateFormat df = new SimpleDateFormat("MMM d, yyyy", Locale.US);
        String date = df.format(Calendar.getInstance().getTime());
        GlobalVariables.getInstance().mosaics.add(0, new Mosaic(filename, typedInName, date));
        GlobalVariables.getInstance().mosaicNumber++;
        GlobalVariables.getInstance().startOfInitials++;
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("saved", true);
        startActivity(intent);
    }
}
