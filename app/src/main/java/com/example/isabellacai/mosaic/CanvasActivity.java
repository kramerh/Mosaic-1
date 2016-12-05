package com.example.isabellacai.mosaic;

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
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import stanford.androidlib.SimpleActivity;

import static com.example.isabellacai.mosaic.R.id.activity_canvas;

public class CanvasActivity extends Activity {

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
            // TODO Auto-generated method stub

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
        Drawable normalShape = getResources().getDrawable(R.drawable.sample_2, getTheme());
        Drawable targetShape = getResources().getDrawable(R.drawable.sample_0, getTheme());

        @Override
        public boolean onDrag(View v, DragEvent event) {

            // Handles each of the expected events
            switch (event.getAction()) {

                //signal for the start of a drag and drop operation.
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing (ADD INFO POP UP?????)
                    break;

                //the drag point has entered the bounding box of the View
                case DragEvent.ACTION_DRAG_ENTERED:
                    //v.setBackground(targetShape);   //change the shape of the view
                    break;

                //the user has moved the drag shadow outside the bounding box of the View
                case DragEvent.ACTION_DRAG_EXITED:
                    //v.setBackground(R.drawable.);   //change the shape of the view back to normal
                    break;

                //drag shadow has been released,the drag point is within the bounding box of the View
                case DragEvent.ACTION_DROP:
                    // if the view is the bottomlinear, we accept the drag item
                    if(v == findViewById(R.id.canvas)) {
                        ImageView view = (ImageView) event.getLocalState();
                        ViewGroup viewgroup = (ViewGroup) view.getParent();
                        viewgroup.removeView(view);
                        //change the text
                        Toast.makeText(getApplicationContext(), "" + event.getX() + ", " + event.getY() + " | " + view.getWidth() + ", " + view.getHeight(),
                                Toast.LENGTH_LONG).show();
//                        TextView text = (TextView) v.findViewById(R.id.text);
//                        text.setText("The item is dropped");
                        //FrameLayout containView = (FrameLayout) v;
                        FrameLayout containView = (FrameLayout) findViewById(R.id.canvas);
                        //ImageView darg = (ImageView) event.getLocalState();
                        view.setScaleX(100);
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                                view.getWidth(), view.getHeight());
                        params.leftMargin =  0;//(int)event.getX();
                        params.rightMargin = 0;//(int)event.getY();

                        containView.addView(view,params);
                        view.setVisibility(View.VISIBLE);


                    } else {
                        View view = (View) event.getLocalState();
                        view.setVisibility(View.VISIBLE);
                        Context context = getApplicationContext();
                        Toast.makeText(context, "You can't drop the image here",
                                Toast.LENGTH_LONG).show();
                        break;
                    }
                    break;

                //the drag and drop operation has concluded.
                case DragEvent.ACTION_DRAG_ENDED:
                    //v.setBackground(normalShape);   //go back to normal shape
                    //Toast.makeText(getApplicationContext(), "drag ended",
                      //      Toast.LENGTH_LONG).show();
                default:
                    break;
            }
            return true;
        }
    }
}