package com.view;

import com.model.Model;
import com.model.pathfinder.Edge;
import com.model.pathfinder.Node;
import com.model.pathfinder.Point;

import java.util.Set;

public class View {

    private final static int HEADER_HEIGHT = 40;
    private final static int CELL_SIZE = 19;

    private Graphics graphics;

    public void draw(Model model) {
        drawHeader(model.getNodes().size());
        drawField();
        drawEdges(model.getEdges());
        drawNodes(model.getNodes());
        drawSFC(model.getCurrent(), model.getStart(), model.getFinish());
    }

    private void drawEdges(Set<Edge> edges) {
        for (Edge edge : edges) {

            Point position1 = edge.getFrom().getPosition();
            Point position2 = edge.getTo().getPosition();
            int x1 = position1.getX();
            int y1 = position1.getY();
            int x2 = position2.getX();
            int y2 = position2.getY();
            graphics.drawLine(x1,y1 + HEADER_HEIGHT, x2,y2 + HEADER_HEIGHT,  Color.BLUE.getRGB());
            graphics.drawText((x1 + x2)/2 - 5, (y1 + y2)/2 + HEADER_HEIGHT, edge.getMax() + "/" + edge.getFlow(), Color.YELLOW.getRGB());

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

    private void drawSFC(Node current, Node start, Node finish){
        Point position = start.getPosition();
        int x = position.getX() - CELL_SIZE/2;
        int y = position.getY() - CELL_SIZE/2;
        graphics.drawOval(x, y + HEADER_HEIGHT, CELL_SIZE , CELL_SIZE, Color.START_AND_FINISH.getRGB());
        position = finish.getPosition();
        x = position.getX() - CELL_SIZE/2;
        y = position.getY() - CELL_SIZE/2;
        graphics.drawOval(x, y + HEADER_HEIGHT, CELL_SIZE , CELL_SIZE, Color.START_AND_FINISH.getRGB());

        position = current.getPosition();
        x = position.getX() - CELL_SIZE/2;
        y = position.getY() - CELL_SIZE/2;
        graphics.drawOval(x, y + HEADER_HEIGHT, CELL_SIZE , CELL_SIZE, Color.CURRENT.getRGB());
    }


    private void drawField() {
        graphics.drawRect(0, HEADER_HEIGHT, 400, 400, Color.BLACK.getRGB());
    }

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }

    private enum Color {
        BLUE(new java.awt.Color(14, 36, 162).getRGB()),
        RED(new java.awt.Color(255, 0, 0).getRGB()),
        HEADER(new java.awt.Color(169, 180, 192).getRGB()),
        BLACK(new java.awt.Color(0, 0, 0).getRGB()),
        NODE(new java.awt.Color(107, 255, 248).getRGB()),
        YELLOW(new java.awt.Color(255, 227, 0).getRGB()),
        START_AND_FINISH(new java.awt.Color(0, 255, 163).getRGB()),
        CURRENT(new java.awt.Color(0, 255, 0).getRGB());

        private final int rgb;

        Color(int rgb) {
            this.rgb = rgb;
        }

        public int getRGB() {
            return rgb;
        }
    }
}
