package fall2018.csc207_project.game2048;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import fall2018.csc207_project.GameCenter.Game;

public class Game2048 extends Game implements Serializable {

    private static final long serialVersionUID = 772895212901L;

    private Board board;  // settings index 0
    private int complexity;
    private int undoStep; // settings index 2
    private int score;     // settings index 1
    private int highestTile;
    private boolean moveAvailable;
    private LinkedList<Board> undoList = new LinkedList<>(); // setting index 3


    Game2048(int complexity){
        this.board = new Board(complexity);
        this.complexity = complexity;
        this.score = 0;
    }

    int getUndoListSize(){
        return this.undoList.size();
    }

    void setUndoStep(int undoStep) {
        this.undoStep = undoStep;
    }

    public int getUndoStep(){return undoStep;}

    public LinkedList<Board> getUndoList(){return undoList;}

    private void addToUndoList(Board board){
        if (this.undoList.size() < this.undoStep){
            this.undoList.add(board);
        }else{
            this.undoList.removeFirst();
            this.undoList.add(board);
        }
    }

    void undo(){
        if (this.undoList.size() != 0) {
            this.board = undoList.removeLast();
        }
    }

    @Override
    public List<Object> getSetting() {
        //TODO: LATER
        return null;
    }

    public int getHighestTile(){return highestTile;}

    public int getComplexity() {
        return complexity;
    }

    public Board getBoard(){
        return board;
    }
    public int getScore(){return score;}
    private boolean doMove(int countDownFrom, int yDirection, int xDirection) {
        boolean moved = false;
        int target = 2048;

        for (int i = 0; i < complexity * complexity; i++) {
            int j = Math.abs(countDownFrom - i);

            int r = j / complexity;
            int c = j % complexity;

            if (board.getTile(r,c).getNum() == 0)
                continue;

            int nextR = r + xDirection;
            int nextC = c + yDirection;

            while (nextR >= 0 && nextR < complexity && nextC >= 0 && nextC < complexity) {

                Tile next = board.getTile(nextR,nextC);
                Tile curr = board.getTile(r,c);

                if (next.getNum() == 0) {

                    if (moveAvailable)
                        return true;

                    next.setNum(curr.getNum());
                    curr.setNum(0);
                    r = nextR;
                    c = nextC;
                    nextR += xDirection;
                    nextC += yDirection;
                    moved = true;

                } else if (next.equals(curr) && !next.getMergedState()) {

                    if (moveAvailable)
                        return true;

                    int value = next.merge(curr);
                    if (value > highestTile)
                        highestTile = value;
                    score += value;
                    board.getTile(r,c).setNum(0);
                    moved = true;
                    break;

                } else
                    break;
            }
        }


        if (moved) {
            if (highestTile < target) {
                board.clearMerged();
                board.addRandomTile();
                if (!movesAvailable()) {
                    System.out.println("Game Over!");
                }
            } else if (highestTile == target)
                System.out.println("You Won!");

        }

        System.out.println("Your Score: -----------------------------> "+score+"!!!!!!!!!!!!!!!!!!!");

        return moved;
    }

    public boolean touchUp() {
        return doMove(0,-1,0);
    }

    public boolean touchDown() {
        return doMove(complexity * complexity - 1,1,0);
    }

    public boolean touchLeft() {
        return doMove(0,0,-1);
    }

    public boolean touchRight() {
        return doMove(complexity * complexity - 1, 0, 1);
    }


    public boolean movesAvailable(){
        moveAvailable = true;
        boolean hasMoves = touchUp() || touchDown() || touchLeft() || touchRight();
        moveAvailable = false;
        return hasMoves;
    }


}
