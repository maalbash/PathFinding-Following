package DataStructures;

/**
 * Created by mohz2 on 3/3/2017.
 */

public class Edge {
    public Edge(Vertex a, Vertex b, float cost){
        this.fromVertex = a;
        this.toVertex = b;
        this.cost = cost;
    }
    public Vertex getFromVertex() {
        return fromVertex;
    }

    public void setFromVertex(Vertex fromVertex) {
        this.fromVertex = fromVertex;
    }

    public Vertex getToVertex() {
        return toVertex;
    }

    public void setToVertex(Vertex toVertex) {
        this.toVertex = toVertex;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    Vertex fromVertex, toVertex;
    float cost;
}
