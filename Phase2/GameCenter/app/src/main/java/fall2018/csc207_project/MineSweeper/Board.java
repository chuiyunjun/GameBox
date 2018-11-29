package fall2018.csc207_project.MineSweeper;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Board implements Serializable, Iterable<Tile> {

    private static final long serialVersionUID = 2657091L;

    private Tile[][] tileTable;
    private int boardSize =10;
    private int bombNum;


    Board(int bombNum){
        tileTable = new Tile[boardSize][boardSize];
        addTiles();
        addBombs(bombNum);
    }

    Board(Board board) {
        tileTable = new Tile[boardSize][boardSize];
        for(int y=0;y<boardSize;y++) {
            for(int x=0;x<boardSize;x++) {
                tileTable[x][y] = new Tile(board.getTileTable()[x][y]);
            }
        }
        this.boardSize = board.getBoardSize();
        this.bombNum = board.getBombNum();
    }


    Tile[][] getTileTable(){
        return tileTable;
    }

    int getBoardSize() {
        return boardSize;
    }

    public int getBombNum() {
        return this.bombNum;
    }

    private void addTiles(){
        Tile tile;
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                tile = new Tile(0);
                tileTable[x][y] = tile;
            }
        }
    }


    private void addBombs(int bombNum){
        LinkedList<Integer> numList = new LinkedList<>();
        for(int index=0; index!= boardSize*boardSize;index++){
            numList.add(index);
        }
        for(int bombCount=0;bombCount != bombNum;bombCount++){
            this.bombNum = bombNum;

            int randomIndex = (int) (Math.random()*numList.size());
            int num = numList.get(randomIndex);
            int row = num/boardSize;
            int col = num%boardSize;
            tileTable[row][col].setNum(10);
            numList.remove(randomIndex);

            if(row != 0){addTileNum(row - 1,col);}
            if(row < boardSize - 1){addTileNum(row + 1,col);}
            if(col != 0){addTileNum(row,col - 1);}
            if(col < boardSize - 1){addTileNum(row,col + 1);}
            if(row != 0&&col != 0){addTileNum(row - 1,col - 1);}
            if(row != 0&&col < boardSize - 1){addTileNum(row - 1,col + 1);}
            if(row < boardSize - 1 && col != 0){addTileNum(row + 1,col - 1);}
            if(row < boardSize - 1 && col < boardSize-1){addTileNum(row + 1,col + 1);}
        }
    }

    private void addTileNum(int row,int col){
        int num = tileTable[row][col].getNum();
        if(num < 10){ tileTable[row][col].setNum(num + 1);}
    }

    @Override
    @NonNull
    public Iterator<Tile> iterator() {
        return new TileIterator();
    }

    /**
     * An Iterator for Tile class
     */
    private class TileIterator implements Iterator<Tile> {

        /**
         * The index of the next Tile to return.
         */
        private int current = 0;

        @Override
        public boolean hasNext() {
            return current < boardSize * boardSize;
        }

        public Tile next() {
            // The Tile which are supposed to be return.
            Tile target;
            // The row and column position of the Tile.
            int row, col;

            row = current / boardSize;
            col = current % boardSize;
            try {
                target = tileTable[row][col];
            } catch (IndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
            current += 1;
            return target;
        }
    }

}
