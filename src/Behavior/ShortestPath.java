package Behavior;

import DataStructures.*;

import java.util.*;

/**
 * Created by mohz2 on 3/21/2017.
 */
public class ShortestPath {

    private compareDijkstraPaths PathComparatorDijkstra = new compareDijkstraPaths();
    private compareAstarPaths PathComparatorAstar = new compareAstarPaths();
    public ShortestPath(int start, int end){
        this.start = start;
        this.end = end;
        this.dijkstraMap = new HashMap<>();
        this.aStarMap = new HashMap<>();
        this.closedList = new HashSet<>();
        this.path = new HashMap<>();
        this.bestPath = new ArrayList<>();
    }


    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    private int start,end;
    private ArrayList<Integer> bestPath;
    private Map<Integer, Float> dijkstraMap;
    private Map<Integer, Float> aStarMap;
    private Map<Integer, Integer> path;
    private PriorityQueue<Integer> openList;

    public int getMaxSizeOL() {
        return maxSizeOL;
    }

    public void setMaxSizeOL(int maxSizeOL) {
        this.maxSizeOL = maxSizeOL;
    }

    private int maxSizeOL;

    public boolean isDijkstra() {
        return isDijkstra;
    }

    public void setDijkstra(boolean dijkstra) {
        isDijkstra = dijkstra;
    }

    private boolean isDijkstra;

    public ArrayList<Integer> getBestPath() {
        return bestPath;
    }

    public void setBestPath(ArrayList<Integer> bestPath) {
        this.bestPath = bestPath;
    }

    public HashSet<Integer> getClosedList() {
        return closedList;
    }

    public void setClosedList(HashSet<Integer> closedList) {
        this.closedList = closedList;
    }

    private HashSet<Integer> closedList;

    public void FindPath(Graph G){
        int startIndex = getStart(),goalIndex = getEnd();
        int currentIndex, endNode;

        float cost = 0;
        this.maxSizeOL = 1;
        if(isDijkstra)
            openList = new PriorityQueue<>(PathComparatorDijkstra);
        else
            openList = new PriorityQueue<>(PathComparatorAstar);
        ArrayList<Edge> neighborEdges;
        openList.add(startIndex);
        dijkstraMap.put(startIndex,0f);
        aStarMap.put(startIndex,(float) calculateHeuristicEuclideanDistance(G.getVertices(),startIndex,goalIndex));
        //aStarMap.put(startIndex, calculateHeuristicManhattan(G.getVertices(),startIndex,goalIndex));
        while(!openList.isEmpty()){
            currentIndex = openList.poll();
            if(currentIndex == goalIndex)
                break;
            neighborEdges = G.getVertexEdges().get(currentIndex);

            for(Edge edge:neighborEdges){
                endNode = edge.getToVertex().getIndex();

                if(closedList.contains(endNode))
                    continue;

                cost = dijkstraMap.get(currentIndex) + edge.getCost();
                if(!dijkstraMap.containsKey(endNode) || cost <= dijkstraMap.get(endNode)){
                        if(openList.contains(endNode))
                            openList.remove(endNode);
                        dijkstraMap.put(endNode,cost);
                        aStarMap.put(endNode,cost + (float) calculateHeuristicEuclideanDistance(G.getVertices(), endNode, goalIndex));
                        //aStarMap.put(endNode, cost + calculateHeuristicManhattan(G.getVertices(),endNode,goalIndex));
                        openList.add(endNode);
                        if(this.maxSizeOL < openList.size())
                            maxSizeOL = openList.size();
                        path.put(endNode,currentIndex);
                }
            }
            closedList.add(currentIndex);
        }
        bestPath.add(goalIndex);
        if(startIndex != goalIndex){
            int prev = path.get(goalIndex);
            while(prev != startIndex){
                bestPath.add(prev);
                prev = path.get(prev);
            }
        }
        bestPath.add(startIndex);
        Collections.reverse(bestPath);
    }

//    public void Astar(Graph G){
//        int startIndex = getStart(),goalIndex = getEnd(), currentIndex, endNode;
//        float cost = 0;
//        openList = new PriorityQueue<>(PathComparatorAstar);
//        ArrayList<Edge> neighborEdges;
//        openList.add(startIndex);
//        dijkstraMap.put(startIndex,0f);
//        aStarMap.put(startIndex,(float) calculateHeuristicEuclideanDistance(G.getVertices(),startIndex,goalIndex));
//        while(!openList.isEmpty()){
//            currentIndex = openList.poll();
//            if (currentIndex == goalIndex)
//                break;
//
//            neighborEdges = G.getVertexEdges().get(currentIndex);
//
//            for (Edge edge:neighborEdges) {
//                endNode = edge.getToVertex().getIndex();
//
//                if (closedList.contains(endNode))
//                    continue;
//                cost = dijkstraMap.get(currentIndex) + edge.getCost();// + (float) calculateHeuristicEuclideanDistance(G.getVertices(), currentIndex, goalIndex);
//                if (aStarMap.containsKey(endNode)) {
//                    if (cost <= aStarMap.get(endNode)){
//                        if (openList.contains(endNode))
//                            openList.remove(endNode);
//                        aStarMap.put(endNode, cost);
//                        openList.add(endNode);
//                        path.put(endNode, currentIndex);
//                    }
//                }else{
//                    if (openList.contains(endNode))
//                        openList.remove(endNode);
//                    aStarMap.put(endNode, cost);
//                    openList.add(endNode);
//                    path.put(endNode, currentIndex);
//                }
//            }
//            closedList.add(currentIndex);
//        }
//        bestPath.add(goalIndex);
//        if(startIndex != goalIndex){
//            int prev = path.get(goalIndex);
//            while(prev != startIndex){
//                bestPath.add(prev);
//                prev = path.get(prev);
//            }
//        }
//        bestPath.add(startIndex);
//        Collections.reverse(bestPath);
//    }

    public double calculateHeuristicEuclideanDistance(Map<Integer,Vertex> vertices,int vertex1, int vertex2){
        return Math.sqrt(Math.pow((vertices.get(vertex2).getPos().x - vertices.get(vertex1).getPos().x),2) + Math.pow((vertices.get(vertex2).getPos().y - vertices.get(vertex1).getPos().y),2));
    }

    public float calculateHeuristicManhattan(Map<Integer,Vertex> vertices,int vertex1, int vertex2){
        return Math.abs(vertices.get(vertex2).getPos().x - vertices.get(vertex1).getPos().x) + Math.abs(vertices.get(vertex2).getPos().y - vertices.get(vertex1).getPos().y);
    }

    public class compareDijkstraPaths implements Comparator<Integer>{

        public int compare(Integer x, Integer y){
            return Float.compare(dijkstraMap.get(x),dijkstraMap.get(y));
        }
    }

    public class compareAstarPaths implements  Comparator<Integer>{
        public int compare(Integer x, Integer y){
            return Float.compare(aStarMap.get(x),aStarMap.get(y));
        }
    }
}

