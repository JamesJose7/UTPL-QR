package com.example.jose.utplqr;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;


public class DisplayFullImage extends ActionBarActivity {

    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_full_image);

        ImageView fullScreenImage = (ImageView) findViewById(R.id.imageFullScreen);
        final ProgressBar imageProgressBar = (ProgressBar) findViewById(R.id.fullScreenImageProgressBar);

        Intent intent = getIntent();
        String imageURI = intent.getStringExtra(DisplayData.IMAGE_URI);

        //Back to Display Activity arrow
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Show exit message
        Toast.makeText(DisplayFullImage.this, "Deslize hacia abajo para salir", Toast.LENGTH_LONG).show();




        //Universal Image Loader
        ImageLoader imageLoader = ImageLoader.getInstance();

        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .showImageOnFail(getResources().getDrawable(R.drawable.image_missing))
                .build();

        imageLoader.displayImage(imageURI, fullScreenImage, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                imageProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                imageProgressBar.setVisibility(View.GONE);

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                imageProgressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getY();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    //Toast.makeText(this, "left2right swipe", Toast.LENGTH_SHORT).show ();
                    finish();
                }
                else
                {
                    // consider as something else - a screen tap for example
                }
                break;
        }
        return super.onTouchEvent(event);
    }


}
