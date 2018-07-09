package com.model.pathfinder;

import java.util.Random;

public class Edge {
    private Node from, to;
    private int flow, max;
    public int state = 0;

    public Edge(Node from, Node to) {
        this.from = from;
        this.to = to;
        flow = 0;
        max =  new Random().nextInt(10) + 1;
    }

    public void resetEdge(){
        state = 0;
        flow  = 0;
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

    public int getFlow() {
        return flow;
    }

    public int getMax() {
        return max;
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
}
