package com.controller;

import com.swing.ControlPanel;
import com.view.View;
import com.model.Model;

import java.util.Random;

public class Controller {

    private final ControlPanel controls;
    private final View view;
    private Model model;

    public Controller(Model model, View view, ControlPanel controls) {
        this.model = model;
        this.view = view;
        this.controls = controls;
    }

    public void viewUpdated() {
        view.draw(model);
    }

    public void generateModel(){
        controls.setBsc(-1);
        model.generateNewModel(new Random().nextInt(5) + 3);
    }
    public void bscModel(){
        controls.setBsc(controls.getState());
    }

    public void atFirstModel(){
        controls.setBsc(-1);
    }
}
