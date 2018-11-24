package fall2018.csc207_project.game2048;


import android.content.Context;
import android.widget.Toast;



public class MovementController {

    private Game2048 game;

    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;
    public static final int TARGET = 2048;
    public static final int COMPLEXITY = 4;


    MovementController(int complexity){
        this.game = new Game2048(complexity);
    }

    public Game2048 getGame(){
        return this.game;
    }
    public boolean processMovement(Context context, int direction){
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
            if(!game.movesAvailable()){Toast.makeText(context, "GAME OVER!", Toast.LENGTH_SHORT).show();}
        }

        boolean has2048 = game.getHighestTile() >= TARGET;

        if(has2048){
            Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
        }
        return hasMoved;
    }
}