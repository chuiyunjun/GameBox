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
    private Game2048 game;
    private Button scoreButton;
    private Button highestScoreButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        globalCenter = (GlobalCenter) (getIntent().getSerializableExtra("GlobalCenter"));
        LocalGameCenter localGameCenter = globalCenter.getLocalGameCenter(globalCenter.getCurrentPlayer().getUsername());
        LinkedList<Object> settings = new LinkedList<>();
        game = (Game2048) localGameCenter.newGame("Game2048", settings);
        game = new Game2048(4);
        localGameCenter.setCurGameName("Game2048");
        setContentView(R.layout.game_2048_activity);
//        addRestartButtonListener();
//        addUndoButtonListener();
//        initTextView();
        scoreButton = findViewById(R.id.current_score);
        scoreButton.setText("" + game.getScore());
        highestScoreButton = findViewById(R.id.highest_score);
        //TODO according to scoreboard

        game.addObserver(this);
    }

    private void addRestartButtonListener(){

    }

    private void addUndoButtonListener(){

    }
    private void initTextView(){}
    private void update(){
        updateBoard();
        updateHighestScore();
        updateScore();
    }

    private void updateBoard(){}
    private void updateHighestScore(){}
    private void updateScore(){}

    public void onBackPressed() {
        Intent tmp = new Intent(this, StartingActivity.class);
        tmp.putExtra("GlobalCenter", globalCenter);
        startActivity(tmp);
        finish();
    }

    @Override
    public void update(Observable o,Object arg) {
        scoreButton.setText(game.getScore());


        String curUserName = globalCenter.getCurrentPlayer().getUsername();
        Map m = globalCenter.getScoreBoards();
        ScoreBoard scoreBoard = (ScoreBoard) m.get("2048Game");
        int topScore = scoreBoard.getUserTopScore(curUserName);
        highestScoreButton.setText(topScore);

        LocalGameCenter localGameCenter = globalCenter.getLocalGameCenter(globalCenter.getCurrentPlayer().getUsername());
        LinkedList<List<Object>> GameSettings = (LinkedList<List<Object>>) localGameCenter.getLocalGames().get("2048Game");
        List<Object> curGameSettings = GameSettings.get(3);
        curGameSettings.set(0, game);
        curGameSettings.set(1, game.getScore());
        curGameSettings.set(2, game.getUndoStep());
        curGameSettings.set(3, game.getUndoList());

//TODO update scoreboard of 2048


    }
}
