package com.etu.game.swing;

import com.etu.game.controller.Controller;
import com.etu.game.model.Model;
import com.etu.game.view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameLauncher extends JFrame {

    private final ControlPanel controls;
    private final Component canvas;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameLauncher().setVisible(true));
    }

    private GameLauncher() throws HeadlessException {
        private final Component startPanel = new JPanel();
        startPanel.setPreferredSize(new Dimension(400, 560));

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

        view.setGraphics(new SwingGraphicsAdapter(this, canvas.getGraphics()));

        Controller controller = new Controller(model, view, controls);

        controls.addGenerateButtonListener(e -> controller.generateModel());
        controls.addBSCButtonListener(e -> controller.bscModel());
        controls.addAtFirstButtonListener(e -> controller.atFirstModel());

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
                        controller.atFirstModel();
                }
            }
        });

        Timer timer = new Timer(100, e -> {
            controller.viewUpdated();
            canvas.requestFocus();
        });
        timer.setRepeats(true);
        timer.start();
        timer.setDelay(20);
    }
}
