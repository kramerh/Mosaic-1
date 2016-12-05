package com.example.isabellacai.mosaic;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Xml;
import android.view.DragEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import stanford.androidlib.SimpleActivity;

import static android.R.attr.id;
import static com.example.isabellacai.mosaic.R.id.activity_canvas;
import static com.example.isabellacai.mosaic.R.id.textView;

public class CanvasActivity extends SimpleActivity {

    private ImageView myImage;
    private static final String IMAGEVIEW_TAG = "Piece";

/** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);
        myImage = (ImageView)findViewById(R.id.image);
        // Sets the tag
        myImage.setTag(IMAGEVIEW_TAG);

        // set the listener to the dragging data
        myImage.setOnLongClickListener(new MyClickListener());

        findViewById(R.id.options).setOnDragListener(new MyDragListener());
        findViewById(R.id.canvas).setOnDragListener(new MyDragListener());

    }

    private final class MyClickListener implements OnLongClickListener {

                // called when the item is long-clicked
        @Override
        public boolean onLongClick(View view) {
            // create it from the object's tag
            ClipData.Item item = new ClipData.Item((CharSequence)view.getTag());

            String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
            ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);
            DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data,shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
            return true;
        }
    }



    class MyDragListener implements OnDragListener {
        private boolean withinCanvas = true;

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
                        toast("dropped - You can't drop this here", 1);
                        break;
                    }
                    break;

                //the drag and drop operation has concluded.
                case DragEvent.ACTION_DRAG_ENDED:
                        View view = (View) event.getLocalState();
                        view.setVisibility(View.VISIBLE);
                        toast("ended - getResult is " + event.getResult(), 1);

                default:
                    break;
            }
            return true;
        }
    }
}