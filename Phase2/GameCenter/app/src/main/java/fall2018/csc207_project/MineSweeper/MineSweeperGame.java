package fall2018.csc207_project.MineSweeper;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import fall2018.csc207_project.GameCenter.Game;

public class MineSweeperGame extends Game implements Serializable {

    private static final long serialVersionUID = 314346L;

    private Board board;
    private int secondPassed = 0;

    private int flagsLeft;

    private boolean announced = false;
    private boolean gameOver = false;
    private boolean win = false;
    private boolean help = true;

    public MineSweeperGame(List<Object> settings) {
        if (settings.size() == 1)
            initWithComplexity((Integer) settings.get(0));
        else {
            this.board = new Board((Board) (settings.get(0))); // TODO: Shallow copy
            this.secondPassed = (Integer) (settings.get(1));
            this.flagsLeft = (Integer) (settings.get(2));
            this.announced = (Boolean) (settings.get(3));
            this.gameOver = (Boolean) (settings.get(4));
            this.win = (Boolean) (settings.get(5));
            this.help = (Boolean) (settings.get(6));
        }
    }

    private void initWithComplexity(int complexity) {
        board = new Board(complexity);
        flagsLeft = complexity;
    }

    @Override
    public List<Object> getSetting() {
        List<Object> settings = new LinkedList<>();
        settings.add(new Board(this.board));
        settings.add(this.secondPassed);
        settings.add(flagsLeft);
        settings.add(announced);
        settings.add(gameOver);
        settings.add(win);
        settings.add(help);

        return settings;
    }

    void setSecondPassed(int secondPassed) {
        this.secondPassed = secondPassed;
    }

    void setAnnounced() {
        this.announced = true;
    }

    Board getBoard(){return board;}

    int getSecondsPassed() {
        return this.secondPassed;
    }

    int getFlagsLeft() {
        return this.flagsLeft;
    }

    boolean getWin() {
        return this.win;
    }

    boolean getGameOver() {
        return this.gameOver;
    }

    boolean getHelp() {
        return help;
    }

    boolean hasAnnounced(){
        return this.announced;
    }

    void flipTile(int row, int col){
        board.getTileTable()[row][col].setFliped();
        int boardSize = board.getBoardSize();
        if(board.getTileTable()[row][col].getNum() == 0){
            if(row != 0 && !board.getTileTable()
                    [row - 1][col].isFliped()){flipTile(row - 1,col);}
            if(row < boardSize - 1 && !board.getTileTable()
                    [row + 1][col].isFliped()){flipTile(row + 1,col);}
            if(col != 0 && !board.getTileTable()
                    [row][col - 1].isFliped()){flipTile(row,col - 1);}
            if(col < boardSize - 1 && !board.getTileTable()
                    [row][col + 1].isFliped()){flipTile(row,col + 1);}
            if(row != 0 && col != 0 && !board.getTileTable()
                    [row - 1][col - 1].isFliped()){flipTile(row - 1,col - 1);}
            if(row != 0 && col<boardSize - 1 && !board.getTileTable()
                    [row - 1][col + 1].isFliped()){flipTile(row - 1,col + 1);}
            if(row <boardSize - 1 && col != 0 && !board.getTileTable()
                    [row + 1][col - 1].isFliped()){flipTile(row + 1,col - 1);}
            if(row <boardSize - 1 && col < boardSize - 1 && !board.getTileTable()
                    [row + 1][col + 1].isFliped()){flipTile(row + 1,col + 1);}
        }
        if(board.getTileTable()[row][col].getNum() == 10){
            gameOver = true;
        }
        setChanged();
        notifyObservers(row*boardSize+col);
    }

    void checkEnd() {
        int boardSize = board.getBoardSize();
        int notRevealed = boardSize * boardSize;
        int bombNotFound = board.getBombNum();
        for (Tile tile : board) {
            if (tile.isFliped() || tile.isLabeled()) {
                notRevealed--;
            }
            if (tile.isLabeled() && tile.getNum() >= 10) {
                bombNotFound--;
            }
        }
        if (notRevealed == 0 && bombNotFound == 0) {
            win = true;
            gameOver = true;

            setChanged();
            notifyObservers();
        }
    }


   void labelTile(int row, int col){
            if (board.getTileTable()[row][col].isLabeled()) {
                flagsLeft++;
                board.getTileTable()
                        [row][col].setLabeled(!board.getTileTable()[row][col].isLabeled());
            } else if (!board.getTileTable()
                    [row][col].isLabeled() && flagsLeft > 0) {
                flagsLeft--;
                board.getTileTable()
                        [row][col].setLabeled(!board.getTileTable()[row][col].isLabeled());
            }
            int boardSize= board.getBoardSize();
            setChanged();
            notifyObservers(row*boardSize+col);
   }

   void help() {
        int position = 0;
        for (Tile tile : board) {
            if (!tile.isFliped() && tile.getNum() > 0 && tile.getNum() < 10) {
                int row = position / board.getBoardSize();
                int col = position % board.getBoardSize();
                if (tile.isLabeled()) {
                    labelTile(row, col);
                }
                flipTile(row, col);
                break;
            }
            position++;
        }
        help = false;
   }
}
