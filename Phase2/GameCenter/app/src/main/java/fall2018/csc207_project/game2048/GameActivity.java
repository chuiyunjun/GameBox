package fall2018.csc207_project.game2048;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc207_project.GameCenter.Game;
import fall2018.csc207_project.GameCenter.GlobalCenter;
import fall2018.csc207_project.GameCenter.LocalGameCenter;
import fall2018.csc207_project.GameCenter.ScoreBoard;
import fall2018.csc207_project.R;
import fall2018.csc207_project.UI.StartingActivity;

public class GameActivity extends AppCompatActivity implements Observer {

    private GlobalCenter globalCenter;
    private BoardView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_2048_activity);
        globalCenter = (GlobalCenter) (getIntent().getSerializableExtra("GlobalCenter"));
        LocalGameCenter localGameCenter = globalCenter.getLocalGameCenter(globalCenter.getCurrentPlayer().getUsername());
        gameView = findViewById(R.id.gameView);
        Game2048 game = (Game2048) localGameCenter.getCurGame();
        gameView.setGame(game);
        game.addObserver(this);

    }


    public void onBackPressed() {
        Intent tmp = new Intent(this, StartingActivity.class);
        tmp.putExtra("GlobalCenter", globalCenter);
        startActivity(tmp);
        finish();
    }

    @Override
    public void update(Observable o,Object arg) {
        System.out.println("UPDATE SUCCESS!!!!!!!!!!!!!!!");
    }
}
