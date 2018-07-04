package com.etu.game.swing;

import com.etu.game.view.Graphics;

import javax.swing.*;
import java.awt.*;

public class SwingGraphicsAdapter implements Graphics {

    private final JFrame mainFrame;
    private final java.awt.Graphics graphics;

    public SwingGraphicsAdapter(JFrame mainFrame, java.awt.Graphics graphics) {
        this.mainFrame = mainFrame;
        this.graphics = graphics;
    }

    @Override
    @SuppressWarnings("SuspiciousNameCombination")
    public void drawRect(int x, int y, int width, int height, int rgb) {
        graphics.setColor(new Color(rgb));
        graphics.fillRect(x, y, width, height);
    }

    @Override
    @SuppressWarnings("SuspiciousNameCombination")
    public void drawOval(int x, int y, int width, int height, int rgb) {
        graphics.setColor(new Color(rgb));
        graphics.fillOval(x, y, width, height);
    }

    @Override
    public void drawText(int x, int y, String text, int rgb) {
        char[] symbols = text.toCharArray();
        graphics.setColor(new Color(rgb));
        graphics.drawChars(symbols, 0, symbols.length, x, y);
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2, int rgb) {
        graphics.setColor(new Color(rgb));
        graphics.drawLine(x1, y1, x2, y2);
    }
}
