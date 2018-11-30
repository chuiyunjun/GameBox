package fall2018.csc207_project.SlidingTileGame;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;


public class MovementController {

    private SlidingTileGame slidingTileGame = null;

    MovementController() {
    }

    public void setSlidingTileGame(SlidingTileGame slidingTileGame) {
        this.slidingTileGame = slidingTileGame;
    }

    void processTapMovement(Context context, int position, boolean display) {
        if (slidingTileGame.isValidTap(position)) {
            slidingTileGame.touchMove(position);
            if (slidingTileGame.puzzleSolved()) {
                String message = "YOU WIN!";
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                slidingTileGame.notifyScoreBoard();
                endGame(context, message);
            }
        }
        else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
    void undo(){
        slidingTileGame.undo();
    }

    void toastNoMoreUndo(Context context){
        Toast.makeText(context, "No more undo!", Toast.LENGTH_SHORT).show();
    }

    /**
     * end the game after playing it by popping up a dialog
     * @param context game activity
     * @param message message for making toast
     */
    private void endGame(final Context context, final String message) {
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
