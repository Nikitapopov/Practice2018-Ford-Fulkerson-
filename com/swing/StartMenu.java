package com.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu extends JFrame {
    public StartMenu(GameLauncher gameLauncher) {
        super("Ford-Fulkerson");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JButton startBut = new JButton("Start");
        JButton exitBut = new JButton("Exit");
        JButton referenceBut = new JButton("?");
        startBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                gameLauncher.setVisible(true);
            }
        });
        exitBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        referenceBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createDialogWindow();
            }
        });
        JLabel topLabel = createLabel();
        JPanel contents = new JPanel();
        contents.setPreferredSize(new Dimension(400, 560));
        contents.add(topLabel);
        contents.add(startBut);
        contents.add(referenceBut);
        contents.add(exitBut);
        setContentPane(contents);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JLabel createLabel(){
        JLabel topLabel = new JLabel("Ford-Fulkerson");
        topLabel.setVerticalAlignment(JLabel.CENTER);
        topLabel.setHorizontalAlignment(JLabel.CENTER);
        topLabel.setPreferredSize(new Dimension(400, 500));
        topLabel.setBorder(BorderFactory.createMatteBorder(220,40,220,40, new Color(104, 127, 142)));
        topLabel.setFont(new Font("Verdana", Font.PLAIN, 40));
        topLabel.setForeground(new Color(104, 127, 142));
        return topLabel;
    }

    private void createDialogWindow(){
        JDialog reference = new JDialog(this, "Reference", true);
        reference.setSize(390, 400);
        JPanel panelReference = new JPanel();
        reference.add(panelReference);
        panelReference.setBorder(BorderFactory.createTitledBorder("<html><h2>Справка<h2>"));

        JLabel strReference = new JLabel();
        strReference.setText(referenceStr);
        strReference.setFont(new Font(null, Font.PLAIN, 12));
        panelReference.add(strReference);
        reference.setLocationRelativeTo(null);
        reference.setVisible(true);
    }

    private String referenceStr =
            "<html>Для того, чтобы приступить к работе, необходимо нажать \"Start\".<br>" +
            "Для того, чтобы выйти, необходимо нажать \"Exit\".<br>" +
            "При нажатии \"Start\" появится рабочее окно с отображением<br>" +
            "алгоритма Форда-Фалкерсона. В центре находится графическое<br>\"" +
            "отображение работы алгоритма.<br><br>" +
            "Снизу находятся кнопки управления: \"generate\", \"restart\", \"begin\".<br>" +
            "\"generate\" - генерирует новый граф с случайным количеством<br>" +
            "вершин.<br>" +
            "\"restart\" - возвращает текущий граф к начальному состоянию.<br>" +
            "\"begin\" - запускает работу алгоритма.<br>" +
            "После запуска алгоритма путем нажатия \"begin\" эта кнопка<br>" +
            "поменяется на \"stop\", которая останавливает ход работы<br>" +
            "алгоритма.<br>\"" +
            "После нажатия \"stop\" эта кнопка поменяется на \"continue\",<br>" +
            "которая запускает алгоритм дальше.<br>";
}
