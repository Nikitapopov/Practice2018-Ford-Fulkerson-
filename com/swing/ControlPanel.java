package com.swing;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {

    private final JButton generate  = createButton("generate", 50, 10);
    private final JButton atFirst   = createButton("restart", 150, 10);
    private JButton bsc             = createButton("begin", 250, 10);
    private final JSlider slider    = createSlider(0, 10, 50, 80);
    private int state = 0;

    public ControlPanel() {
        super(null);
        add(generate);
        add(bsc);
        add(atFirst);
        add(slider);
    }

    public void setBsc(int value) {
        switch (value){
            case  -1:
                state = 0;
                bsc.setText("begin");
                break;
            case 0:
            case 2:
                state = 1;
                bsc.setText("stop");
                break;
            case 1:
                state = 2;
                bsc.setText("continue");
                break;
        }
    }

    public int getState() {
        return state;
    }

    public void addGenerateButtonListener(ActionListener listener) {
        generate.addActionListener(listener);
    }

    public void addBSCButtonListener(ActionListener listener) {
        bsc.addActionListener(listener);
    }

    public void addAtFirstButtonListener(ActionListener listener) {
        atFirst.addActionListener(listener);
    }

    private JButton createButton(String text, int x, int y) {
        JButton left = new JButton(text);
        left.setBounds(x, y, 90, 30);
        left.setFocusPainted(false);
        left.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        return left;
    }

    private JSlider createSlider(int min, int max, int x, int y){
        JSlider slider = new JSlider(min, max);
        slider.setBounds(x, y, 300, 30);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(1);
        return slider;
    }
}