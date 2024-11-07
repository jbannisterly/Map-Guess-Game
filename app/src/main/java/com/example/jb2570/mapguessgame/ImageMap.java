package com.example.jb2570.mapguessgame;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

public class ImageMap {
    private ImageView map;
    private ConstraintLayout container;
    private MainActivity main;
    private List<ImageLocationPin> pins = new ArrayList<>();
    private double minLat = 90;
    private double maxLat = -90;
    private double minLong = -180;
    private double maxLong = 180;
    private GameManager game;

    private ImageLocationPin pin;

    public ImageMap(MainActivity main){
        container = CreateContainer(main);
        map = new ImageView(container.getContext());
        container.addView(map);
        map.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        map.setImageResource(R.drawable.map_world);
        map.setOnTouchListener((view, motionEvent) -> OnTouchImage(view, motionEvent));
        this.main = main;
        pin = CreateNewPin(Color.parseColor("#ff0000"));
    }

    private ConstraintLayout CreateContainer(MainActivity main){
        Context con = main.getApplicationContext();
        ConstraintLayout mapLayout = new ConstraintLayout(con);

        ResizeableView mapResize = new ResizeableView(mapLayout);
        mapResize.AddConfig(new ResizeableViewConfiguration(0, 0,0,0, 1, 0, 0,0.5, -1, 100000, -1, 100000, 1, 100));
        mapResize.AddConfig(new ResizeableViewConfiguration(0, 0,0,0, 0.6, 0, 0,0.3, -1, 100000, -1, 100000, 0, 1));

        main.AddResizeableView(mapResize);
        return mapLayout;
    }


    public void SetGameManager(GameManager game){
        this.game = game;
    }

    public void SubmitGuess(){
        game.SubmitGuess(pin.GetLat(), pin.GetLong());
    }

    public ImageLocationPin CreateNewPin(int pinColour){
        ImageLocationPin pin = new ImageLocationPin(this, map.getContext(), pinColour);
        pin.AddToContainer(container);
        pin.SetVisibility(View.GONE);
        pins.add(pin);
        return pin;
    }

    private boolean OnTouchImage(View v, MotionEvent m){
        double height = v.getHeight();
        double width = v.getWidth();
        double x = m.getX();
        double y = m.getY();
        double xFract = x / width;
        double yFract = y / height;
        double longitude = Interpolate(minLong, maxLong, xFract);
        double latitude = Interpolate(minLat, maxLat, yFract);
        pin.SetLocation(latitude, longitude, GetWidth(), GetHeight());
        pin.SetVisibility(View.VISIBLE);
        game.EnableSubmitGuess();
        return false;
    }

    public double LatitudeToY(double lat){
        double height = GetHeight();
        double offset = GetY();

        return (lat - minLat) / (maxLat - minLat) * height;
    }

    public double LongitudeToX(double longitude){
        double width = GetWidth();
        double offset = GetX();

        return (longitude - minLong) / (maxLong - minLong) * width;
    }

    private double Interpolate(double min, double max, double interpolate){
        return max * interpolate + (1 - interpolate) * min;
    }

    public int GetWidth(){
        return container.getLayoutParams().width;
    }
    public int GetHeight(){return container.getLayoutParams().height;}
    public float GetX(){return container.getX();}
    public float GetY(){return container.getY();}

    public void ConfigChanged(Configuration config) {
        ViewGroup.LayoutParams p = container.getLayoutParams();
        for(int i = 0; i < pins.size(); i++){
            pins.get(i).RefreshLocation(p.width, p.height);
        }
    }

    public void NextImage(){
        pin.SetVisibility(View.GONE);
    }
}
