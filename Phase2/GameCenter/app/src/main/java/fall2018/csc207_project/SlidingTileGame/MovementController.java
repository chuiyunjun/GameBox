package fall2018.csc207_project.SlidingTileGame;

import android.content.Context;
import android.widget.Toast;


public class MovementController {

    private SlidingTileGame slidingTileGame = null;

    public MovementController() {
    }

    public void setSlidingTileGame(SlidingTileGame slidingTileGame) {
        this.slidingTileGame = slidingTileGame;
    }

    void processTapMovement(Context context, int position, boolean display) {
        if (slidingTileGame.isValidTap(position)) {
            slidingTileGame.touchMove(position);
            if (slidingTileGame.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
                slidingTileGame.notifyScoreBoard();
            }
        }
        else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
    public void undo(){
        slidingTileGame.undo();
    }

    public void toastNoMoreUndo(Context context){
        Toast.makeText(context, "No more undo!", Toast.LENGTH_SHORT).show();
    }
}
