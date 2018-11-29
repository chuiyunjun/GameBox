package fall2018.csc207_project.MineSweeper;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc207_project.GameCenter.GlobalCenter;
import fall2018.csc207_project.GameCenter.LocalGameCenter;
import fall2018.csc207_project.GameCenter.ScoreBoard;
import fall2018.csc207_project.R;
import fall2018.csc207_project.UI.StartingActivity;

/**
 *
 * activity performs game
 *
 * timer adapted from https://github.com/lany192/Minesweeper.git
 * resource of images for tiles from https://github.com/marcellelek/Minesweeper
 *
 */
public class GameActivity extends AppCompatActivity implements Observer {
    private GlobalCenter globalCenter;
    private LocalGameCenter localCenter;
    private BoardView gameView;

    TextView mineCountText;
    TextView timerCountText;
    TextView helpLeft;

    private Handler timer = new Handler();

    MineSweeperGame game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_mine_sweeper);

        globalCenter = (GlobalCenter) (getIntent().getSerializableExtra("GlobalCenter"));
        localCenter = globalCenter.getLocalGameCenter(globalCenter.
                getCurrentPlayer().getUsername());
        ScoreBoard scoreBoard = (MineSweeperScoreBoard)globalCenter.getScoreBoards().get(localCenter.getCurGameName());
        game = (MineSweeperGame) localCenter.getCurGame();
        gameView = findViewById(R.id.grid);
        gameView.setGame(game);
        game.setPlayer(globalCenter.getCurrentPlayer().getUsername());

        gameView.setTableImage();
        game.addObserver(this);
        game.addObserver(scoreBoard);

        this.mineCountText = findViewById(R.id.bomb);
        this.timerCountText = findViewById(R.id.time);
        this.helpLeft = findViewById(R.id.help_left);

        timerCountText.setText(String.valueOf(game.getSecondsPassed()));
        mineCountText.setText(String.valueOf(game.getFlagsLeft()));
        String helpAvailable = "(" + String.valueOf(game.getHelp() ? 1 : 0) + ")";
        helpLeft.setText(helpAvailable);

        addHelpButtonListener();

        if (game.getSecondsPassed() < 999) {
            startTimer();
        }
    }

    /**
     * start timing
     */
    public void startTimer() {

        timer.removeCallbacks(updateTimeElasped);
        // tell timer to run call back after 1 second
        timer.postDelayed(updateTimeElasped, 1000);

    }

    /**
     * stop timing
     */
    public void stopTimer() {
        // disable call backs
        timer.removeCallbacks(updateTimeElasped);
    }

    // timer call back when timer is ticked
    private Runnable updateTimeElasped = new Runnable() {
        public void run() {
            long currentMilliseconds = System.currentTimeMillis();
            int secondsPassed = game.getSecondsPassed();

            ++secondsPassed;

            game.setSecondPassed(secondsPassed);

            timerCountText.setText(String.valueOf(secondsPassed));

            // add notification
            timer.postAtTime(this, currentMilliseconds);
            // notify to call back after 1 seconds
            // basically to remain in the timer loop
            timer.postDelayed(updateTimeElasped, 1000);

            if (secondsPassed >= 999) {
                stopTimer();
            }
        }
    };

    private void updateFlagsLeft() {
        int flagsLeft = game.getFlagsLeft();
        mineCountText.setText(String.valueOf(flagsLeft));
    }

    @Override
    public void update(Observable o, Object arg){
        if (arg instanceof Integer)
            gameView.setTileImage((int) arg);
        if (game.getGameOver()) {
            stopTimer();
        }
        updateFlagsLeft();
        autoSave();
    }

    private void autoSave() {
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                .format(Calendar.getInstance().getTime());
        globalCenter.getLocalGameCenter(globalCenter.getCurrentPlayer().getUsername()).
                autoSave(timeStamp);
    }

    public void addHelpButtonListener(){
        Button helpButton = findViewById(R.id.help);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!game.getGameOver()) {
                    if (game.getHelp()) {
                        helpLeft.setText("(0)");
                        gameView.getMController().helpPressed();
                    } else {
                            gameView.getMController().toastHelpUsedUp(getApplicationContext());
                    }
                }
            }
        });
    }

    public void onBackPressed() {
        Intent tmp = new Intent(this, StartingActivity.class);
        tmp.putExtra("GlobalCenter", globalCenter);
        startActivity(tmp);
        finish();
    }
}
