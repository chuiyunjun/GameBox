package fall2018.csc207_project.MineSweeper;


import java.util.LinkedList;
import java.util.Observable;

public class Board{
    private Tile[][] tileTable;
    private int boardSize =10;
    private int bombNum;


    Board(int bombNum){
        tileTable = new Tile[boardSize][boardSize];
        addTiles();
        addBombs(bombNum);
        
    }


    public Tile[][] getTileTable(){
        return tileTable;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void addTiles(){
        Tile tile;
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                tile = new Tile(0);
                tileTable[x][y] = tile;
            }
        }
    }


    public void addBombs(int bombNum){
        LinkedList numList = new LinkedList();
        for(int i=0; i!= boardSize*boardSize;i++){
            numList.add(i);
        }
        for(int j=0;j != bombNum;j++){
            int randomIndex = (int)(Math.random()* numList.size());
            int num = (int) numList.get(randomIndex);
            int row = num/boardSize;
            int col = num%boardSize;
            tileTable[row][col].setNum(10);
            numList.remove(randomIndex);

            if(row!=0){addTileNum(row-1,col);}
            if(row<boardSize-1){addTileNum(row+1,col);}
            if(col!=0){addTileNum(row,col-1);}
            if(col<boardSize-1){addTileNum(row,col+1);}
            if(row!=0&&col!=0){addTileNum(row-1,col-1);}
            if(row!=0&&col<boardSize-1){addTileNum(row-1,col+1);}
            if(row<boardSize-1&&col!=0){addTileNum(row+1,col-1);}
            if(row<boardSize-1&&col<boardSize-1){addTileNum(row+1,col+1);}
        }
    }

    public void addTileNum(int row,int col){
        int num = tileTable[row][col].getNum();
        if( num<10){ tileTable[row][col].setNum(num+1);}
    }

}
