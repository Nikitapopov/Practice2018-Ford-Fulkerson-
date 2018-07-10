package com.model.pathfinder;

import java.util.Random;

public class Edge {
    private Node from, to;
    private Point T1, T2;
    private int flow, max;
    private boolean sogl;
    public int state = 0;

    public Edge(Node from, Node to) {
        this.from = from;
        this.to = to;
        flow = 0;
        max =  new Random().nextInt(10) + 1;
    }

    public void setT1T2(Point T1, Point T2)
    {
        this.T1 = new Point(T1);
        this.T2 = new Point(T2);
    }

    public Edge(Node from, Node to, int max_flow) {
        this.from = from;
        this.to = to;
        flow = 0;
        max = max_flow;// new Random().nextInt(4) + 1;
    }

    @Override
    public String toString() {
        return "mypack.Edge{" +
                "from=" + from +
                ", to=" + to +
                ", flow=" + flow +
                '}';
    }

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }

    public Point getT2() {
        return T2;
    }

    public Point getT1() {
        return T1;
    }

    public int getFlow() {
        return flow;
    }

    public int getMax() {
        return max;
    }

    public boolean getSogl() {
        return sogl;
    }

    public void changeFlow(int value) {
        flow += value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        if (!from.equals(edge.from)) return false;
        return to.equals(edge.to);
    }

    @Override
    public int hashCode() {
        int result = from.hashCode();
        result = 31 * result + to.hashCode();
        return result;
    }

    public void setSogl(boolean b) {
        sogl = b;
    }
}
