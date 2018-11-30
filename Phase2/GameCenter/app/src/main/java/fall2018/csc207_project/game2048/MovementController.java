package fall2018.csc207_project.game2048;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;


import fall2018.csc207_project.Interfaces.Game;
import fall2018.csc207_project.Interfaces.Controller;


public class MovementController implements Controller {

    private Game2048 game;
    private static final int UP = 1;
    private static final int DOWN = 2;
    private static final int LEFT = 3;
    private static final int RIGHT = 4;
    private static final int TARGET = 2048;


    MovementController(Game2048 game){
        this.game = game;
    }

    public Game2048 getGame(){
        return this.game;
    }
    boolean processMovement(Context context, int direction){
        System.out.println(game.getPlayer());
        boolean hasMoved = false;
        if(direction == UP){
            hasMoved = game.touchUp();
        }else if(direction == DOWN){
            hasMoved = game.touchDown();
        }else if(direction == LEFT){
            hasMoved = game.touchLeft();
        }else if(direction == RIGHT){
            hasMoved = game.touchRight();
        }

        if(!hasMoved){
            if(!game.movesAvailable()){
                String message = "GAME OVER!";
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                game.notifyScoreBoard();
                endGame(context, message);
            }
        }

        boolean has2048 = game.getHighestTile() >= TARGET;

        if(has2048){
            String message = "YOU WIN!";
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            game.notifyScoreBoard();
            endGame(context, message);
        }
        return hasMoved;
    }

    public void setGame(Game game) {
        this.game = (Game2048) game;
    }

    void undo(){
        game.undo();
    }

    public void restart(){
        game.restart();
    }

    /**
     * end the game after playing it by popping up a dialog
     * @param context game activity
     * @param message message for making toast
     */
    public void endGame(final Context context, final String message) {
        GameActivity gameActivity = (GameActivity) context;
        gameActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final GameActivity gameActivity = (GameActivity) context;
                if (!gameActivity.isFinishing()) {
                    new AlertDialog.Builder(context)
                            .setTitle(message)
                            .setMessage("You can check the scoreboard.")
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.
                                    OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    gameActivity.switchToScoreBoard();
                                }
                            }).show();
                }
            }
        });
    }
}