package com.etu.game.model;

import com.etu.game.model.pathfinder.Edge;
import com.etu.game.model.pathfinder.Node;
import com.etu.game.model.pathfinder.PathFinder;
import com.etu.game.model.pathfinder.Point;

import java.util.HashSet;
import java.util.Set;

import static java.lang.Double.NaN;

public class Model {

    private Set<Node> nodes;
    private Set<Edge> edges;
    private PathFinder algorithm;
    private static final int CONST = 80;

    public void resetNodesCharCount() {
        Node.resetCount();
    }

    public void generateNewModel(int nodes_number) {
        resetNodesCharCount();

        nodes = new HashSet<>();
        edges = new HashSet<>();
        for (int i = 0; i < nodes_number; i++) {
            Node node = new Node(nodes);
            //System.out.println("New node [" + node.getCh() + "] (" + node.getPosition().getX() + ", " + node.getPosition().getY() + ")");
            nodes.add(node);
        }

        for (Node node : nodes) {
            Point O = node.getPosition();

            for (Node node2 : nodes) {
                if (node == node2) continue;
                Point M = node2.getPosition();

                boolean isThereAlreadyThatEdge = false;
                boolean isThereIntersect = false;
                for (Edge edge : edges) {
                    if(edge.getFrom() == node && edge.getTo() == node2 || edge.getFrom() == node2 && edge.getTo() == node) {
                        isThereAlreadyThatEdge = true;
                        break;
                    }
                    if(checkForIntersect(edge, node, node2))
                    {
                        isThereIntersect = true;
                        break;
                    }
                }

                if(isThereAlreadyThatEdge || isThereIntersect)
                {
                    continue;
                }
                //System.out.println("Trying to build edge between [" + node2.getCh() + "] and [" + node.getCh() + "]");

                Point T1 = null, T2 = null;
                boolean isThereAnyPointsBetween = false;


                for (Node node3 : nodes) {
                    if (node3 == node2 || node3 == node ) continue;
                    Point P = node3.getPosition();


                    Pair<Point, Point> TrianglePoints = prepareTrianglePoints(O, M);

                    T1 = TrianglePoints.t;
                    T2 = TrianglePoints.u;

                    if(isPointInTriangle(O, P, T1, T2)) {
                        isThereAnyPointsBetween = true;
                        //System.out.println("Point [" + node3.getCh() + "] is in triange between [" + node.getCh() + "] and [" + node2.getCh() + "]");
                        break;
                    }
                }

                if(!isThereAnyPointsBetween){
                    Edge edge = new Edge(node, node2);
                    edge.setT1T2(T1, T2);
                    node.addEdge(edge);
                    node2.addEdge(edge);
                    edges.add(edge);
                    //System.out.println("Edge between [" + node2.getCh() + "] and [" + node.getCh() + "] is built");
                }

            }
        }

        algorithm = new PathFinder(edges, nodes);
    }

    public Model(int nodes_number) {
        generateNewModel(nodes_number);
    }

    private boolean checkForIntersect(Edge edge, Node node, Node node2) {
        // TODO никаких проверок на корректность вычислений нет )))
        if(node == edge.getFrom() || node == edge.getTo() || node2 == edge.getFrom() || node2 == edge.getTo()) return false;
        /*
              (y1 - y0)
        y =   ---------*(x - x0) + y0 = -k1*(x - x0) + y0;
              (x1 - x0)                                             k2*x3 + y3 - k1*x1 - y1
        ||                                                  => x =  ----------------------- - point of intersection
              (y3 - y2)                                                     k2 - k1
        y =   ---------*(x - x2) + y2 = -k2*(x - x2) + y2;
              (x3 - x2)
         */
        int x1 = node2.getPosition().getX();
        int x0 = node.getPosition().getX();
        int x3 = edge.getFrom().getPosition().getX();
        int x2 = edge.getTo().getPosition().getX();

        int y1 = node2.getPosition().getY();
        int y0 = node.getPosition().getY();
        int y3 = edge.getFrom().getPosition().getY();
        int y2 = edge.getTo().getPosition().getY();

        double k1 = (double)(y1 - y0)/(double)(x1 - x0);
        double k2 = (double)(y3 - y2)/(double)(x3 - x2);
        double x = (k1*x0 - y0 - k2*x2 + y2)/(k1 - k2);
       /* double y;
        if(!isDoubleInBounds(k1, EPS, -EPS)) y = -k1*(x - x1) + y1;
        else y = -k2*(x - x3) + y3;*/

        if(isDoubleInBounds(x, x0, x1) && isDoubleInBounds(x, x2, x3))
        {

            return true;/*System.out.println("x = " + x + "(" + x0 + ", " + x1 + ", " + x2 + ", " + x3 + ")");
            System.out.println("1111Edge {" + edge.getFrom().getCh() + ", " + edge.getTo().getCh() + "} intersect {" +
                    "{" + node.getCh() + ", " + node2.getCh() + "}");*/
        }

        return false;
    }

    // if value принадлежит (bound1, bound2) или (bound2, bound1)
    private boolean isDoubleInBounds(double value, double bound1, double bound2) {
        return Double.compare(bound1, bound2) < 0 && Double.compare(bound2, value) > 0 && Double.compare(bound1, value) < 0
                || Double.compare(bound1, bound2) > 0 && Double.compare(bound2, value) < 0 && Double.compare(bound1, value) > 0;
    }

    private Pair<Point, Point> prepareTrianglePoints(Point O, Point M)
    {
        Point T1, T2;
        int dx = M.getX() - O.getX();
        int dy = M.getY() - O.getY();

        if (dx == 0) {
            T1 = new Point(M.getX() + CONST, M.getY());
            T2 = new Point(M.getX() - CONST, M.getY());
        } else if (dy == 0) {
            T1 = new Point(M.getX(), M.getY() + CONST);
            T2 = new Point(M.getX(), M.getY() - CONST);
        } else {
            double k = dx / dy;
            double new_x = CONST / Math.sqrt(1 + k * k) + M.getX();
            T1 = new Point((int) (new_x), (int) ((-k * (new_x - M.getX())) + M.getY()));

            new_x = (-CONST / Math.sqrt(1 + k * k)) + M.getX();
            T2 = new Point((int) (new_x), (int) (-k * (new_x - M.getX()) + M.getY()));
        }
        return  new Pair<>(T1, T2);
    }

    // Лежит ли точка Р в треугольнике А, Т1, Т2
    private boolean isPointInTriangle(Point A, Point P, Point T1, Point T2) {
        Point B;
        Point C;
        Point Pn;
        B  = new Point(T1.getX() - A.getX(), T1.getY() - A.getY());
        C  = new Point(T2.getX() - A.getX(), T2.getY() - A.getY());
        Pn = new Point(P.getX()  - A.getX(), P.getY()  - A.getY());


        double m = (double) (Pn.getX() * B.getY() - B.getX() * Pn.getY()) /(double)(C.getX()*B.getY() - B.getX()*C.getY());
        if (m == NaN) System.out.println("NAn");
         if (Double.compare(m, 0) > 0 && Double.compare(m, 1) < 0){
            double l = (Pn.getX() - m*C.getX()) /(double) B.getX();
             return (Double.compare(l, 0) > 0) && (Double.compare(m + l, 1) < 0);
        }
        return false;
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    // Класс для возврата из функции 2 значений, ну и прост чтоб можно было пары хранить)0)0
    public class Pair<T, U> {
        T t;
        U u;

        Pair(T t, U u) {
            this.t= t;
            this.u= u;
        }
    }
}
