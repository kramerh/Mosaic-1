package com.example.isabellacai.mosaic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.os.Bundle;
import android.content.ClipData;
import android.content.ClipDescription;
import android.view.DragEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import stanford.androidlib.SimpleActivity;

public class CanvasActivity extends SimpleActivity {

    private ImageView piece1;
    private ImageView piece2;
    private ImageView piece3;
    private ImageView piece4;
    private ImageView piece5;
    private ImageView piece6;
    private ImageView piece7;
    private ImageView myImage1;
    private static final String IMAGEVIEW_TAG = "Piece";
    private File directory;
    private ScaleGestureDetector mSGD;
    private Matrix mMatrix = new Matrix();
    private float mScale = 1f;

/** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_copy);
        TextView title = (TextView) findViewById(R.id.canvasText);
        Button done = findButton(R.id.canvasDoneButton);
        Typeface sourcebold = Typeface.createFromAsset(getAssets(), "fonts/sourcesansprobold.ttf");
        title.setTypeface(sourcebold);
        done.setTypeface(sourcebold);



        piece1 = (ImageView)findViewById(R.id.img1);
        piece2 = (ImageView)findViewById(R.id.img2);
        piece3 = (ImageView)findViewById(R.id.img3);
        piece4 = (ImageView)findViewById(R.id.img4);
        piece5 = (ImageView)findViewById(R.id.img5);
        piece6 = (ImageView)findViewById(R.id.img6);
        piece7 = (ImageView)findViewById(R.id.img7);

        // Sets the tag
        piece1.setTag(IMAGEVIEW_TAG);
        piece2.setTag(IMAGEVIEW_TAG);
        piece3.setTag(IMAGEVIEW_TAG);
        piece4.setTag(IMAGEVIEW_TAG);
        piece5.setTag(IMAGEVIEW_TAG);
        piece6.setTag(IMAGEVIEW_TAG);
        piece7.setTag(IMAGEVIEW_TAG);

        // set the listener to the dragging data
        piece1.setOnLongClickListener(new MyClickListener());
        piece2.setOnLongClickListener(new MyClickListener());
        piece3.setOnLongClickListener(new MyClickListener());
        piece4.setOnLongClickListener(new MyClickListener());
        piece5.setOnLongClickListener(new MyClickListener());
        piece6.setOnLongClickListener(new MyClickListener());
        piece7.setOnLongClickListener(new MyClickListener());


        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        findViewById(R.id.options).setOnDragListener(new MyDragListener());
        findViewById(R.id.box1).setOnDragListener(new MyDragListener());
        findViewById(R.id.box2).setOnDragListener(new MyDragListener());
        findViewById(R.id.box3).setOnDragListener(new MyDragListener());
        findViewById(R.id.box4).setOnDragListener(new MyDragListener());
        findViewById(R.id.box5).setOnDragListener(new MyDragListener());
        findViewById(R.id.box6).setOnDragListener(new MyDragListener());
        findViewById(R.id.box7).setOnDragListener(new MyDragListener());
        findViewById(R.id.canvas).setOnDragListener(new MyDragListener());

    }

    public void goHome(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to leave without saving?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent intent = new Intent(CanvasActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void goToDetail(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to leave without saving?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(CanvasActivity.this, DetailActivity.class);
                        startActivity(intent);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    private void saveImage(Bitmap bm, String filename){
        // Create imageDir
        File path=new File(directory, filename);
        FileOutputStream fos = null;
        try {
            if (path.exists()) path.delete();
                fos = new FileOutputStream(path);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bm.compress(Bitmap.CompressFormat.PNG, 40, fos);
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

    public void goToSave(View view) {
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.canvas);
        layout.setDrawingCacheEnabled(true);
        Bitmap bm = Bitmap.createBitmap(layout.getDrawingCache());
        layout.setDrawingCacheEnabled(false);
        String filename = "m" + GlobalVariables.getInstance().mosaicNumber +".jpeg";
        saveImage(bm, filename);
        Intent intent = new Intent(CanvasActivity.this, SaveActivity.class);
        intent.putExtra("filename", filename);
        bm.recycle();
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }

    private final class MyClickListener implements OnLongClickListener {

                // called when the item is long-clicked
        @Override
        public boolean onLongClick(View view) {
                // create it from the object's tag
                ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);
                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
        }
    }



    class MyDragListener implements OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            // Handles each of the expected events
            switch (event.getAction()) {

//                //signal for the start of a drag and drop operation.
//                case DragEvent.ACTION_DRAG_STARTED:
//                    break;
//                //the drag point has entered the bounding box of the View
//                case DragEvent.ACTION_DRAG_ENTERED:
//                    break;
//
//                //the user has moved the drag shadow outside the bounding box of the View
//                case DragEvent.ACTION_DRAG_EXITED:
                //TODO: add didactics here
//                   // v.setBackground(getResources().getDrawable(R.drawable.sample_0));
//                    //v.setBackgroundColor(Color.BLUE); this is if it exits anything
//                    break;

                //drag shadow has been released,the drag point is within the bounding box of the View
                case DragEvent.ACTION_DROP:
                    View canvas = findViewById(R.id.canvas);
                    View options = findViewById(R.id.options);
                    View box1 = findViewById(R.id.box1);
                    View box2 = findViewById(R.id.box2);
                    View box3 = findViewById(R.id.box3);
                    View box4 = findViewById(R.id.box4);
                    View box5 = findViewById(R.id.box5);
                    View box6 = findViewById(R.id.box6);
                    View box7 = findViewById(R.id.box7);

                    if (v == canvas) {
                        float canvasX = getResources().getDimension(R.dimen.activity_horizontal_margin);
                        float canvasY = getResources().getDimension(R.dimen.activity_horizontal_margin);
                        ImageView img = (ImageView) event.getLocalState();
                        //TODO: create copies of imgview to put multiple pieces on
                        RelativeLayout viewgroup = (RelativeLayout) img.getParent();
                        viewgroup.removeView(img);
                        RelativeLayout containView = (RelativeLayout) v;
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                img.getWidth(), img.getHeight());
                        containView.addView(img, params);
                        float xOrigin = event.getX() - (img.getWidth() / 2);
                        float yOrigin = event.getY() - (img.getHeight() / 2);
                        if (xOrigin < 0)
                            img.setX(0);
                        else if (xOrigin + img.getWidth() > canvas.getWidth())
                            img.setX(canvas.getWidth() - img.getWidth());
                        else
                            img.setX(xOrigin);
                        if (yOrigin < 0)
                            img.setY(0);
                        else if (yOrigin + img.getHeight() > canvas.getHeight())
                            img.setY(canvas.getHeight() - img.getHeight());
                        else
                            img.setY(yOrigin);
                        img.setVisibility(View.VISIBLE);
                    } else if((v == box1 || v == box2 || v == box3 || v == box4 || v == box5 || v == box6 || v == box7) && v != options){
                        //TODO: create copies of imgview to put multiple pieces on
                        ImageView img = (ImageView) event.getLocalState();
                        RelativeLayout viewgroup = (RelativeLayout) img.getParent();
                        viewgroup.removeView(img);
                        RelativeLayout containView = (RelativeLayout) v;
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                img.getWidth(), img.getHeight());
                        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                        containView.addView(img, params);
                        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        img.setX(0);
                        img.setY(0);
                        img.setVisibility(View.VISIBLE);
                        img.setLayoutParams(params);
                    } else {
                        View view = (View) event.getLocalState();
                        view.setVisibility(View.VISIBLE);
                        toast("You can't drop this here yo", 1);
                        break;
                    }
                    break;

                //the drag and drop operation has concluded.
                case DragEvent.ACTION_DRAG_ENDED:
                        View view = (View) event.getLocalState();
                        view.setVisibility(View.VISIBLE);
                        if (!event.getResult()){
                            toast("You can't drop this here");
                        }
                default:
                    break;
            }
            return true;
        }
    }
}