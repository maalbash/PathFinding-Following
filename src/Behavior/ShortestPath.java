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
    private HashSet<Integer> closedList;

    public void Dijkstra(Graph G){
        int startIndex = getStart(),goalIndex = getEnd();
        int currentIndex, endNode;
        float cost = 0;
        openList = new PriorityQueue<>(PathComparatorDijkstra);
        ArrayList<Edge> neighborEdges;
        openList.add(startIndex);
        dijkstraMap.put(startIndex,0f);

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

                if(cost <= dijkstraMap.get(endNode) || !dijkstraMap.containsKey(endNode)){
                    if(openList.contains(endNode))
                        openList.remove(endNode);
                    dijkstraMap.put(endNode,cost);
                    openList.add(endNode);
                    path.put(endNode,currentIndex);
                }
            }
            openList.remove(currentIndex);
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

    public void Astar(Graph G){
        int startIndex = getStart(),goalIndex = getEnd(), currentIndex, endNode;
        float cost = 0;
        openList = new PriorityQueue<>(PathComparatorAstar);
        ArrayList<Edge> neighborEdges;
        openList.add(startIndex);
        aStarMap.put(startIndex,(float) calculateHeuristicEuclideanDistance(G.getVertices(),startIndex,goalIndex));
        while(!openList.isEmpty()){
            currentIndex = openList.poll();
            if (currentIndex == goalIndex)
                break;

            neighborEdges = G.getVertexEdges().get(currentIndex);

            for (Edge edge:neighborEdges){
                endNode = edge.getToVertex().getIndex();

                if (closedList.contains(endNode))
                    continue;
                cost = aStarMap.get(currentIndex) + edge.getCost() + (float) calculateHeuristicEuclideanDistance(G.getVertices(),currentIndex,goalIndex);
                    if(cost <= aStarMap.get(endNode) || !aStarMap.containsKey(endNode)){
                        if(openList.contains(endNode))
                            openList.remove(endNode);
                        aStarMap.put(endNode,cost);
                        openList.add(endNode);
                        path.put(endNode,currentIndex);
                    }
            }
            openList.remove(currentIndex);
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

    public double calculateHeuristicEuclideanDistance(Map<Integer,Vertex> vertices,int x, int y){
        return Math.sqrt(Math.pow((vertices.get(x).getPos().x - vertices.get(y).getPos().x),2) + Math.pow((vertices.get(x).getPos().y - vertices.get(y).getPos().y),2));
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

