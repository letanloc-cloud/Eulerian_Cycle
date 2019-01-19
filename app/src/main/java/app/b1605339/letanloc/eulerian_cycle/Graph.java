/*
 * Created by Lê Tấn Lộc on 15:10 18/01/2019
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 11:35 18/01/2019
 */

package app.b1605339.letanloc.eulerian_cycle;

import java.util.ArrayList;

public class Graph {
    //adjacency matrix
    //private int numberOfEdge = 0;

    private int numberOfVertices;   //vertices or nodes or points

    private ArrayList<ArrayList<Boolean>> graphMatrix;
    /*add edge into graph
    ArrayList<Boolean> edge = new ArrayList<Boolean>();
    edge.add(x);
    edge.add(y);
    graph.add(edge);*/

    public Graph(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;

        ArrayList<Boolean> vertices = new ArrayList<>(this.numberOfVertices);
        for (int i = 0; i < this.numberOfVertices; i++) {
            vertices.add(false);
        }
        graphMatrix = new ArrayList<>(this.numberOfVertices);
        for (int i = 0; i < this.numberOfVertices; i++) {
            graphMatrix.add(vertices);
        }

        //System.out.println("\nCreate graph " + numberOfVertices + "x" + numberOfVertices);
        //this.printGraph();
    }

    public void addEdge(int x, int y) {
        //System.out.println("\naddEdge(" + x + "," + y + ")");

        if (!(x == y || this.graphMatrix.get(x).get(y))) { //don't add an edge has one vertices or an edge was added
            //add edge (x,y)
            ArrayList<Boolean> verticesX = new ArrayList<>(this.graphMatrix.get(x));
            verticesX.set(y, true);
            this.graphMatrix.set(x, verticesX);

            //add edge (y,x)
            ArrayList<Boolean> verticesY = new ArrayList<>(this.graphMatrix.get(y));
            verticesY.set(x, true);
            this.graphMatrix.set(y, verticesY);

            //numberOfEdge++;
            //this.printGraph();
        }
    }

    public void printGraph() {
        //System.out.println("\nnumberOfEdge: " + this.numberOfEdge);
        for (int i = 0; i < this.numberOfVertices; i++) {
            for (int j = 0; j < this.numberOfVertices; j++) {
                System.out.print((this.graphMatrix.get(i).get(j) ? 1 : 0) + " ");
            }
            System.out.print("\n");
        }
    }

    //depthFirstSearchRecursion
    private void visit(int u, ArrayList<Boolean> verticesVisitList) {
        //u visited
        if (verticesVisitList.get(u)) {
            return;
        }

        //System.out.print(" " + u + " "); //print to show dfs
        //u was visited
        verticesVisitList.set(u, true);

        for (int i = 0; i < this.numberOfVertices; i++) {
            if ((i != u) && this.graphMatrix.get(u).get(i)) {
                visit(i, verticesVisitList);
            }
        }
    }

    private void depthFirstSearch(int u, ArrayList<Boolean> verticesVisitList) {
        for (int i = 0; i < this.numberOfVertices; i++) {
            verticesVisitList.add(false);
        }
        visit(u, verticesVisitList);
    }

    Boolean isConnected() {


        int i;
        for (i = 0; i < this.numberOfVertices; i++) {
            Boolean checkBreak = false;
            for (int j = 0; j < this.numberOfVertices; j++) {
                //if vertices i has edge (i,j), move to next vertices i++
                if (this.graphMatrix.get(i).get(j)) {
                    break;
                } else {
                    if (j == (this.numberOfVertices - 1)) {
                        checkBreak = true;
                    }
                }
            }
            if (checkBreak) break; //this vertices has no edge, out for-loop
        }

        //all vertices have edge
        if (i == this.numberOfVertices) {
            return true;
        }

        ArrayList<Boolean> verticesVisitList = new ArrayList<Boolean>(this.numberOfVertices);
        depthFirstSearch(i, verticesVisitList);

        for (i = 0; i < this.numberOfVertices; i++) {
            for (int j = 0; j < this.numberOfVertices; j++) {
                //if vertices i has edge (i,j) and it wasn't visited, return false
                if (!verticesVisitList.get(i) && this.graphMatrix.get(i).get(j)) {
                    return false;
                }
            }
        }

        return true;
    }

    public int isEulerian() {

        int sumCheck = 0;
        for (int i = 0; i < this.numberOfVertices; i++) {
            int check = 0;
            for (int j = 0; j < this.numberOfVertices; i++) {
                if (this.graphMatrix.get(i).get(j)) {
                    check++;
                }
            }
            if ((check % 2) == 0) {
                sumCheck++;
            }
        }
        if (sumCheck > 0) {
            return -1;
        }

        if (sumCheck == 2) {

        }

        return numberOfVertices;
    }

    //test
    public static void main(String args[]) {
        Graph graph = new Graph(7);
        /*graph.addEdge(3, 4);
        graph.addEdge(4, 2);
        graph.addEdge(2, 4);
        graph.addEdge(3, 3);*/

        graph.addEdge(0, 1);
        graph.addEdge(0, 4);
        graph.addEdge(0, 5);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(3, 4);
        graph.addEdge(5, 6);

        /*graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);*/

        Boolean test = new Boolean(graph.isConnected());
        System.out.println(test);

    }
}
