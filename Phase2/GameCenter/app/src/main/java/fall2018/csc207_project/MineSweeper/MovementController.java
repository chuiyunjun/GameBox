package fall2018.csc207_project.MineSweeper;

import android.content.Context;
import android.widget.Toast;

class MovementController {
    private MineSweeperGame game;

    MovementController(){}

    void setGame(MineSweeperGame game) {
        this.game = game;
    }

    MineSweeperGame getGame(){return game;}

    void flip(Context context, int row, int col) {
        if (!game.getGameOver()) {
            game.flipTile(row, col);
            Tile tile = game.getBoard().getTileTable()[row][col];
            if (tile.getNum() >= 10 && tile.isFliped()) {
                tile.setBoom();
                for(int rowNum = 0; rowNum < game.getBoard().getBoardSize(); rowNum++) {
                    for(int colNum = 0; colNum < game.getBoard().getBoardSize(); colNum++) {
                        game.flipTile(rowNum, colNum);
                    }
                }
                Toast.makeText(context, "GAME OVER!", Toast.LENGTH_SHORT).show();
            }
        }
        game.checkEnd();
        if (game.getWin() && !game.hasAnnounced()) {
            Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            game.setAnnounced();
        }
    }

    void changeLabelState(Context context, int row, int col) {
        if (!game.getGameOver()) {
            game.labelTile(row, col);
        }
        game.checkEnd();
        if (game.getWin() && !game.hasAnnounced()) {
            Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            game.setAnnounced();
        }
    }
}
