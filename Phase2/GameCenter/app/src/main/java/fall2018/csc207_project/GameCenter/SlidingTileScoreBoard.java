package fall2018.csc207_project.GameCenter;


import java.io.Serializable;
import java.util.List;

/**
 * The SlidingTileScoreBoard
 */

public class SlidingTileScoreBoard extends ScoreBoard implements Serializable {
    private static final long serialVersionUID = 6666L;
    /**
     * calculate scores based on setting
     * @param  setting the boardSize at index 0 and moves at index 1 of slidingTileGame
     * @return score based on setting
     * Precondition: boardSize need to be at index 0 and number of moves took should at index 1
     */

    @Override
    public int calculateScore(List<Object> setting) {
        int maxScore = 1000;
        Integer boardSize = (Integer) setting.get(0);
        Integer moves = (Integer) setting.get(2);
        int roughScore = 0;
        if(boardSize.equals(5)){
            roughScore = maxScore - moves;
        } else if(boardSize.equals(4)){
            roughScore = maxScore - 3 * moves;
        } else if(boardSize.equals(3)){
            roughScore = maxScore - 5 * moves;
        }
        if (roughScore > 300){
            return roughScore;
        }
        else {
            return 300;
        }
    }
}