package com.example.jb2570.mapguessgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageStreet {
    private ImageView street;
    private MainActivity main;
    private GoogleMapsStreetImage[] imageBuffer = new GoogleMapsStreetImage[16];
    private int currentImagePointer = 0;
    private int lastImagePointer = 0;
    private Thread imageBufferThread = new Thread(this::ManageImageBuffer);
    private GameManager game;

    public ImageStreet(MainActivity main){
        street = CreateStreetImage(main);
        this.main = main;
        for(int i = 0; i < imageBuffer.length; i++){
            imageBuffer[i] = null;
        }
    }

    private ImageView CreateStreetImage(MainActivity main){
        Context con = main.getApplicationContext();

        ImageView imgStreet = new ImageView(con);

        ResizeableView streetResize = new ResizeableView(imgStreet);
        streetResize.AddConfig(new ResizeableViewConfiguration(0, -0.001,0, 0, 1, 0, 0,0.75, -1, 100000, -1, 100000, 1, 100));
        streetResize.AddConfig(new ResizeableViewConfiguration(0.6, 0,0,0, 0.4, 0, 0,0.3, -1, 100000, -1, 100000, 0, 1));

        main.AddResizeableView(streetResize);

        return imgStreet;
    }


    private void ManageImageBuffer(){
        for(int i = 0; i < imageBuffer.length; i++){
            Log.d("mib check", String.valueOf(i));
            if(imageBuffer[i] == null) {
                imageBuffer[i] = GetNewImage();
                Log.d("mib loaded", String.valueOf(i));
                Log.d("mib count", String.valueOf(URLReader.count));
            }
        }

    }

    public void SetupImages(){
        imageBufferThread = new Thread(this::ManageImageBuffer);
        imageBufferThread.start();
    }

    public void SetGameManager(GameManager game){
        this.game = game;
    }

    public void NewLocation(){
        if(!imageBufferThread.isAlive()) {
            SetupImages();
        }
        while(imageBuffer[currentImagePointer] == null){}
        GoogleMapsStreetImage image = imageBuffer[currentImagePointer];

        Bitmap bmp = BitmapFactory.decodeByteArray(image.data, 0, image.data.length);

        GoogleMapsStreetImage finalImage = image;
        street.setImageBitmap(bmp);

        game.SetAnswer(finalImage.latitude, finalImage.longitude);

        imageBuffer[currentImagePointer] = null;
        currentImagePointer++;
        currentImagePointer %= imageBuffer.length;
    }

    private GoogleMapsStreetImage GetNewImage(){
        double longitude;
        double latitude;
        int width = street.getWidth();
        int height = street.getHeight();

        GoogleMapsStreetImage image = null;
        while (image == null) {
            longitude = RandomBetween(-11, 2.2);
            latitude = RandomBetween(49, 61);
            longitude = RandomBetween(-180, 180);
            latitude = RandomBetween(-90, 90);
            image = GoogleMaps.GetStreetViewImage(longitude, latitude, width, height);
        }
        return image;
    }

    private static double RandomBetween(double min, double max){
        return Math.random() * (max - min) + min;
    }

}
