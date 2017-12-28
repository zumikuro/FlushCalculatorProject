package com.example.aiko.flushcalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class FirstActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);


        final ImageView info = (ImageView) findViewById(R.id.imageView2);

       // info.setVisibility(View.INVISIBLE);

        final ImageButton infobt = (ImageButton) findViewById(R.id.infobt);

        infobt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (info.getVisibility() == View.INVISIBLE) {
                    info.setVisibility(View.VISIBLE);
                   // infobt.setBackgroundResource(R.drawable.back);

                } else if(info.getVisibility() == View.VISIBLE) {
                    info.setVisibility(View.INVISIBLE);
                   // infobt.setBackgroundResource(R.drawable.infobt);
                }
            }
        });


    }

    public void start(View v) {
        Intent intent = new Intent(this, SelectActivity.class);
        startActivity(intent);

    }
}







