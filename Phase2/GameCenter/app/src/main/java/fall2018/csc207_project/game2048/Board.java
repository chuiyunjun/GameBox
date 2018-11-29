package fall2018.csc207_project.game2048;


import android.graphics.Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Board implements Serializable {

    private static final long serialVersionUID = 99472526420521L;

    private int boardSize;
    private Tile[][] tileTable;
    private List<Integer> blankTileList = new LinkedList<>();


    Board(int complexity){
        this.boardSize = complexity;
        tileTable = new Tile[boardSize][boardSize];;
        addTiles();
        addRandomTile();
        addRandomTile();
//        addTile(2048,0,0);
//        addTile(2048,0,1);
    }

    Board(Board board) {
        this.boardSize = board.getBoardSize();
        tileTable = new Tile[boardSize][boardSize];
        for(int y=0;y<boardSize;y++) {
            for(int x=0;x<boardSize;x++) {
                tileTable[x][y] = new Tile(board.getTile(x,y).getNum());
            }
        }
        blankTileList = new LinkedList<>();
        blankTileList.addAll(board.getBlankTileList());
    }

    public void setTileTable(Tile[][] newTiles){
        this.tileTable = newTiles;
    }

    public List<Integer> getBlankTileList() {
        return this.blankTileList;
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

    public void syncBoard(List<Integer> list) {
        int count = 0;
        for(int y=0;y<boardSize;y++) {
            for (int x=0;x<boardSize;x++) {
                tileTable[x][y].setNum(list.get(count));
                count+=1;
            }
        }
    }


    public void addRandomTile() {
        blankTileList.clear();

        for(int i=0;i < boardSize*boardSize;i++) {
            if(tileTable[i/boardSize][i%boardSize].getNum() <= 0)
                blankTileList.add(i);
        }
        int randomIndex = (int)(Math.random()*blankTileList.size());
        int point = blankTileList.get(randomIndex);
        tileTable[point/boardSize][point%boardSize].setNum(Math.random()>0.1?2:4);
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