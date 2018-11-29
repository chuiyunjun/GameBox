package fall2018.csc207_project.MineSweeper;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import fall2018.csc207_project.GameCenter.ScoreBoard;

public class MineSweeperScoreBoard extends ScoreBoard implements Serializable {

    private static final long serialVersionUID = 8271998400642L;
    private final int MAXIMUM = 1000;

    @Override
    public int calculateScore(List<Object> setting) {
        if ((boolean)setting.get(5)) {
            Board board = new Board((Board) (setting.get(0)));
            return (int)((MAXIMUM - (Integer) (setting.get(1))) *
                    ((float)board.getBombNum() / 20));
        } else {
            return 0;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof LinkedList) {
            LinkedList<Object> temp = (LinkedList<Object>)arg;
            int score = calculateScore((List<Object>)(temp.getLast()));
            this.addNewScore((String)temp.getFirst(),score);
        }
    }


}
