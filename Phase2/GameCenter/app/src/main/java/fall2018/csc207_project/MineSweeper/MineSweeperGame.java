package fall2018.csc207_project.MineSweeper;

import java.util.Observable;

public class MineSweeperGame extends Observable {
    private Board board;
    private long time;

    MineSweeperGame(int bombNum){
        this.time = 0;
        board = new Board(bombNum);
    }

    public Board getBoard(){return board;}


    public void flipTile(int row, int col){
        board.getTileTable()[row][col].setFliped(true);
        int boardSize = board.getBoardSize();
        if(board.getTileTable()[row][col].getNum() == 0){
            if(row!=0&&!board.getTileTable()[row-1][col].isFliped()){flipTile(row-1,col);}
            if(row<boardSize-1&&!board.getTileTable()[row+1][col].isFliped()){flipTile(row+1,col);}
            if(col!=0&&!board.getTileTable()[row][col-1].isFliped()){flipTile(row,col-1);}
            if(col<boardSize-1&&!board.getTileTable()[row][col+1].isFliped()){flipTile(row,col+1);}
            if(row!=0&&col!=0&&!board.getTileTable()[row-1][col-1].isFliped()){flipTile(row-1,col-1);}
            if(row!=0&&col<boardSize-1&&!board.getTileTable()[row-1][col+1].isFliped()){flipTile(row-1,col+1);}
            if(row<boardSize-1&&col!=0&&!board.getTileTable()[row+1][col-1].isFliped()){flipTile(row+1,col-1);}
            if(row<boardSize-1&&col<boardSize-1&&!board.getTileTable()[row+1][col+1].isFliped()){flipTile(row+1,col+1);}
        }
        setChanged();
        notifyObservers(row*boardSize+col);
    }


    public void labelTile(int row, int col){
        board.getTileTable()[row][col].setLabeled(!board.getTileTable()[row][col].isLabeled());
        setChanged();
        int boardSize = board.getBoardSize();
        notifyObservers(row*boardSize+col);
    }
}

