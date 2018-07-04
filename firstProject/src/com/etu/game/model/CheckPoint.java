package com.etu.game.model;

import com.etu.game.model.pathfinder.Point;

import java.util.Scanner;

public class CheckPoint {

    private Point position;
    private boolean checked = false;

    public CheckPoint(Point position) {
        this(position, false);
    }

    private CheckPoint(Point position, boolean checked) {
        this.checked = checked;
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
