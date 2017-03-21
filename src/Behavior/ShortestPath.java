package Behavior;

import DataStructures.*;
/**
 * Created by mohz2 on 3/21/2017.
 */
public class ShortestPath {
    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    private Integer start,end;

    public void Dijkstra(Graph G){
        Integer startIndex = getStart();
        Integer goalIndex = getEnd();
    }

    public void Astar(Graph G){
        Integer startIndex = getStart();
        Integer goalIndex = getEnd();
    }
}
