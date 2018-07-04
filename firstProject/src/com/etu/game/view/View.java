package com.etu.game.view;

import com.etu.game.model.CheckPoint;
import com.etu.game.model.Model;
import com.etu.game.model.pathfinder.Edge;
import com.etu.game.model.pathfinder.Node;
import com.etu.game.model.pathfinder.Point;

import java.util.Set;

public class View {

    private final static int HEADER_HEIGHT = 40;
    private final static int CELL_SIZE = 19;

    private Graphics graphics;

    public void draw(Model model) {
        drawHeader(model.getNodes().size());
        drawField();
        /*drawCheckPoints(model.getBonuses());
        drawFinishPoint(model.getFinish());
        drawBall(model.getBall());*/
        drawEdges(model.getEdges());
        drawNodes(model.getNodes());

    }

    private void drawEdges(Set<Edge> edges) {
        for (Edge edge : edges) {
            /*try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            Point position1 = edge.getFrom().getPosition();
            Point position2 = edge.getTo().getPosition();
            int x1 = position1.getX();
            int y1 = position1.getY();
            int x2 = position2.getX();
            int y2 = position2.getY();
            graphics.drawLine(x1,y1 + HEADER_HEIGHT, x2,y2 + HEADER_HEIGHT,  Color.BLUE.getRGB());
            graphics.drawText((x1 + x2)/2 - 5, (y1 + y2)/2 + HEADER_HEIGHT, edge.getMax() + "/" + edge.getFlow(), Color.YELLOW.getRGB());

            /*position2 = edge.getT1();
            x2 = position2.getX();
            y2 = position2.getY();
            graphics.drawLine(x1, y1 + HEADER_HEIGHT, x2,  y2 + HEADER_HEIGHT,  Color.YELLOW.getRGB());


            position2 = edge.getT2();
            x2 = position2.getX();
            y2 = position2.getY();
            graphics.drawLine(x1, y1 + HEADER_HEIGHT, x2,  y2 + HEADER_HEIGHT,  Color.YELLOW.getRGB());*/
            /*
            position2 = edge.getTo().getPosition();
            x2 = position2.getX();
            y2 = position2.getY();
            position1 = edge.getT1();
            x1 = position1.getX();
            y1 = position1.getY();
            graphics.drawLine(y1 + CELL_SIZE /2 - 3, x1 + HEADER_HEIGHT + CELL_SIZE/2 + 4,  y2 + CELL_SIZE /2 - 3, x2 + HEADER_HEIGHT + CELL_SIZE/2 + 4, Color.RED.getRGB());

            position1 = edge.getT2();
            x1 = position1.getX();
            y1 = position1.getY();
            graphics.drawLine(y1 + CELL_SIZE /2 - 3, x1 + HEADER_HEIGHT + CELL_SIZE/2 + 4,  y2 + CELL_SIZE /2 - 3, x2 + HEADER_HEIGHT + CELL_SIZE/2 + 4, Color.RED.getRGB());
*/

        }
    }

    @SuppressWarnings("SuspiciousNameCombination")
    private void drawHeader(int node_number) {
        int height = HEADER_HEIGHT - 2;
        graphics.drawRect(0, 0, 400, height, Color.HEADER.getRGB());
        graphics.drawRect(0, height, 400, 2, Color.YELLOW.getRGB());
        graphics.drawText(20, 25, "Node number = " + node_number, Color.BLUE.getRGB());

    }

    private void drawNodes(Set<Node> nodes) {
        for (Node node : nodes) {
            Point position = node.getPosition();
            int x = position.getX() - CELL_SIZE/2;
            int y = position.getY() - CELL_SIZE/2;
            graphics.drawOval(x, y + HEADER_HEIGHT, CELL_SIZE , CELL_SIZE, Color.NODE.getRGB());
            graphics.drawText(x, y + HEADER_HEIGHT, String.valueOf(node.getCh()).toUpperCase(), Color.RED.getRGB());
        }
    }

    private void drawField() {
        graphics.drawRect(0, HEADER_HEIGHT, 400, 400, Color.BLACK.getRGB());
        for (int i = 0; i < 10; i++) {
            graphics.drawLine(i*40, HEADER_HEIGHT,i*40, HEADER_HEIGHT + 5+i,  Color.BLUE.getRGB());
            //graphics.drawLine(0, HEADER_HEIGHT + i*40,5+i, HEADER_HEIGHT + i*40,  Color.BLUE.getRGB());
        }
    }

    private void drawCheckPoints(Set<CheckPoint> bonuses) {
        int bonusesTaken = 0;
        for (CheckPoint bonus : bonuses) {
            Point position = bonus.getPosition();
            int x = position.getX() * CELL_SIZE + CELL_SIZE / 3;
            int y = position.getY() * CELL_SIZE + CELL_SIZE / 3;
            Color color;
            if (bonus.isChecked()) {
                color = Color.BONUS_CHECKED;
                ++bonusesTaken;
            } else {
                color = Color.BONUS_UNCHECKED;
            }
            graphics.drawOval(HEADER_HEIGHT +  x, y, CELL_SIZE / 4, CELL_SIZE / 4, color.getRGB());
        }
        Color textColor = bonusesTaken == bonuses.size() ? Color.BONUS_CHECKED : Color.BONUS_UNCHECKED;
        graphics.drawText(20, 25, "Bonuses taken: " + bonusesTaken + " / " + bonuses.size(), textColor.getRGB());
    }

    private void drawFinishPoint(CheckPoint finish) {
        Point position = finish.getPosition();
        int x = position.getX() * CELL_SIZE + CELL_SIZE / 4;
        int y = position.getY() * CELL_SIZE + CELL_SIZE / 4;
        graphics.drawOval(HEADER_HEIGHT + x, y, CELL_SIZE / 2, CELL_SIZE / 2, Color.FINISH.getRGB());
    }

    private void drawBall(Point ball) {
        int x = ball.getX() * CELL_SIZE + CELL_SIZE / 4;
        int y = ball.getY() * CELL_SIZE + CELL_SIZE / 4;
        graphics.drawOval(HEADER_HEIGHT + x, y, CELL_SIZE / 2, CELL_SIZE / 2, Color.BALL.getRGB());
    }

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }

    private enum Color {
        BONUS_CHECKED(java.awt.Color.GREEN.getRGB()),
        BONUS_UNCHECKED(java.awt.Color.ORANGE.getRGB()),
        BLUE(new java.awt.Color(14, 36, 162).getRGB()),
        RED(new java.awt.Color(255, 0, 0).getRGB()),
        HEADER(new java.awt.Color(169, 180, 192).getRGB()),
        BLACK(new java.awt.Color(0, 0, 0).getRGB()),
        NODE(new java.awt.Color(107, 255, 248).getRGB()),
        YELLOW(new java.awt.Color(255, 227, 0).getRGB()),
        BALL(java.awt.Color.BLUE.getRGB()),
        FINISH(java.awt.Color.RED.getRGB());

        private final int rgb;

        Color(int rgb) {
            this.rgb = rgb;
        }

        public int getRGB() {
            return rgb;
        }
    }
}
