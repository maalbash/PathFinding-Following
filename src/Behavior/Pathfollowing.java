package Behavior;

import processing.core.*;
import DataStructures.*;
import java.util.*;

/**
 * Created by mohz2 on 3/23/2017.
 */
public class Pathfollowing extends PApplet {
    PShape fullShape,body,head,breadCrumb;
    Agent player;
    GameObject initTarget;
    PVector initPos;
    Smotion Sarrive;
    Align Salign;
    ArrayList<PVector> crumbs;
    long crumbTime, time;
    Graph myGraph;
    ClassRoom buildingGameAI;
    int width = 800;
    int height = 800;
    int HORIZONTAL_TILES = 50;
    int VERTICAL_TILES = 50;

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
        initPos = new PVector(width/2 - 25, height/2 - 25);
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
    }

    public void initEnvironment(){
        myGraph = new Graph(HORIZONTAL_TILES,VERTICAL_TILES);
        buildingGameAI = new ClassRoom(width,height,HORIZONTAL_TILES,VERTICAL_TILES);
        buildingGameAI.createObstacles();
        buildingGameAI.setTileSize(50f);
        for(Obstacle obstacle:buildingGameAI.getObstacles()) {
            myGraph.getInvalidVertices().addAll(obstacle.getInvalidTiles());
        }
        myGraph.buildGraph();
    }

    public void settings(){
        size(800,800);
    }

    public void setup(){
        initEnvironment();
        initPlayer();
    }

    public void draw(){
        background(255);
        time++;
        drawEnvironment();
        drawMovement();
    }

    public void drawEnvironment(){
        //drawObstacles
        for(int i = 0; i < HORIZONTAL_TILES; i++) {
            for (int j = 0; j < VERTICAL_TILES; j++) {
                rect(i*buildingGameAI.getTileSize(),j*buildingGameAI.getTileSize(),buildingGameAI.getTileSize(),buildingGameAI.getTileSize());
            }
        }

    }

    public void drawMovement(){
        Sarrive.getSteering();
        Salign.getSteering();
        if (Salign.orientationReached()) {
            player.setRotation(0);
            player.setAngular(0);
        }
        player.update();

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
        //PVector tpos = new PVector((int)(mouseX/buildingGameAI.getTileSize()),(int)(mouseY/buildingGameAI.getTileSize()));
        PVector tpos = new PVector(mouseX,mouseY);
        GameObject target = new GameObject(tpos,0);

        PVector dir = PVector.sub(tpos,player.getPosition());
        Sarrive.setTarget(target);
        Salign.setTarget(target);
        Salign.getTarget().setOrientation(dir.heading());
    }

    public static void main(String args[]) {
        PApplet.main( new String[]{"Behavior.Pathfollowing"});
    }
}
