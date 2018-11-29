package fall2018.csc207_project.game2048;


import android.content.Context;
import android.widget.Toast;

import java.io.Serializable;


public class MovementController{

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
                Toast.makeText(context, "GAME OVER!", Toast.LENGTH_SHORT).show();
                game.notifyScoreBoard();
            }
        }

        boolean has2048 = game.getHighestTile() >= TARGET;

        if(has2048){
            Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            game.notifyScoreBoard();
        }
        return hasMoved;
    }

    public void setGame(Game2048 game) {
        this.game = game;
    }

    void undo(){
        game.undo();
    }
    public void restart(){
        game.restart();
    }
}