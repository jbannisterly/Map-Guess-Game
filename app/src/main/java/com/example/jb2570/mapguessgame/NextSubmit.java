package com.example.jb2570.mapguessgame;

import android.content.Context;
import android.view.View;
import android.widget.Button;

public class NextSubmit {
    private GameManager game;
    private MainActivity main;
    private Button nextSubmit;
    private states currentState;
    public enum states{NEXT_IMAGE, SUBMIT, DISABLED}

    public NextSubmit(MainActivity main, GameManager game){
        this.main = main;
        this.game = game;
        nextSubmit = CreateButton();
        nextSubmit.setOnClickListener(this::onClick);
        SetState(states.NEXT_IMAGE);
    }

    private Button CreateButton(){
        Context con = main.getApplicationContext();
        Button nextSubmit = new Button(con);

        ResizeableView buttonResize = new ResizeableView(nextSubmit);
        buttonResize.AddConfig(new ResizeableViewConfiguration(0.7, 0,0,0.5, 0.3, 0.95, 0,-1.25, -1, 100000, -1, 100000, 1, 100));
        buttonResize.AddConfig(new ResizeableViewConfiguration(0.6, 0,0,0.3, 0.4, 0.95, 0,-0.3, -1, 100000, -1, 100000, 0, 1));

        main.AddResizeableView(buttonResize);
        return nextSubmit;
    }

    private void onClick(View v){
        switch(currentState){
            case SUBMIT:
                game.SubmitConfirm();
                SetState(states.NEXT_IMAGE);
                break;
            case NEXT_IMAGE:
                game.NewLocation();
                SetState(states.DISABLED);
                break;
            case DISABLED:
                break;
        }
    }

    public void SetState(states nextState){
        currentState = nextState;
        switch(nextState){
            case NEXT_IMAGE:
                nextSubmit.setEnabled(true);
                nextSubmit.setText("Next");
                break;
            case SUBMIT:
                nextSubmit.setEnabled(true);
                nextSubmit.setText("Guess");
                break;
            case DISABLED:
                nextSubmit.setEnabled(false);
                nextSubmit.setText("Guess");
                break;
        }
    }

}
