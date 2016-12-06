package com.example.isabellacai.mosaic;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.drawable.Drawable;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import stanford.androidlib.SimpleActivity;

import static android.R.attr.id;
import static com.example.isabellacai.mosaic.R.id.activity_canvas;
import static com.example.isabellacai.mosaic.R.id.textView;

public class CanvasActivity extends SimpleActivity {

    private ImageView myImage1;
    private ImageView myImage2;
    private ImageView myImage3;
    private static final String IMAGEVIEW_TAG = "Piece";
    private File directory;
    private ScaleGestureDetector mSGD;
    private Matrix mMatrix = new Matrix();
    private float mScale = 1f;

    private ImageView currentImg;
    private boolean inPinch = false;
/** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);
        myImage1 = (ImageView)findViewById(R.id.image1);
        myImage2 = (ImageView)findViewById(R.id.image2);
        myImage3 = (ImageView)findViewById(R.id.image3);
        // Sets the tag
        myImage1.setTag(IMAGEVIEW_TAG);
        myImage2.setTag(IMAGEVIEW_TAG);
        myImage3.setTag(IMAGEVIEW_TAG);

        // set the listener to the dragging data
        myImage1.setOnLongClickListener(new MyClickListener());
        myImage2.setOnLongClickListener(new MyClickListener());
        myImage3.setOnLongClickListener(new MyClickListener());

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        mSGD = new ScaleGestureDetector(this, new ScaleListener());
        findViewById(R.id.options).setOnDragListener(new MyDragListener());
        findViewById(R.id.canvas).setOnDragListener(new MyDragListener());
        myImage1.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                currentImg = (ImageView) v;
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_UP:
                        inPinch = false;
                        int width = (int) (myImage1.getWidth()*mScale);
                        int height = (int) (myImage1.getHeight()*mScale);
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
                        myImage1.setLayoutParams(params);
                        log("after scaling: " + myImage1.getWidth() + ", " + myImage1.getHeight());
                    default:
                        break;
                }
                if(event.getPointerCount() > 1) {
                    inPinch = true;
                    mSGD.onTouchEvent(event);
                    return true;
                }


//                if (event.getAction() == MotionEvent.ACTION_UP){
//                    log("up");
//                    inPinch = false;
//                }
                return false;
            }
        });

    }


    private class ScaleListener extends ScaleGestureDetector.
            SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScale *= detector.getScaleFactor();
            mScale = Math.max(0.1f, Math.min(mScale, 5.0f));
            mMatrix.setScale(mScale, mScale);
            myImage1.setImageMatrix(mMatrix);
            return true;
        }

    }

    private void saveImage(Bitmap bm, String filename){
        filename = filename + ".jpg";
        // Create imageDir
        File path=new File(directory, filename);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
            //toast("success");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //moved to saveActivity
//        GlobalVariables.getInstance().mosaics.add(new Mosaic(filename, "new creator", "just now", R.drawable.voyage, "Jules", "Voyage"));
//        GlobalVariables.getInstance().mosaicNumber++;
    }

    public void saveBitmap(View view) {
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.canvas);
        layout.setDrawingCacheEnabled(true);
        Bitmap bm = Bitmap.createBitmap(layout.getDrawingCache());
        layout.setDrawingCacheEnabled(false);
        String filename = "m" + GlobalVariables.getInstance().mosaicNumber;
        saveImage(bm, filename);
        Intent intent = new Intent(CanvasActivity.this, SaveActivity.class);
        intent.putExtra("filename", filename);
        startActivity(intent);
    }

    private final class MyClickListener implements OnLongClickListener {

                // called when the item is long-clicked
        @Override
        public boolean onLongClick(View view) {
            if (!inPinch) {
                // create it from the object's tag
                ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);
                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            }
            return false;
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
//                   // v.setBackground(getResources().getDrawable(R.drawable.sample_0));
//                    //v.setBackgroundColor(Color.BLUE); this is if it exits anything
//                    break;

                //drag shadow has been released,the drag point is within the bounding box of the View
                case DragEvent.ACTION_DROP:
                    View canvas = findViewById(R.id.canvas);
                    if(v == canvas) {
                        float canvasX = getResources().getDimension(R.dimen.activity_horizontal_margin);
                        float canvasY = getResources().getDimension(R.dimen.activity_horizontal_margin);
                        ImageView img = (ImageView) event.getLocalState();
                        ViewGroup viewgroup = (ViewGroup) img.getParent();
                        viewgroup.removeView(img);
                        RelativeLayout containView = (RelativeLayout) v;
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                              img.getWidth(), img.getHeight());
                        containView.addView(img,params);
                        float xOrigin = event.getX() - (img.getWidth()/2);
                        float yOrigin = event.getY() - (img.getHeight()/2);
                        if (xOrigin < 0)
                            img.setX(0);
                        else if (xOrigin + img.getWidth()> canvas.getWidth())
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
                    } else {
                        View view = (View) event.getLocalState();
                        view.setVisibility(View.VISIBLE);
                        toast("You can't drop this here", 1);
                        break;
                    }
                    break;

                //the drag and drop operation has concluded.
                case DragEvent.ACTION_DRAG_ENDED:
                        View view = (View) event.getLocalState();
                        view.setVisibility(View.VISIBLE);
                        if (event.getResult() == false){
                            toast("You can't drop this here");
                        }
                default:
                    break;
            }
            return true;
        }
    }
}