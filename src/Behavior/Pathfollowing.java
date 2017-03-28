package Behavior;

import processing.core.*;
import DataStructures.*;
import java.util.*;

/**
 * Created by mohz2 on 3/23/2017.
 */
public class Pathfollowing extends PApplet {
    PShape fullShape,body,head,breadCrumb,tile;
    Agent player;
    GameObject initTarget, updatedTarget;
    int startIndex, goalIndex;
    ShortestPath shortestPathAlgos;
    PVector initPos, currTargetPos;
    Smotion Sarrive;
    Align Salign;
    ArrayList<PVector> crumbs;
    long crumbTime, time;
    Graph myGraph;
    ClassRoom buildingGameAI;
    HashSet<Integer> closedList = new HashSet<>();
    int width = 1000;
    int height = 1000;
    int HORIZONTAL_TILES = 50;
    int VERTICAL_TILES = 50;
    int currTarget, pathOffset = 5;
    int iterForTargetCorrection = 0;
    ArrayList<PVector> bestPath = new ArrayList<>();

    public ArrayList<PVector> getNewPath(ArrayList<Integer> Path){
        ArrayList<PVector> newPath = new ArrayList<>();
        currTarget = 0;
        for(int index : Path){
            newPath.add(new PVector((index % VERTICAL_TILES) * buildingGameAI.getTileSize().x + buildingGameAI.getTileSize().x/2,(index / VERTICAL_TILES)*buildingGameAI.getTileSize().y + buildingGameAI.getTileSize().y/2));
        }
        currTargetPos = newPath.get(currTarget);
        return newPath;
    }

    public void playerUpdate(){
        if(bestPath == null || bestPath.size() == 0)
            return;
        if((PVector.sub(currTargetPos,player.getPosition())).mag() < 20f){
            if(currTarget < bestPath.size() - 1){
                if(currTarget + pathOffset <= bestPath.size() - 1)
                    currTarget += pathOffset;
                else
                    currTarget = bestPath.size()-1;

                currTargetPos = bestPath.get(currTarget);
            }
        }
        updatedTarget = new GameObject(currTargetPos,0);
        PVector dir = PVector.sub(currTargetPos,player.getPosition());
        Sarrive.setTarget(updatedTarget);
        Salign.setTarget(updatedTarget);
        Salign.getTarget().setOrientation(dir.heading());
        Sarrive.getSteering();
        Salign.getSteering();
        if (Salign.orientationReached() || (PVector.sub(updatedTarget.getPosition(),player.getPosition())).mag() < Sarrive.getRadiusOfSatisfaction()) {
            player.setRotation(0);
            player.setAngular(0);
        }
        player.update();
    }

    public void updateCrumbs(){
        if(crumbs.size() > 8)
        {
            crumbs.remove(0);
        }
        crumbs.add(player.getPosition().copy());
    }

    public void drawCrumbs(){
        for(PVector crumb: crumbs) {
            pushMatrix();
            translate(crumb.x,crumb.y);
            shape(breadCrumb);
            popMatrix();
        }
    }

    public void initPlayer(){
        initPos = new PVector( 25, 25);
        player = new Agent();
        initTarget = new GameObject();
        player.setPosition(initPos);
        player.setOrientation(0);
        initTarget.setPosition(initPos);
        initTarget.setOrientation(0);

        breadCrumb = createShape(ELLIPSE,0, 0 , 4, 4);
        breadCrumb.setFill(155);
        crumbs = new ArrayList<PVector>();

        body = createShape(ELLIPSE,0, 0, 20, 20);
        head = createShape(TRIANGLE,0 ,- 10, 0 , 10, 20, 0);
        fullShape = createShape(GROUP);
        fullShape.addChild(body);
        fullShape.addChild(head);
        body.setFill(0);
        head.setFill(0);

        crumbTime = 0;
        Sarrive = new Smotion(5.f,2.f,5.f,20.f, 2.f);
        Salign = new Align( PConstants.PI/50, PConstants.PI/30,PConstants.PI/15, PConstants.PI/2, 10f );
        Sarrive.setPlayer(player);
        Sarrive.setTarget(initTarget);
        Salign.setPlayer(player);
        Salign.setTarget(initTarget);
        currTargetPos = new PVector();
    }

    public void initEnvironment(){
        myGraph = new Graph(HORIZONTAL_TILES,VERTICAL_TILES);
        buildingGameAI = new ClassRoom(width,height,HORIZONTAL_TILES,VERTICAL_TILES);
        buildingGameAI.createObstacles();
        buildingGameAI.populateInvalidList();

        for(Obstacle obstacle:buildingGameAI.getObstacles()) {
            myGraph.getInvalidVertices().addAll(obstacle.getInvalidTiles());
        }
        myGraph.buildGraph();
        shortestPathAlgos = new ShortestPath(HORIZONTAL_TILES/2,VERTICAL_TILES/2);
        shortestPathAlgos.setDijkstra(false);
        tile = createShape(RECT,0,0,buildingGameAI.getTileSize().x,buildingGameAI.getTileSize().y);
                //rect(i*buildingGameAI.getTileSize().x,j*buildingGameAI.getTileSize().y,buildingGameAI.getTileSize().x,buildingGameAI.getTileSize().y);
    }

