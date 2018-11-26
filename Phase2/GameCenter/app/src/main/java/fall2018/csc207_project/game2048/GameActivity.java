package fall2018.csc207_project.game2048;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
    Game2048 game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_2048_activity);
        globalCenter = (GlobalCenter) (getIntent().getSerializableExtra("GlobalCenter"));
        LocalGameCenter localGameCenter = globalCenter.getLocalGameCenter(globalCenter.getCurrentPlayer().getUsername());
        gameView = findViewById(R.id.gameView);
        game = (Game2048) localGameCenter.getCurGame();
        gameView.setGame(game);
        game.addObserver(this);

        setUndoButtonListener();

        initTextView();


    }


    public void onBackPressed() {
        Intent tmp = new Intent(this, StartingActivity.class);
        tmp.putExtra("GlobalCenter", globalCenter);
        startActivity(tmp);
        finish();
    }

    private void initTextView() {
        TextView score = findViewById(R.id.current_score);
        TextView highest = findViewById(R.id.highest_score);
        TextView undoStepLeft = findViewById(R.id.undo_step_left_2048);
        score.setText("Score\n"+game.getScore());
        highest.setText("Best\n"+0);
        undoStepLeft.setText("("+game.getUndoStep()+")");

    }

    public void setUndoButtonListener() {
        Button button = findViewById(R.id.undo_button_2048);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.undo();
            }
        });

    }

    @Override
    public void update(Observable o,Object arg) {
        TextView score = findViewById(R.id.current_score);
        TextView undoStepLeft = findViewById(R.id.undo_step_left_2048);
        score.setText("Score\n"+game.getScore());
        undoStepLeft.setText("("+game.getUndoStep()+")");
        gameView.updateDisplay();
    }
}
