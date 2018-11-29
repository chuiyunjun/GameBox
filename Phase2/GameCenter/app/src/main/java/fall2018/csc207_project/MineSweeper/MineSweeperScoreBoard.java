package fall2018.csc207_project.MineSweeper;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import fall2018.csc207_project.GameCenter.ScoreBoard;

public class MineSweeperScoreBoard extends ScoreBoard implements Serializable {

    private static final long serialVersionUID = 8271998400642L;

    @Override
    public int calculateScore(List<Object> setting) {
        return -(Integer)(setting.get(1));
    }

    @Override
    public void update(Observable o, Object arg) {
        LinkedList<Object> temp = (LinkedList<Object>)arg;
        int score = calculateScore((List<Object>)(temp.getLast()));
        this.addNewScore((String)temp.getFirst(),score);
    }
}
