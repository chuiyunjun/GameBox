package fall2018.csc207_project.game2048;


import android.graphics.Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Board implements Serializable {

    private static final long serialVersionUID = 99472526420521L;

    private int boardSize;
    private Tile[][] tileTable;
    private List<Point> emptyPoints = new ArrayList<>();


    Board(int complexity){
        this.boardSize = complexity;
        tileTable = new Tile[boardSize][boardSize];;
        addTiles();
        addRandomTile();
        addRandomTile();

    }

    public void setTileTable(Tile[][] newTiles){
        this.tileTable = newTiles;
    }

    public Tile[][] getTileTable(){
        return this.tileTable;
    }

    public Tile getTile(int x, int y) {
        return tileTable[x][y];
    }

    public void setBoardSize(int size){
        this.boardSize = size;
    }

    public int getBoardSize(){
        return this.boardSize;
    }

    public void clearMerged(){
        for(int y=0;y<boardSize;y++) {
            for(int x=0;x<boardSize;x++) {
                tileTable[x][y].setMergedState(false);
            }
        }
    }

    private void addTile(int num, int x, int y) {
        tileTable[x][y].setNum(num);
    }

    public void addRandomTile() {

        emptyPoints.clear();

        for (int y=0;y<boardSize;y++) {
            for (int x=0;x<boardSize;x++) {
                if(tileTable[x][y].getNum() <= 0)
                    emptyPoints.add(new Point(x,y));
            }
        }

        Point p = emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
        tileTable[p.x][p.y].setNum(Math.random()>0.1?2:4);
    }


    private  void clearTileTable() {
        for (int y = 0; y < boardSize; y++) {
            for (int x = 0; x < boardSize; x++) {
                tileTable[x][y].setNum(0);
            }
        }
    }

    private void addTiles(){
        Tile tile;
        for (int y = 0; y < boardSize; y++) {
            for (int x = 0; x < boardSize; x++) {
                tile = new Tile(0);
                tileTable[x][y] = tile;
            }
        }
    }
}