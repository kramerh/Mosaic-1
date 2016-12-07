package com.example.isabellacai.mosaic;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
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
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/sourcesanspro.ttf");
        txt.setTypeface(font);
        btn.setTypeface(font);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        Intent in = getIntent();
        boolean saved = false;
        if (in != null)
            saved = in.getBooleanExtra("saved", false);
        if(saved) Toast.makeText(MainActivity.this, "Mosaic saved successfully!", Toast.LENGTH_LONG).show();
    }

    public void goToDetail(View view) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        startActivity(intent);
    }
}
