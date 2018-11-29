package fall2018.csc207_project.GameCenter;

import java.util.List;

import fall2018.csc207_project.MineSweeper.MineSweeperGame;
import fall2018.csc207_project.SlidingTileGame.SlidingTileGame;
import fall2018.csc207_project.game2048.Game2048;

public class GameFactory {
    public static final String SLIDINGTILEGAME = "slidingTileGame";
    public static final String GAME2048 = "game2048";
    public static final String MINESWEEPER = "minesweeperGame";

    public Game createGame(String gameName, List<Object> settings) {
        if(gameName.equals(SLIDINGTILEGAME))
            return new SlidingTileGame(settings);
        else if(gameName.equals(GAME2048)) {
            return new Game2048(settings);
        }
        else if(gameName.equals(MINESWEEPER)) {
            return new MineSweeperGame(settings);
        }
        return null;
    }
}
