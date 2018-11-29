package fall2018.csc207_project.MineSweeper;

import android.content.Context;
import android.widget.Toast;

/**
 * control the change of the game model from the command of the activity and view
 */
class MovementController {

    /**
     * model of the game
     */
    private MineSweeperGame game;

    /**
     * default constructor
     */
    MovementController(){}

    /**
     * set up the game model
     * @param game game model
     */
    void setGame(MineSweeperGame game) {
        this.game = game;
    }

    /**
     * get the game model
     * @return the game model
     */
    MineSweeperGame getGame(){return game;}

    /**
     * flip the tile and process the move
     * @param context game activity
     * @param row row index of the tile
     * @param col column index of the tile
     */
    void flip(Context context, int row, int col) {
        //lock the game if the game ends
        if (!game.getGameOver()) {
            game.flipTile(row, col);
            Tile tile = game.getBoard().getTileTable()[row][col];
            //fail if a bomb is flipped, and flip all tiles
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
        checkIfEnd(context);
    }

    /**
     * change the if the tile is flagged
     * @param context game activity
     * @param row row index of the tile
     * @param col column index of the tile
     */
    void changeLabelState(Context context, int row, int col) {
        if (!game.getGameOver()) {
            game.labelTile(row, col);
        }
        checkIfEnd(context);
    }

    /**
     * process help function if the user press help
     */
    void helpPressed(){
       game.help();
    }

    /**
     * tell the player there is no help left
     * @param context game activity
     */
    void toastHelpUsedUp(Context context){
        Toast.makeText(context, "Help used up!",
                Toast.LENGTH_SHORT).show();
    }

    /**
     * store time used in the game data model
     * @param newTime time used
     */
    void changeTime(int newTime){
        game.setSecondPassed(newTime);
    }

    /**
     * check if the game has ended and the user has won
     * @param context game activity
     */
    private void checkIfEnd(Context context) {
        game.checkEnd();
        //if the user wins and win toast has been made
        //if win toast has been made, then it will not be made
        if (game.getWin() && game.hasAnnouncedInverted()) {
            Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            game.setAnnounced();
            game.notifyScoreBoard();
        }
    }
}
