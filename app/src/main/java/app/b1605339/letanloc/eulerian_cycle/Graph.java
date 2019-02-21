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

    private String eulerianCycle = new String("");

    /*add edge into graph
    ArrayList<Boolean> edge = new ArrayList<Boolean>();
    edge.add(x);
    edge.add(y);
    graph.add(edge);*/

    //constructor
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

    public Graph(Graph graph) {
        this.numberOfVertices = graph.numberOfVertices;
        this.graphMatrix = new ArrayList<ArrayList<Boolean>>(graph.graphMatrix);
    }

    public void coppyGraph(Graph graph) {
        this.numberOfVertices = graph.numberOfVertices;
        this.graphMatrix = new ArrayList<ArrayList<Boolean>>(graph.graphMatrix);
    }

    private void changeEdge(int x, int y, Boolean value) {

        //add edge (x,y)
        ArrayList<Boolean> verticesX = new ArrayList<>(this.graphMatrix.get(x));
        verticesX.set(y, value);
        this.graphMatrix.set(x, verticesX);

        //add edge (y,x)
        ArrayList<Boolean> verticesY = new ArrayList<>(this.graphMatrix.get(y));
        verticesY.set(x, value);
        this.graphMatrix.set(y, verticesY);

        //numberOfEdge++;
        //this.printGraph();
    }

    public void addEdge(int x, int y) {
        //System.out.println("\naddEdge(" + x + "," + y + ")");
        if (!(x == y || this.graphMatrix.get(x).get(y))) { //don't add an edge has one vertices or an edge was added
            changeEdge(x, y, true);
        }

    }

    public void removeEdge(int x, int y) {
        changeEdge(x, y, false);
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

    public String printEdge(int i, int j) {
        return (this.graphMatrix.get(i).get(j) ? "1" : "0");
    }

    public int getNumberOfVertices() {
        return numberOfVertices;
    }

    public String getEulerianCycle() {
        eulerianCycle = "";
        this.chooseVertexStart();
        return eulerianCycle;
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

    private Boolean isConnected() {
        int i;
        for (i = 0; i < this.numberOfVertices; i++) {
            Boolean checkBreak = false;
            // Find a vertex with non-zero degree
            // Tìm 1 đỉnh có bậc khác bậc 0
            for (int j = 0; j < this.numberOfVertices; j++) {
                if (this.graphMatrix.get(i).get(j)) {
                    checkBreak = true;
                }
            }
            if (checkBreak) break; //this vertices has no edge, out for-loop
        }

        // If there are no edges in the graph, return true
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


    //0: graph is not Eulerian
    //1: graph has a Euler path
    //2: graph has a Euler cycle
    public int isEulerian() {

        if (!this.isConnected()) {
            return 0;
        }

        int sumCheck = 0;
        for (int i = 0; i < this.numberOfVertices; i++) {
            int check = 0;
            for (int j = 0; j < this.numberOfVertices; j++) {
                if (this.graphMatrix.get(i).get(j)) {
                    check++;
                }
            }
            if ((check % 2) != 0) {
                sumCheck++;
            }
        }

        //graph has more than two odd vertices
        if (sumCheck > 2) {
            return 0;
        }

        //graph has two odd vertices =>
        if (sumCheck == 2) {
            return 1;
        }

        return 2;
    }

    public int countConnectedComponents() {
        int count = 0;
        ArrayList<Boolean> verticesVisitList = new ArrayList<Boolean>(this.numberOfVertices);
        for (int i = 0; i < this.numberOfVertices; i++) {
            verticesVisitList.add(false);
        }
        for (int i = 0; i < this.numberOfVertices; i++) {
            if (!verticesVisitList.get(i)) {
                depthFirstSearch(i, verticesVisitList);
                count++;
            }
        }
        return count;
    }

    public void chooseVertexStart() {
        Graph tempGraph = new Graph(this);
        int res = this.isEulerian();
        if (res == 2) {
            for (int i = 0; i < this.numberOfVertices; i++) {
                for (int j = 0; j < this.numberOfVertices; j++) {
                    if (this.graphMatrix.get(i).get(j)) {
                        System.out.println(i);
                        this.printEulerian(i);
                        this.coppyGraph(tempGraph);
                        return;
                    }
                }

            }
        } else if (res == 1) {
            for (int i = 0; i < this.numberOfVertices; i++) {
                int check = 0;
                for (int j = 0; j < this.numberOfVertices; j++) {
                    if (this.graphMatrix.get(i).get(j)) {
                        check++;
                    }
                }
                if ((check % 2) != 0) {
                    System.out.println(i);
                    this.printEulerian(i);
                    this.coppyGraph(tempGraph);
                    eulerianCycle = "Đồ thị chỉ có đường đi Euler\nĐồ thị không có chu trình Euler";
                    return;
                }
            }
        } else {
            System.out.println(res);
            eulerianCycle = "Đồ thị không có chu trình Euler";
            return;
        }

    }

    public void printEulerian(Integer u) {
        for (int i = 0; i < numberOfVertices; i++) {
            if (this.graphMatrix.get(u).get(i)) {
                if (isNotBridge(u, i)) {
                    System.out.println(u + "-" + i + " ");
                    eulerianCycle = eulerianCycle + u + "-" + i + " ";
                    printEulerian(i);
                }
            }
        }
    }

    private Boolean isNotBridge(Integer u, Integer i) {
        int countVertex = 0;
        for (int j = 0; j < this.numberOfVertices; j++) {
            if (this.graphMatrix.get(u).get(j)) {
                countVertex++;
            }
        }
        if (countVertex == 1) {
            this.removeEdge(u, i);
            return true;
        }

        int count1 = this.countConnectedComponents();
        this.removeEdge(u, i);

        int count2 = this.countConnectedComponents();

        if (count1 < count2) {
            this.addEdge(u, i);
            return false;
        } else {
            return true;
        }
    }
    //test
    /*public static void main(String args[]) {
        Graph graph = new Graph(7);
        //graph.addEdge(3, 4);
        //graph.addEdge(4, 2);
        //graph.addEdge(2, 4);
        //graph.addEdge(3, 3);

        graph.addEdge(0, 1);
        graph.addEdge(0, 4);
        graph.addEdge(0, 5);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(3, 4);
        graph.addEdge(5, 6);

        //graph.addEdge(0, 1);
        //graph.addEdge(1, 2);
        //graph.addEdge(2, 3);

        Boolean test = new Boolean(graph.isConnected());

        System.out.println(test);

    }*/

    void test() {
        int res = isEulerian();
        if (res == 0)
            System.out.println("graph is not Eulerian");
        else if (res == 1)
            System.out.println("graph has a Euler path");
        else
            System.out.println("graph has a Euler cycle");
    }

    // Driver method
    public static void main(String args[]) {
        /*
        // Let us create and test graphs shown in above figures
        Graph g1 = new Graph(5);
        g1.addEdge(1, 0);
        g1.addEdge(0, 2);
        g1.addEdge(2, 1);
        g1.addEdge(0, 3);
        g1.addEdge(3, 4);
        g1.test();
        System.out.println("Count connected components: " + g1.countConnectedComponents());

        Graph g2 = new Graph(5);
        g2.addEdge(1, 0);
        g2.addEdge(0, 2);
        g2.addEdge(2, 1);
        g2.addEdge(0, 3);
        g2.addEdge(3, 4);
        g2.addEdge(4, 0);
        g2.test();
        System.out.println("Count connected components: " + g2.countConnectedComponents());

        Graph g3 = new Graph(5);
        g3.addEdge(1, 0);
        g3.addEdge(0, 2);
        g3.addEdge(2, 1);
        g3.addEdge(0, 3);
        g3.addEdge(3, 4);
        g3.addEdge(1, 3);
        g3.test();
        System.out.println("Count connected components: " + g3.countConnectedComponents());

        // Let us create a graph with 3 vertices
        // connected in the form of cycle
        Graph g4 = new Graph(3);
        g4.addEdge(0, 1);
        g4.addEdge(1, 2);
        g4.addEdge(2, 0);
        g4.test();
        System.out.println("Count connected components: " + g4.countConnectedComponents());

        // Let us create a graph with all veritces
        // with zero degree
        //Không tạo thành đồ thị
        Graph g5 = new Graph(3);
        g5.test();
        System.out.println("Count connected components: " + g5.countConnectedComponents());

        Graph g6 = new Graph(4);
        g6.addEdge(0, 1);
        g6.addEdge(0, 2);
        g6.addEdge(1, 2);
        g6.test();
        System.out.println("Count connected components: " + g6.countConnectedComponents());
        Graph g7 = new Graph(6);
        g7.addEdge(0, 1);
        g7.addEdge(0, 2);
        g7.addEdge(1, 2);
        g7.addEdge(3, 4);
        g7.addEdge(3, 5);
        g7.addEdge(4, 5);
        g7.test();
        System.out.println("Count connected components: " + g7.countConnectedComponents());*/

        Graph g8 = new Graph(5);
        g8.addEdge(1, 0);
        g8.addEdge(0, 2);
        g8.addEdge(2, 1);
        g8.addEdge(0, 3);
        g8.addEdge(3, 4);
        g8.addEdge(3, 2);
        g8.addEdge(3, 1);
        g8.addEdge(2, 4);
        System.out.println("Graph");
        g8.printGraph();
        System.out.println("Eulerian");
        //g8.printEulerian(0);
        System.out.println("Choose");
        g8.test();
        g8.chooseVertexStart();
        g8.printGraph();
        /*g8.printGraph();
        g8.removeEdge(0, 1);
        System.out.println("");
        g8.printGraph();*/

        /*Graph g1 = new Graph(4);
        g1.addEdge(0, 1);
        g1.addEdge(0, 2);
        g1.addEdge(1, 2);
        g1.addEdge(2, 3);
        System.out.println("Eulerian");
        //g1.printEulerian(2);
        System.out.println("Choose");
        g1.chooseVertexStart();

        Graph g2 = new Graph(3);
        g2.addEdge(0, 1);
        g2.addEdge(1, 2);
        g2.addEdge(2, 0);
        System.out.println("Eulerian");
        //g2.printEulerian(0);
        System.out.println("Choose");
        g2.chooseVertexStart();*/

        Graph g10 = new Graph(4);
        g10.addEdge(0, 1);
        System.out.println(g10.countConnectedComponents());


    }
}
