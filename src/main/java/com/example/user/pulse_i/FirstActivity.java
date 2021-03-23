package com.example.user.pulse_i;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by user on 2015-10-05.
 */
public class FirstActivity extends Activity {

    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);

        imageView1=(ImageView)findViewById(R.id.image2);
        imageView2=(ImageView)findViewById(R.id.image3);
        imageView3=(ImageView)findViewById(R.id.image4);

        imageView1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.getId() == R.id.image2) {
                    Intent myIntent = new Intent(FirstActivity.this, MainActivity.class);
                    startActivity(myIntent);
                }
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.getId() == R.id.image3) {
                    Intent myIntent2 = new Intent(FirstActivity.this, List2Activity.class);
                    startActivity(myIntent2);
                }
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.getId() == R.id.image4) {
                    Intent myIntent3 = new Intent(FirstActivity.this, List3Activity.class);
                    startActivity(myIntent3);
                }
            }
        });


    }



}
