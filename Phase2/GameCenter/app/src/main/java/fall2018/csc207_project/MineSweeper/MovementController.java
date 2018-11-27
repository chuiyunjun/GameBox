package fall2018.csc207_project.MineSweeper;

import android.content.Context;
import android.widget.Toast;

public class MovementController {
    private MineSweeperGame game;


    MovementController(){}

    public void setGame(MineSweeperGame game) {
        this.game = game;
    }

    public MineSweeperGame getGame(){return game;}

    public void flip(Context context, int row, int col){
        game.flipTile(row,col);
        Tile tile = game.getBoard().getTileTable()[row][col];
        if(tile.getNum()>=10&&tile.isFliped()){
            Toast.makeText(context, "GAME OVER!", Toast.LENGTH_SHORT).show();
        }else{
            boolean win = true;
            Tile[][] boardTable = game.getBoard().getTileTable();
            int boardSize = game.getBoard().getBoardSize();
            int x = 0;
            int y = 0;
            while(x != boardSize&&win){
                while(y!= boardSize&&win){
                    Tile t = boardTable[x][y];
                    if(! (t.isFliped() && t.getNum() < 10 ||!t.isFliped()&&t.getNum()>=10)){
                        win = false;
                    }
                    y++;
                }
                x++;
            }

            if(win){Toast.makeText(context, "YOU WIN", Toast.LENGTH_SHORT).show();}
        }

    }

    public void changeLabelState(int row, int col){
        game.labelTile(row,col);
    }
}
