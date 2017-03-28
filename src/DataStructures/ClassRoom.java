package DataStructures;

import processing.core.PVector;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by mohz2 on 3/26/2017.
 */
public class ClassRoom {

    public ClassRoom(){
        this.obstacles = new ArrayList<>();
        this.tileSize = new PVector();
        this.invalidTiles = new HashSet<>();
    }

    public ClassRoom(int width, int height,int horizontalTiles, int verticalTiles){
        this();
        this.width = width;
        this.height = height;
        this.horizontalTiles = horizontalTiles;
        this.verticalTiles = verticalTiles;
        this.tileSize.x = width/verticalTiles;
        this.tileSize.y = height/horizontalTiles;
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    public void setObstacles(ArrayList<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public PVector getTileSize() {
        return tileSize;
    }

    public void setTileSize(PVector tileSize) {
        this.tileSize = tileSize;
    }


    public int getHorizontalTiles() {
        return horizontalTiles;
    }

    public void setHorizontalTiles(int horizontalTiles) {
        this.horizontalTiles = horizontalTiles;
    }

    public int getVerticalTiles() {
        return verticalTiles;
    }

    public void setVerticalTiles(int verticalTiles) {
        this.verticalTiles = verticalTiles;
    }

    public void createObstacles(){
        this.obstacles.add(new Obstacle(new PVector(0.2f * horizontalTiles,0.05f * verticalTiles), 30,4,new PVector(170,0,0),horizontalTiles,verticalTiles)); //left wall
        this.obstacles.add(new Obstacle(new PVector(0.2f * horizontalTiles, 0.2f * verticalTiles), 4,30,new PVector(170,0,0),horizontalTiles,verticalTiles)); //back seats
        this.obstacles.add(new Obstacle(new PVector(0.4f * horizontalTiles,0.2f * verticalTiles), 4,30,new PVector(170,0,0),horizontalTiles,verticalTiles)); //mid seats
        this.obstacles.add(new Obstacle(new PVector(0.6f * horizontalTiles,0.2f * verticalTiles), 4,30,new PVector(170,0,0),horizontalTiles,verticalTiles)); //front seats
        this.obstacles.add(new Obstacle(new PVector(0.1f * horizontalTiles,0.88f * verticalTiles), 30,4,new PVector(170,0,0),horizontalTiles,verticalTiles)); //right wall
        this.obstacles.add(new Obstacle(new PVector(0.8f * horizontalTiles,0.5f * verticalTiles), 3,15,new PVector(0,0,0),horizontalTiles,verticalTiles)); //teacher desk front
        this.obstacles.add(new Obstacle(new PVector(0.87f * horizontalTiles,0.75f * verticalTiles), 7,3,new PVector(0,0,0),horizontalTiles,verticalTiles)); //teacher desk side
    }

    public void populateInvalidList(){
        for(Obstacle obstacle:this.getObstacles()) {
            this.invalidTiles.addAll(obstacle.getInvalidTiles());
        }
    }


    private ArrayList<Obstacle> obstacles;
    private int width;
    private int height;
    private int horizontalTiles;
    private int verticalTiles;
    private PVector tileSize;

    public HashSet<Integer> getInvalidTiles() {
        return invalidTiles;
    }

    public void setInvalidTiles(HashSet<Integer> invalidTiles) {
        this.invalidTiles = invalidTiles;
    }

    private HashSet<Integer> invalidTiles;
}
