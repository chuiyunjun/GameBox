package fall2018.csc207_project.game2048;

import java.io.Serializable;
import java.util.ArrayList;
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
    private LinkedList<LinkedList<Integer>> undoList = new LinkedList<>(); // setting index 3


    public Game2048(List<Object> settings) {
        if(settings.size() == 1) {
            this.complexity = (Integer) settings.get(0);
            this.board = new Board(complexity);
            this.undoStep = 3;
            this.score = 0;

            addToUndoList();

        } else {
            this.board = new Board((Board)settings.get(0));
            this.complexity = (Integer) settings.get(1);
            this.undoStep = (Integer) settings.get(2);
            this.score = (Integer) settings.get(3);
            this.highestTile = (Integer) settings.get(4);
            this.moveAvailable = (Boolean) settings.get(5);
            this.undoList = new LinkedList<>();
            this.undoList.addAll((LinkedList)settings.get(6));


            addToUndoList();
        }

    }

    @Override
    public List<Object> getSetting() {
        LinkedList<Object> result = new LinkedList<>();
        result.add(new Board(board));
        result.add(complexity);
        result.add(undoStep);
        result.add(score);
        result.add(highestTile);
        result.add(moveAvailable);
        result.add(undoList);

        return result;
    }

    int getUndoListSize(){
        return this.undoList.size();
    }

    void setUndoStep(int undoStep) {
        this.undoStep = undoStep;
    }

    public int getUndoStep(){return undoStep;}

    public LinkedList<LinkedList<Integer>> getUndoList(){return undoList;}

    public void restart(){
        this.board = new Board(complexity);
        this.undoStep = 3;
        this.score = 0;
        this.undoList.clear();
        addToUndoList();
        setChanged();
        notifyObservers();
    }

    private void addToUndoList(){
        if(this.undoList.size()<this.undoStep+1)
            this.undoList.add(record(board));
        else {
            this.undoList.removeFirst();
            this.undoList.add(record(board));
        }
    }


    private LinkedList<Integer> record(Board board) {
        LinkedList<Integer> list = new LinkedList<>();
        for(int y=0;y<board.getBoardSize();y++) {
            for(int x=0;x<board.getBoardSize();x++) {
                list.add(board.getTile(x,y).getNum());
            }
        }
        list.add(score);
        return list;
    }


    void undo() {
        if(this.undoList.size()>=2 && undoStep !=0) {
            undoList.removeLast();
            LinkedList<Integer> temp = undoList.getLast();
            board.syncBoard(temp);
            undoStep-=1;
            this.score = temp.getLast();
            setChanged();
            notifyObservers();
        }
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

                addToUndoList();
                setChanged();
                notifyObservers();

                if (!movesAvailable()) {
                    System.out.println("Game Over!");
                }
            } else if (highestTile == target)
                System.out.println("You Won!");

        }
        
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
