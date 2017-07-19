package com.example.poornima.molemash;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button bstart ;
    Button bpause ;
    Button breset;
    Bitmap mole;
    LinearLayout canvas;
    ImageView imole;
     Handler h;
    final int delay = 500; //milliseconds
    Runnable runnable;
    TextView tvscore;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        tvscore = (TextView) findViewById(R.id.score);
        updateScore(1);

        canvas = (LinearLayout) findViewById(R.id.canvas);
        //ViewGroup.LayoutParams canvasParam = canvas.getLayoutParams();
        imole = (ImageView) findViewById(R.id.imageView1);

        bstart = (Button) findViewById(R.id.start);
            bstart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(h ==null) {
                    h = new Handler();

                    h.postDelayed(new Runnable() {
                        public void run() {
                            MoveMole();
                            runnable = this;
                            h.postDelayed(this, delay);
                        }
                    }, delay);

                }
            }
        });
        bpause = (Button) findViewById(R.id.pause);
        bpause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                h.removeCallbacks(runnable); //stop handler when activity not visible
            }
        });

        breset = (Button) findViewById(R.id.reset);
        breset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                h.removeCallbacks(runnable); //stop handler when activity not visible
                updateScore(0);
                imole.setImageResource(0);
                h = null;
            }
        });

    }

    public void gotMole(View v){
        updateScore(2);
        Vibrator vb = (Vibrator)   getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(100);
    }

    public void updateScore(int mode){
        switch (mode) {
            case (0):  score = 0; // reset
                        break;
            case (1):  // display
                        break;
            case (2):  score++;// increment
                        break;
            }
            tvscore.setText("Score: "+score);
    }
    private void MoveMole() {
        ViewGroup.LayoutParams canvasParam = canvas.getLayoutParams();
        Random rnd = new Random();
        int w = rnd.nextInt(canvasParam.width-imole.getWidth());
        int h = rnd.nextInt(canvasParam.height-imole.getHeight());
        imole.setX(w);
        imole.setY(h);
        imole.setImageResource(R.drawable.mole);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
