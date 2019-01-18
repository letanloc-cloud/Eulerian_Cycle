package app.b1605339.letanloc.eulerian_cycle;

import java.util.ArrayList;

public class Graph {
    //adjacency matrix
    //public int numberOfEdge = 0;

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

        if (!(x == y || this.graphMatrix.get(x).get(y))) { //don't add an edge has one vertices or an edge was being added
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

    //test
    /*public static void main(String args[]) {
        Graph graph = new Graph(5);
        graph.addEdge(3, 4);
        graph.addEdge(4, 2);
        graph.addEdge(2, 4);
        graph.addEdge(3, 3);
    }*/
}
