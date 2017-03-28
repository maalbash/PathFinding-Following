package DataStructures;

import processing.core.PVector;

import java.util.ArrayList;

/**
 * Created by mohz2 on 3/26/2017.
 */
public class ClassRoom {

    public ClassRoom(){
        this.obstacles = new ArrayList<>();
        this.tileSize = new PVector();
    }

    public ClassRoom(int width, int height,int horizontalTiles, int verticalTiles){
        this();
        this.width = width;
        this.height = height;
        this.horizontalTiles = horizontalTiles;
        this.verticalTiles = verticalTiles;
        this.tileSize.x = width/horizontalTiles;
        this.tileSize.y = height/verticalTiles;
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
        this.obstacles.add(new Obstacle(new PVector(50,50), 1,8,new PVector(255,0,0),horizontalTiles,verticalTiles)); //left wall
        this.obstacles.add(new Obstacle(new PVector(width/4,height/4), 5,2,new PVector(255,0,0),horizontalTiles,verticalTiles)); //back seats
        this.obstacles.add(new Obstacle(new PVector(width/4,height/2), 5,2,new PVector(255,0,0),horizontalTiles,verticalTiles)); //front seats
        this.obstacles.add(new Obstacle(new PVector(width - 100,50), 1,8,new PVector(255,0,0),horizontalTiles,verticalTiles)); //right wall
        this.obstacles.add(new Obstacle(new PVector(width - 300,height - 200), 3,1,new PVector(255,0,0),horizontalTiles,verticalTiles)); //teacher desk front
        this.obstacles.add(new Obstacle(new PVector(width - 100,height - 200), 1,8,new PVector(255,0,0),horizontalTiles,verticalTiles)); //teacher desk side
    }


    private ArrayList<Obstacle> obstacles;
    private int width;
    private int height;
    private int horizontalTiles;
    private int verticalTiles;
    private PVector tileSize;
}
