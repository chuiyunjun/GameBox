package fall2018.csc207_project.MineSweeper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Observable;
import java.util.Observer;

import fall2018.csc207_project.R;

public class GameActivity extends AppCompatActivity implements Observer {
    //private GlobalCenter globalCenter;
    private BoardView gameView;
    MineSweeperGame game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_mine_sweeper);
        game = new MineSweeperGame(10);
        gameView = findViewById(R.id.grid);
        gameView.setGame(game);
        game.addObserver(this);

    }

    @Override
    public void update(Observable o, Object arg){
        gameView.setTileImage((int)arg);
    }
}
