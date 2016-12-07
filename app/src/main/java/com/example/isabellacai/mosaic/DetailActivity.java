package com.example.isabellacai.mosaic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    public void goToCreate(View view) {
        Intent intent = new Intent(DetailActivity.this, CanvasActivity.class);
        startActivity(intent);
    }

    public void goBack(View view) {
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
