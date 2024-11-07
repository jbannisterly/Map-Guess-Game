package com.example.jb2570.mapguessgame;

import android.view.View;
import android.view.ViewGroup;

public class ResizeableViewConfiguration {
    private double left = 0;
    private double top = 0;
    private double leftH = 0;
    private double topW = 0;
    private double width = 1;
    private double height = 1;
    private double widthH = 0;
    private double heightW = 0;
    private double minWidth = -1;
    private double maxWidth = 100000;
    private double minHeight = -1;
    private double maxHeight = 100000;
    private double minRatio = -1;
    private double maxRatio = 100000;

    public ResizeableViewConfiguration(double left, double top,double leftH, double topW, double width, double height, double widthH, double heightW, double minWidth, double maxWidth, double minHeight, double maxHeight, double minRatio, double maxRatio){
        this.left = left;
        this.top = top;
        this.leftH = leftH;
        this.topW = topW;
        this.width = width;
        this.height = height;
        this.widthH = widthH;
        this.heightW = heightW;
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.minRatio = minRatio;
        this.maxRatio = maxRatio;
    }

    public void Resize(int screenWidth, int screenHeight, View toResize){
        ViewGroup.LayoutParams params = toResize.getLayoutParams();
        params.height = (int)(height * screenHeight + heightW * screenWidth);
        params.width = (int)(width * screenWidth + widthH * screenHeight);
        toResize.setLayoutParams(params);
        if(left < 0){
            toResize.setX((float)(left * screenWidth + screenWidth - params.width + screenHeight * leftH));
        }else{
            toResize.setX((float)(left * screenWidth + screenHeight * leftH));
        }
        if(top < 0) {
            toResize.setY((float) (top * screenHeight + screenHeight - params.height + screenWidth * topW));
        }else{
            toResize.setY((float) (top * screenHeight + screenWidth * topW));
        }
    }

    public boolean IsValid(int screenWidth, int screenHeight){
        double ratio = ((double)screenHeight) / screenWidth;
        if(ratio < minRatio) return false;
        if(ratio > maxRatio) return false;
        if(screenHeight < minHeight) return false;
        if(screenHeight > maxHeight) return false;
        if(screenWidth < minWidth) return false;
        if(screenWidth > maxWidth) return false;
        return true;
    }
}
