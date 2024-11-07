package com.example.jb2570.mapguessgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageMap map;
    private ImageStreet street;
    private GameManager game;
    private List<ResizeableView> resizeableViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        game = new GameManager(this);


        ScreenSizeChanged();
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        Log.d("Config", "MainPre");
        super.onConfigurationChanged(config);
        Log.d("Config", "MainPost");
        ScreenSizeChanged();
        game.ConfigChanged(config);
    }

    public void AddResizeableView(ResizeableView toAdd){
        resizeableViews.add(toAdd);
        addContentView(toAdd.GetView(), new ConstraintLayout.LayoutParams(0,0));
    }


    private void ScreenSizeChanged(){
        Point screenSize = new Point();
        this.getWindowManager().getDefaultDisplay().getSize(screenSize);
        Point screenSize2 = new Point();
        this.getWindowManager().getDefaultDisplay().getRealSize(screenSize2);

        for(int i = 0; i < resizeableViews.size(); i++){
            resizeableViews.get(i).Resize(screenSize.x, screenSize.y);
        }
    }






}