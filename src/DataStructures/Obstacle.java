package DataStructures;

import processing.core.PVector;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by mohz2 on 3/26/2017.
 */
public class Obstacle {

    public Obstacle(){
        this.tilePositions = new ArrayList<>();
        this.invalidTiles = new HashSet<>();
    }

    public Obstacle(PVector firstTilePosition,int width, int height, PVector obstacleColor, int horizontalTiles, int verticalTiles){
        this();
        this.firstTile = firstTilePosition;
        this.width = width;
        this.height = height;
        this.color = obstacleColor;
        for(int i = (int)firstTilePosition.x; i <= (int)firstTilePosition.x + width; i++){
            for(int j = (int)firstTilePosition.y; j <= (int)firstTilePosition.y + height; j++){
                this.tilePositions.add(new PVector(i,j));
                this.invalidTiles.add(i*horizontalTiles+j);
            }
        }
    }


    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public PVector getColor() {
        return color;
    }

    public void setColor(PVector color) {
        this.color = color;
    }

    public ArrayList<PVector> getTilePositions() {
        return tilePositions;
    }

    public void setTilePositions(ArrayList<PVector> tilePositions) {
        this.tilePositions = tilePositions;
    }

    public HashSet<Integer> getInvalidTiles() {
        return invalidTiles;
    }

    public void setInvalidTiles(HashSet<Integer> invalidTiles) {
        this.invalidTiles = invalidTiles;
    }

    public PVector getFirstTile() {
        return firstTile;
    }

    public void setFirstTile(PVector firstTile) {
        this.firstTile = firstTile;
    }

    private PVector firstTile;
    private float width, height;
    private PVector color;
    private ArrayList<PVector> tilePositions;
    private HashSet<Integer> invalidTiles; // for creating edges

}
