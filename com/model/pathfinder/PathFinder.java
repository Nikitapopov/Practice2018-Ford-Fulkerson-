package com.model.pathfinder;

import java.util.Random;
import java.util.Set;

public class PathFinder {
    private Node start, finish, current;
    private Set<Edge> v_edges;
    private Set<Node> v_nodes;
    private int state = 0, max_flow = 0;

    public PathFinder(Set<Edge> edges, Set<Node> nodes) {
        v_edges = edges;
        v_nodes = nodes;
        current = start = find_node('a'); // First node always A
        finish = find_node( (char) (new Random().nextInt(nodes.size() - 1) + 'b') ); // Finish node is random, excluding first one
    }

    public void rewindState() {
        for(Edge edge : v_edges){
            edge.resetEdge();
        }
        for(Node node : v_nodes){
            node.resetNode();
        }

        clear_visited_marks();

        start.setMark_flow(20);
        current = start;
        max_flow = 0;
        state = 0;
    }

    private void checkForFullEdges(){
        for(Edge edge : v_edges)
            if(edge.getFlow() - edge.getMax() == 0) edge.state = 2;
    }

    public void make_step()
    {
        checkForFullEdges();

        switch (state)
        {
            case 0:
                start.setMark_flow(20);
                start.setNode_camefrom(null);
                start.setMark(1);
                state = 1;
                break;
            case 1:
                findAllUnmarkedNode();
                current.setMark(2);
                boolean isCurrentChanged = false;

                if(finish.getMark() == 1) {
                    current = finish;
                    state = 2;
                    break;
                }

                for(Node node : v_nodes)
                    if(node.getMark() == 1) {
                    current = node;
                    isCurrentChanged = true;
                    break;
                }

                if(!isCurrentChanged) {
                    print_answer();
                    clear_visited_marks();
                    state = 3;
                }

                break;
            case 2:
                if(current == start) {
                    clear_visited_marks();
                    start.setMark_flow(20);
                    state = 1;
                    break;
                }

                current.getNode_camefrom().setMark_flow(current.getMark_flow());
                current.getEdge_camefrom().changeFlow(current.getMark_flow());
                current = current.getNode_camefrom();

            case 3:
                break;
        }
    }

    private void findAllUnmarkedNode(){
        for (Edge edge : current.getEdgeSet()) {
            if (edge.getTo().getMark() == 0 && edge.getMax() - edge.getFlow() > 0) {
                changeFlow(current, edge.getTo(), edge);
            } else if (edge.getFrom().getMark() == 0 && edge.getMax() - edge.getFlow() > 0) {
                changeFlow(current, edge.getFrom(), edge);
            } else continue;
            edge.state = 1;
        }
    }

    private void changeFlow(Node from, Node to, Edge edge) {
        to.setMark(1);
        to.setNode_camefrom(from);
        to.setEdge_camefrom(edge);

        if (edge.getMax() > edge.getFlow())
            to.setMark_flow(Integer.min(from.getMark_flow(), edge.getMax() - edge.getFlow()));
    }

    private Node find_node(char ch) {
        for(Node node: v_nodes)
            if(node.getCh() == ch) return node;
        System.out.println("Not found!");
        return null;
    }

    private void clear_visited_marks() {
        for(Node node : v_nodes) node.setMark(0);
        for(Edge edge : v_edges) if (edge.state != 2) edge.state = 0;
        start.setMark(1);
    }

    private void print_answer() {

        for(Edge edge: v_edges) {
            if(edge.getFrom() == finish || edge.getTo() == finish) max_flow += edge.getFlow();
        }

        System.out.println("Max flow = " + max_flow);
    }

    public Node getStart() {
        return start;
    }

    public Node getFinish() {
        return finish;
    }

    public Node getCurrent() {
        return current;
    }

    public int getMaxFlow() {
        return max_flow;
    }
}
