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
        drawHeader(model.getNodes().size(), model.getMaxFlow());
        drawField();
        drawEdges(model.getEdges());
        drawNodes(model.getNodes(), model.getCurrent(), model.getStart(), model.getFinish());
        drawNodesPossibleFlow(model.getNodes());
    }

    private void drawEdges(Set<Edge> edges) {
        for (Edge edge : edges) {

            Point position1 = edge.getFrom().getPosition();
            Point position2 = edge.getTo().getPosition();
            int x1 = position1.getX();
            int y1 = position1.getY();
            int x2 = position2.getX();
            int y2 = position2.getY();

            Color edge_color;
            if(edge.state == 1) edge_color = Color.EDGE_RED;
            else if(edge.state == 2) edge_color = Color.EDGE_FULL;
            else edge_color = Color.EDGE_BLUE;

            graphics.drawLine(x1,y1 + HEADER_HEIGHT, x2,y2 + HEADER_HEIGHT,  edge_color.getRGB());
            graphics.drawText((x1 + x2)/2 - 5, (y1 + y2)/2 + HEADER_HEIGHT, edge.getMax() + "/" + edge.getFlow(), Color.YELLOW.getRGB());
        }
    }

    @SuppressWarnings("SuspiciousNameCombination")
    private void drawHeader(int node_number, int max_flow) {
        int height = HEADER_HEIGHT - 2;
        graphics.drawRect(0, 0, 400, height, Color.WHITE.getRGB());
        graphics.drawRect(0, height, 400, 2, Color.BLACK.getRGB());
        StringBuilder sb = new StringBuilder();
        sb.append("Node number = " + node_number);
        if(max_flow > 0) sb.append("   Max flow = " + max_flow);
        graphics.drawText(20, 25, sb.toString(), Color.BLACK.getRGB());

    }

    private void drawNodesPossibleFlow(Set<Node> nodes) {
        for (Node node : nodes) {
            Point position = node.getPosition();
            int x = position.getX() - CELL_SIZE/2;
            int y = position.getY() - CELL_SIZE/2 + 14 + HEADER_HEIGHT;

            if(node.getMark_flow() > 9) x += 3; else x += 7;

            graphics.drawText(x, y, String.valueOf(node.getMark_flow()), Color.EDGE_RED.getRGB());
        }
    }

    private void drawNodes(Set<Node> nodes, Node current, Node start, Node finish) {
        for (Node node : nodes) {
            Point position = node.getPosition();
            int x = position.getX() - CELL_SIZE/2;
            int y = position.getY() - CELL_SIZE/2;
            Color node_color;

            if(node.equals(finish)) node_color = Color.NODE_FINISH;
            else if(node.equals(start)) node_color = Color.NODE_START;
            else if(node.getMark() == 1) node_color = Color.NODE_VIEWED;
            else if(node.getMark() == 2) node_color = Color.NODE_DONE;
            else node_color = Color.NODE;

            if(node.equals(current))
                graphics.drawOval(x - 3, y + HEADER_HEIGHT - 3, CELL_SIZE + 6, CELL_SIZE + 6, Color.WHITE.getRGB());
            graphics.drawOval(x, y + HEADER_HEIGHT, CELL_SIZE , CELL_SIZE, node_color.getRGB());
            graphics.drawText(x, y + HEADER_HEIGHT, String.valueOf(node.getCh()).toUpperCase() + node.getMark(), Color.EDGE_RED.getRGB());
        }
    }

    private void drawField() {
        graphics.drawRect(0, HEADER_HEIGHT, 400, 400, Color.BLACK.getRGB());
    }

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }

    private enum Color {
        EDGE_BLUE(new java.awt.Color(106, 20, 47).getRGB()),
        EDGE_FULL(new java.awt.Color(175, 0, 255).getRGB()),
        EDGE_RED(new java.awt.Color(106, 111, 27).getRGB()),
        WHITE(new java.awt.Color(174, 184, 171).getRGB()),
        BLACK(new java.awt.Color(43, 38, 37).getRGB()),
        NODE(new java.awt.Color(255, 218, 135).getRGB()),
        YELLOW(new java.awt.Color(140, 171, 110).getRGB()),
        NODE_START(new java.awt.Color(42, 255, 39).getRGB()),
        NODE_FINISH(new java.awt.Color(183, 0, 2).getRGB()),
        NODE_DONE(new java.awt.Color(66, 150, 143).getRGB()),
        NODE_VIEWED(new java.awt.Color(83, 195, 188).getRGB());

        private final int rgb;

        Color(int rgb) {
            this.rgb = rgb;
        }

        public int getRGB() {
            return rgb;
        }
    }
}
