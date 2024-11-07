package com.example.jb2570.mapguessgame;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.DrawableContainer;
import android.opengl.Visibility;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class ImageLocationPin {
    private ImageMap baseMap;
    private ImageView display;
    private double latitude;
    private double longitude;
    private int pinHeight;
    private int pinWidth;

    public ImageLocationPin(ImageMap inbaseMap, Context con, int colour){
        baseMap = inbaseMap;
        display = new ImageView(con);
        display.setImageResource(R.drawable.pin);
        display.setX(0);
        display.setY(0);
        display.setAdjustViewBounds(true);
        display.setColorFilter(colour);
    }

    public void RefreshLocation(int width, int height){
        SetLocation(latitude, longitude, width, height);
    }

    public void SetLocation(double inLat, double inLong, int width, int height){
        UpdateScale(width, height);
        latitude = inLat;
        longitude = inLong;
        display.setX((float)baseMap.LongitudeToX(longitude) - pinWidth / 2);
        display.setY((float)baseMap.LatitudeToY(latitude) - pinHeight);
    }

    public double GetLong(){
        return longitude;
    }

    public double GetLat(){
        return latitude;
    }

    private void UpdateScale(int width, int height){
        display.setScaleType(ImageView.ScaleType.FIT_END);
        ViewGroup.LayoutParams p = display.getLayoutParams();
        pinWidth = (int)(width / 32);
        pinHeight = pinWidth * 226 / 160;
        p.height = pinHeight;
        p.width  = pinWidth;
        display.setLayoutParams(p);
    }

    public void AddToContainer(ConstraintLayout container){
        container.addView(display);
    }

    public void SetVisibility(int visibility){
        display.setVisibility(visibility);
    }
}
