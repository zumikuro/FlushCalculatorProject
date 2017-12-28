package com.example.aiko.flushcalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        Button button =(Button)findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent =new Intent(getApplicationContext(),PlayActivity.class);
                intent.putExtra(PlayActivity.EXTRA_KEY, PlayActivity.GAME_MODE_1);
                startActivity(intent);
            }

        });
                Button button3 =(Button)findViewById(R.id.button2);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplication(),PlayActivity.class);
                intent.putExtra(PlayActivity.EXTRA_KEY, PlayActivity.GAME_MODE_2);
                startActivity(intent);

            }
        });

        Button button4 =(Button)findViewById(R.id.button3);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplication(),PlayActivity.class);
                intent.putExtra(PlayActivity.EXTRA_KEY, PlayActivity.GAME_MODE_3);
                startActivity(intent);

            }
        });




        Button button5 =(Button)findViewById(R.id.button4);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplication(),PlayActivity.class);
                intent.putExtra(PlayActivity.EXTRA_KEY, PlayActivity.GAME_MODE_4);
                startActivity(intent);

            }
        });

    }
}



