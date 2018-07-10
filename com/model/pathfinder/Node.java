package com.model.pathfinder;

import java.util.*;

public class Node {
    private static int count = 0;
    private char ch, mark;
    private Set<Edge> edges;
    private Point position;

    private Node node_camefrom = null;
    private Edge edge_camefrom = null;
    private int mark_flow;

    private static final int INDENT = 30;
    private static final int BOUND = 300;

    public Node(Set<Node> nodes) {
        edges = new HashSet<>();
        ch = (char)((int)'a' + count++);

        int xn = new Random().nextInt(BOUND) + INDENT;
        int yn = new Random().nextInt(BOUND) + INDENT;

        while(isThereOtherNodesNear(nodes, xn, yn, 70))
        {
            xn = new Random().nextInt(BOUND) + INDENT;
            yn = new Random().nextInt(BOUND) + INDENT;
        }

        position = new Point(xn, yn);
    }

    public Node(char ch) {
        this.ch = ch;
        mark = 0;
        mark_flow = 0;
        node_camefrom = null;
        edge_camefrom = null;
        edges = new HashSet<>();
    }

    @Override
    public String toString() {
        return "mypack.Node{" +
                "ch=" + ch +
                '}';
    }

    private boolean isThereOtherNodesNear(Set<Node> nodes, int xn, int yn, int r)
    {
        for (Node node : nodes) {
            Point position = node.getPosition();
            int x = position.getX();
            int y = position.getY();
            if (Math.sqrt(Math.pow(x - xn, 2) + Math.pow(y - yn, 2)) < r) return true;
        }

        return false;
    }

    public Point getPosition() {
        return position;
    }

    public char getCh() {
        return ch;
    }

    public static void resetCount() {
        count = 0;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public int getMark() {
            return mark;
    }

    public void setMark(int i) {
        mark = (char)i;
    }

    public Set<Edge> getEdgeSet() {
        return edges;
    }

    public Node getNode_camefrom() {
        return node_camefrom;
    }

    public int getMark_flow() {
        return mark_flow;
    }

    public void setMark_flow(int mark_flow) {
        this.mark_flow = mark_flow;
    }

    public Edge getEdge_camefrom() {
        return edge_camefrom;
    }

    public void setNode_camefrom(Node o) {
        node_camefrom = o;
    }

    public void changeFlow(int mark_flow) {
        this.mark_flow += mark_flow;
    }

    public void setEdge_camefrom(Edge edge) {
        edge_camefrom = edge;
    }
}
