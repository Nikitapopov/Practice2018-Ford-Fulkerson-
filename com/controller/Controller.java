package com.controller;

import com.swing.ControlPanel;
import com.view.View;
import com.model.Model;

import java.util.Random;

public class Controller {

    private final ControlPanel controls;
    private final View view;
    private Model model;
    private int framesCount = 0;
    private final int FRAME_TICK = 20;

    public Controller(Model model, View view, ControlPanel controls) {
        this.model = model;
        this.view = view;
        this.controls = controls;
        model.update();
    }

    public void updateView() {
        view.draw(model);

        if(controls.getBSCState() == 1)
            if(controls.getSlider().getValue()/FRAME_TICK <= framesCount) {
                model.update();
                framesCount = 0;
            } else framesCount++;
    }

    public void generateModel(){
        bscSetToBegin();
        framesCount = 0;
        model.generateNewModel(new Random().nextInt(8) + 5);
    }
    public void bscModel(){
        controls.setBscState(controls.getBSCState());
    }

    public void bscSetToBegin(){
        controls.setBscState(-1);
    }

    public void resetModel() {
        model.rewindState();
    }
}
