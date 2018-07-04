package com.etu.game.model.pathfinder;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class PathFinder {
    private Node start, finish, current;
    private Set<Edge> v_edges;
    private Set<Node> v_nodes;

    public PathFinder(Set<Edge> edges, Set<Node> nodes) {
        v_edges = edges;
        v_nodes = nodes;
        current = start = find_node('a'); // First node always A
        finish = find_node( (char) (new Random().nextInt(nodes.size() - 1) + 'b') ); // Finish node is random, excluding first one

        print_info();
        find_path();
    }

    private void print_info() {
        System.out.println("Nodes: ");
        for(Node node : v_nodes) {
            System.out.println(node);
        }

        System.out.println("Edges: ");
        for(Edge edge : v_edges) {
            System.out.println(edge);
        }

        System.out.println("Start  = " + start);
        System.out.println("Finish = " + finish);
    }

    private void find_path() {
        int max_flow_capacity = Integer.MAX_VALUE;
        start.setMark_flow(max_flow_capacity);
        start.setNode_camefrom(null);
        start.setMark(1);

        while(true) {
            buildEnlargingChain();
            if(current == finish) {
                changeFlowInChain();
                clear_visited_marks();
            }
            else
                break;
        }
        print_answer();
    }

    private void changeFlowInChain() {
        while(current != start) {
            current.getNode_camefrom().setMark_flow(current.getMark_flow());

            if(current.getEdge_camefrom().getSogl())
                current.getEdge_camefrom().changeFlow(current.getMark_flow());
            else
                current.getEdge_camefrom().changeFlow(-current.getMark_flow());

            current = current.getNode_camefrom();
        }
    }

    private void buildEnlargingChain() {
        if(current == finish) return;
        for( Edge edge: current.getEdgeSet() ) {

            if (     edge.getTo().getMark()   == 0 && edge.getMax() - edge.getFlow() > 0)
                changeFlow(edge.getFrom(), edge.getTo(), edge);

            else if (edge.getFrom().getMark() == 0 && edge.getMax() - edge.getFlow() > 0)
                changeFlow(edge.getTo(), edge.getFrom(), edge);
        }
        current.setMark(2);
        for( Edge edge: current.getEdgeSet() )
        {
            if( edge.getTo().getMark() == 1 )
            {
                current = edge.getTo();
                buildEnlargingChain();
                if(current == finish) return;

                current = edge.getFrom();
            } else if ( edge.getFrom().getMark() == 1 )
            {
                current = edge.getFrom();
                buildEnlargingChain();
                if(current == finish) return;

                current = edge.getTo();
            }
        }
    }

    private void changeFlow(Node from, Node to, Edge edge) {
        to.setMark(1);
        to.setNode_camefrom(from);
        to.setEdge_camefrom(edge);

        if (edge.getMax() > edge.getFlow()) {
            edge.setSogl(true);
            to.setMark_flow(Integer.min(from.getMark_flow(), edge.getMax() - edge.getFlow()));
        } else {
            edge.setSogl(false);
            to.setMark_flow(Integer.min(from.getMark_flow(), edge.getFlow()));
        }
    }

    private Node find_node(char ch) {
        for(Node node: v_nodes)
            if(node.getCh() == ch) return node;
        System.out.println("Not found!");
        return null;
    }

    private void clear_visited_marks() {
        for(Node node : v_nodes) {
            node.setMark(0);
        }
        start.setMark(1);
    }

    private void print_answer() {
        int max_flow = 0;

        for(Edge edge: v_edges) {
            if(edge.getFrom() == start || edge.getTo() == start) max_flow += edge.getFlow();
        }

        System.out.println("Max flow = " + max_flow);
    }
}