    public void settings(){
        size(width,height);
    }

    public void setup(){
        initEnvironment();
        initPlayer();
    }

    public void draw(){
        background(150);
        time++;
        drawEnvironment();
        drawMovement();
    }

    public void drawEnvironment(){
        //drawObstacles
        for(Obstacle obstacle : buildingGameAI.getObstacles())
        {
            for(int i : obstacle.getColoredTiles()) {
                pushMatrix();
                tile.setFill(color(obstacle.getColor().x, obstacle.getColor().y, obstacle.getColor().z));
                tile.setStroke(color(obstacle.getColor().x, obstacle.getColor().y, obstacle.getColor().z));
                translate((i % VERTICAL_TILES) * buildingGameAI.getTileSize().x, (i / VERTICAL_TILES) * buildingGameAI.getTileSize().y);
                shape(tile);
                popMatrix();
            }
        }
        //drawpath
        for(int i : shortestPathAlgos.getBestPath()) {
            pushMatrix();
            tile.setFill(color(0,0,255));
            tile.setStroke(color(0,0,255));
            translate((i % VERTICAL_TILES) * buildingGameAI.getTileSize().x, (i / VERTICAL_TILES) * buildingGameAI.getTileSize().y);
            shape(tile);
            popMatrix();
        }

    }

    public void drawMovement(){

        playerUpdate();

        if(time-crumbTime > 3)
        {
            updateCrumbs();
            crumbTime = time;
        }

        drawCrumbs();

        pushMatrix();
        translate(player.getPosition().x,player.getPosition().y);
        rotate(player.getOrientation());
        shape(fullShape);
        popMatrix();
    }

    public void mousePressed(){

        moveToMouseLoc();
    }

    public void mouseDragged(){
        moveToMouseLoc();
    }

    public void moveToMouseLoc(){
        bestPath.clear();
        closedList.clear();
        PVector tpos = new PVector((int)(mouseX/buildingGameAI.getTileSize().x),(int)(mouseY/buildingGameAI.getTileSize().y));
        iterForTargetCorrection = 1;
        int index = (int)(tpos.y * VERTICAL_TILES + tpos.x);
        while(buildingGameAI.getInvalidTiles().contains(index)){
           if(!buildingGameAI.getInvalidTiles().contains(index - iterForTargetCorrection)){
               index -= iterForTargetCorrection;
           }else if(!buildingGameAI.getInvalidTiles().contains(index + iterForTargetCorrection)){
               index += iterForTargetCorrection;
           }else if(!buildingGameAI.getInvalidTiles().contains(index - iterForTargetCorrection * VERTICAL_TILES)){
               index -= iterForTargetCorrection * VERTICAL_TILES;
           }else if(!buildingGameAI.getInvalidTiles().contains(index + iterForTargetCorrection * VERTICAL_TILES)){
               index += iterForTargetCorrection * VERTICAL_TILES;
           }else if(!buildingGameAI.getInvalidTiles().contains(index - iterForTargetCorrection * VERTICAL_TILES - iterForTargetCorrection)){
                index -= iterForTargetCorrection * VERTICAL_TILES - iterForTargetCorrection;
           }else if(!buildingGameAI.getInvalidTiles().contains(index - iterForTargetCorrection * VERTICAL_TILES + iterForTargetCorrection)){
                index -= iterForTargetCorrection * VERTICAL_TILES + iterForTargetCorrection;
           }else if(!buildingGameAI.getInvalidTiles().contains(index + iterForTargetCorrection * VERTICAL_TILES - iterForTargetCorrection)){
                index += iterForTargetCorrection * VERTICAL_TILES - iterForTargetCorrection;
           }else if(!buildingGameAI.getInvalidTiles().contains(index + iterForTargetCorrection * VERTICAL_TILES + iterForTargetCorrection)){
                index += iterForTargetCorrection * VERTICAL_TILES + iterForTargetCorrection;
           }
           iterForTargetCorrection++;
        }

        PVector playerVertex = new PVector((int)(player.getPosition().x/buildingGameAI.getTileSize().x),(int)(player.getPosition().y/buildingGameAI.getTileSize().y));

        shortestPathAlgos = new ShortestPath((int)(playerVertex.y * VERTICAL_TILES + playerVertex.x),index);
        shortestPathAlgos.FindPath(myGraph);
        closedList = shortestPathAlgos.getClosedList();
        bestPath = getNewPath(shortestPathAlgos.getBestPath());
        currTarget = 0;
    }

    public static void main(String args[]) {
        PApplet.main( new String[]{"Behavior.Pathfollowing"});
    }
}
