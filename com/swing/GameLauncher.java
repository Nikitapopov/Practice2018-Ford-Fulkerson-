package com.swing;

import com.controller.Controller;
import com.model.Model;
import com.view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameLauncher extends JFrame {

    private final ControlPanel controls;
    private final Component canvas;

    public static void main(String[] args) { SwingUtilities.invokeLater(() -> { new StartMenu(new GameLauncher() ); });
    }

    private GameLauncher() throws HeadlessException {

        canvas = new JPanel();
        canvas.setPreferredSize(new Dimension(400, 440));

        controls = new ControlPanel();
        controls.setPreferredSize(new Dimension(400, 120));

        JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
        rootPanel.setPreferredSize(new Dimension(400, 560));
        rootPanel.add(canvas, BorderLayout.CENTER);
        rootPanel.add(controls, BorderLayout.SOUTH);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(rootPanel);
        pack();
        setLocationRelativeTo(null);

        initListeners();
    }

    private void initListeners() {
        Model model;
        model = new Model(new Random().nextInt(8) + 3);

        View view = new View();

        view.setGraphics(new SwingGraphicsAdapter(canvas.getGraphics()));

        Controller controller = new Controller(model, view, controls);

        controls.addGenerateButtonListener(e -> controller.generateModel());
        controls.addBSCButtonListener(e -> controller.bscModel());
        controls.addAtFirstButtonListener(e -> controller.bscSetToBegin());
        controls.addAtFirstButtonListener(e -> controller.resetModel());

        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent event) {
                switch (event.getKeyCode()) {
                    case KeyEvent.VK_G:
                        controller.generateModel();
                        break;
                    case KeyEvent.VK_SPACE:
                        controller.bscModel();
                        break;
                    case KeyEvent.VK_R:
                        controller.bscSetToBegin();
                }
            }
        });

        Timer timer = new Timer(20, e -> {
            controller.updateView();
            canvas.requestFocus();
        });
        timer.setRepeats(true);
        timer.start();
    }
}
