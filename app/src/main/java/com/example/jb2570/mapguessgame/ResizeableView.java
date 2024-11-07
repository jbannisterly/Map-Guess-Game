package com.example.jb2570.mapguessgame;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ResizeableView {
    private View base;
    private List<ResizeableViewConfiguration> sizes = new ArrayList<>();
    public ResizeableView(View v) {
        base = v;
    }

    public View GetView(){
        return base;
    }

    public void AddConfig(ResizeableViewConfiguration size){
        sizes.add(size);
    }

    public void Resize(int screenWidth, int screenHeight){
        ResizeableViewConfiguration size;
        for(int i = 0; i < sizes.size(); i++){
            size = sizes.get(i);
            if(size.IsValid(screenWidth, screenHeight)){
                size.Resize(screenWidth, screenHeight, base);
                return;
            }
        }
    }
}
