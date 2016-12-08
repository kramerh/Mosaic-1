package com.example.isabellacai.mosaic;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        adapter = new ImageAdapter(this);
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(MainActivity.this, ViewMosaicActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        TextView txt = (TextView) findViewById(R.id.browseText);
        Button btn = (Button) findViewById(R.id.createButton);
        TextView title = (TextView) findViewById(R.id.title);
        TextView artist = (TextView) findViewById(R.id.artist);
        TextView didactic = (TextView) findViewById(R.id.didactic);
        Typeface sourceBoldItalic = Typeface.createFromAsset(getAssets(), "fonts/sourcesansprobolditalic.ttf");
        Typeface sourcebold = Typeface.createFromAsset(getAssets(), "fonts/sourcesansprobold.ttf");
        Typeface serif = Typeface.createFromAsset(getAssets(), "fonts/arapey-regular.ttf");

        txt.setTypeface(sourcebold); //bold
        btn.setTypeface(sourcebold); //maybe bold or semibold
        title.setTypeface(sourceBoldItalic);
        artist.setTypeface(sourcebold);
        didactic.setTypeface(serif);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent in = getIntent();
        boolean saved = false;
        if (in != null)
            saved = in.getBooleanExtra("saved", false);
        if(saved){
            adapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "Mosaic saved successfully!", Toast.LENGTH_LONG).show();
        }
    }

    public void goToDetail(View view) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        startActivity(intent);
    }
}
