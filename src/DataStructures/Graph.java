package DataStructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by mohz2 on 3/3/2017.
 */
public class Graph {


    int row,col;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public HashMap<Integer, Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(HashMap<Integer, Vertex> vertices) {
        this.vertices = vertices;
    }

    public HashMap<Integer, ArrayList<Edge>> getVertexEdges() {
        return vertexEdges;
    }

    public void setVertexEdges(HashMap<Integer, ArrayList<Edge>> vertexEdges) {
        this.vertexEdges = vertexEdges;
    }

    HashMap<Integer,Vertex> vertices;
    HashMap<Integer,ArrayList<Edge>> vertexEdges;

    public HashSet<Integer> getInvalidVertices() {
        return invalidVertices;
    }

    public void setInvalidVertices(HashSet<Integer> invalidVertices) {
        this.invalidVertices = invalidVertices;
    }

    HashSet<Integer> invalidVertices;

    public Graph(int row, int col) {
        this.row = row;
        this.col = col;
        this.vertices = new HashMap<>();
        this.vertexEdges = new HashMap<>();
        this.invalidVertices = new HashSet<>();
    }

    public void buildGraph(){
        for(int x = 0; x < row; x++){
            for(int y = 0; y < col; y++) {
                Vertex v = new Vertex(x * col + y, x, y);
                vertices.put(x * col + y, v);
            }
        }

        for(int x = 0; x < row; x++)
        {
            for(int y = 0; y < col; y++)
            {
                int iter = x * row + y;
                if(invalidVertices.contains(iter))
                    continue;

                ArrayList<Edge> temp = new ArrayList<>();

                if(y-1 >= 0 && !invalidVertices.contains(iter-1))
                    temp.add(new Edge(vertices.get(iter),vertices.get(iter-1),2.f));

                if(x-1 >= 0 && !invalidVertices.contains(iter-col))
                    temp.add(new Edge(vertices.get(iter),vertices.get((x-1)*col+y),2.f));

                if(y+1 < col && !invalidVertices.contains(iter+1))
                    temp.add(new Edge(vertices.get(iter),vertices.get(iter+1),2.f));

                if(x+1 < row && !invalidVertices.contains(iter+col))
                    temp.add(new Edge(vertices.get(iter),vertices.get((x+1)*col+y),2.f));



                if(x-1 >= 0 && y-1 >= 0 && !invalidVertices.contains(iter-1-col))
                    temp.add(new Edge(vertices.get(iter),vertices.get((x-1)*col+y-1),(float)Math.sqrt(8)));

                if(x+1 < row && y-1 >= 0 && !invalidVertices.contains(iter-1+col))
                    temp.add(new Edge(vertices.get(iter),vertices.get((x+1)*col+y-1),(float)Math.sqrt(8)));

                if(x+1 < row && y+1 < col && !invalidVertices.contains(iter+1+col))
                    temp.add(new Edge(vertices.get(iter),vertices.get((x+1)*col+y+1),(float)Math.sqrt(8)));

                if(x-1 >= 0 && y+1 < col && !invalidVertices.contains(iter+1-col))
                    temp.add(new Edge(vertices.get(iter),vertices.get((x-1)*col+y+1),(float)Math.sqrt(8)));

                vertexEdges.put(iter,temp);
            }
        }
    }
}
