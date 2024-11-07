package com.example.jb2570.mapguessgame;

import android.content.res.Configuration;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

public class GameManager {
    private static final double EARTH_RADIUS = 3963;
    private double trueLat;
    private double trueLong;
    private double guessLat;
    private double guessLong;
    private ImageLocationPin answerPin;
    private ImageMap map;
    private ImageStreet street;
    private MainActivity main;
    private NextSubmit nextSubmit;
    private GameText gameFeedback;

    public GameManager(MainActivity main){
        this.main = main;
        map = new ImageMap(main);
        street = new ImageStreet(main);
        nextSubmit = new NextSubmit(main, this);
        gameFeedback = new GameText(main);
        gameFeedback.SetText("");
        map.SetGameManager(this);
        street.SetGameManager(this);
        answerPin = map.CreateNewPin(Color.parseColor("#00ff00"));

    }

    public void SubmitGuess(double latitude, double longitude){
        guessLat = latitude;
        guessLong = longitude;
    }

    public void NewLocation(){
        street.NewLocation();
        map.NextImage();
    }

    public void SubmitConfirm(){
        map.SubmitGuess();
        RevealAnswer();
    }


    public void SetAnswer(double latitude, double longitude){
        gameFeedback.SetText("Guess where this is");
        answerPin.SetVisibility(View.GONE);
        trueLat = latitude;
        trueLong = longitude;
    }

    public void ConfigChanged(Configuration config){
        map.ConfigChanged(config);
    }

    public void RevealAnswer(){
        answerPin.SetLocation(trueLat, trueLong, (int)map.GetWidth(), (int)map.GetHeight());
        answerPin.SetVisibility(View.VISIBLE);

        double distance = Distance(guessLat, guessLong, trueLat, trueLong);
        gameFeedback.SetText(String.valueOf((int)distance) + " miles out");
    }

    private double SigFigs(double value, int sigfigs){
        int shift = (int)Math.ceil(Math.log10(value)) - sigfigs;
        return Math.round(value / Math.pow(10, shift)) * Math.pow(10, shift);
    }

    private double Distance(double lat1, double long1, double lat2, double long2){
        double lat1c = Math.cos(lat1 * Math.PI / 180);
        double lat2c = Math.cos(lat2 * Math.PI / 180);
        double lat1s = Math.sin(lat1 * Math.PI / 180);
        double lat2s = Math.sin(lat2 * Math.PI / 180);
        double long1c = Math.cos(long1 * Math.PI / 180);
        double long2c = Math.cos(long2 * Math.PI / 180);
        double long1s = Math.sin(long1 * Math.PI / 180);
        double long2s = Math.sin(long2 * Math.PI / 180);

        return EARTH_RADIUS * Math.acos(
                (lat1c * lat2c * long1s * long2s)
                        + (lat1c * lat2c * long1c * long2c)
                + (lat1s * lat2s)
        );
    }

    public void EnableSubmitGuess(){
        nextSubmit.SetState(NextSubmit.states.SUBMIT);
    }

}
