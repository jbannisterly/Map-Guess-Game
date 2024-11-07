package com.example.jb2570.mapguessgame;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GameText {
    private TextView display;
    private MainActivity main;
    public GameText(MainActivity main){
        this.main = main;
        display = new TextView(main.getApplicationContext());
        ResizeableView textResize = new ResizeableView(display);
        textResize.AddConfig(new ResizeableViewConfiguration(0, 0,0,0.5, 0.7, 0.95, 0,-1.25, -1, 100000, -1, 100000, 1, 100));
        textResize.AddConfig(new ResizeableViewConfiguration(0, 0,0,0.3, 0.6, 0.95, 0,-0.3, -1, 100000, -1, 100000, 0, 1));

        main.AddResizeableView(textResize);

        display.setTextSize(20);
        display.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        display.setGravity(Gravity.CENTER);
    }

    public void SetText(String text){
        display.setText(text);
    }

}
