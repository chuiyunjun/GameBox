package fall2018.csc207_project.game2048;


import java.io.Serializable;

public class Tile implements Serializable {

    private static final long serialVersionUID = 732521258574L;

    private int num;
    private boolean mergedState;

    Tile(int number){
        this.num = number;
        this.mergedState = false;
    }

    void setNum(int number){
        this.num = number;
    }

    int getNum(){
        return this.num;
    }

    void setMergedState(){
        this.mergedState = false;
    }

    boolean getMergedState(){
        return this.mergedState;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Tile)
            return this.getNum() == ((Tile)o).getNum();
        else
            return super.equals(o);
    }

    int merge(Tile tile) {
        if(this.equals(tile)) {
            this.mergedState = true;
            this.setNum(num *= 2);
            return num;
        }
        return -1;
    }
}
