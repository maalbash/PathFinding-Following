package DataStructures; /**
 * Created by mohz2 on 3/3/2017.
 */
import processing.core.PVector;

public class Vertex {

    public Vertex() {
        this.pos = new PVector();
    }

    public Vertex(int ID, int i, int j){
        this();
        this.index = ID;
        this.pos.x = i;
        this.pos.y = j;
    }

    public PVector getPos() {
        return pos;
    }

    public void setPos(PVector pos) {
        this.pos = pos;
    }

    private PVector pos;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    private Integer index;
}
