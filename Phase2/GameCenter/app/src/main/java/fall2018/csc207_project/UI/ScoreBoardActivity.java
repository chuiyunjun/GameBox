package fall2018.csc207_project.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import fall2018.csc207_project.GameCenter.GlobalCenter;
import fall2018.csc207_project.GameCenter.LocalGameCenter;
import fall2018.csc207_project.GameCenter.ScoreBoard;
import fall2018.csc207_project.GameCenter.SlidingTileScoreBoard;
import fall2018.csc207_project.R;

public class ScoreBoardActivity extends AppCompatActivity {

    private GlobalCenter globalCenter;
    private boolean state;
    private ScoreBoard scoreboard;
    private LinearLayout canvas;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard);
        globalCenter = (GlobalCenter) (getIntent().getSerializableExtra("GlobalCenter"));
        state = getIntent().getBooleanExtra("perPlayer?", true);
        LocalGameCenter localCenter = globalCenter.getLocalGameCenter(globalCenter.getCurrentPlayer().getUsername());
        scoreboard = (SlidingTileScoreBoard) globalCenter.getScoreBoards().get(localCenter.getCurGameName());
        canvas = findViewById(R.id.score_board_canvas);

        TextView band = findViewById(R.id.score_board_band);
        if(!state) {
            band.setText(R.string.global_score_band);
            displayGlobalScore();
        } else
            displayPlayerScore();

    }

    public void displayPlayerScore() {
        Integer[] playerScorers = scoreboard.getPlayerScore(globalCenter.getCurrentPlayer().getUsername());
        for(int x=0;x<playerScorers.length;x++) {
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);

            TextView rank = new TextView(this);
            TextView name = new TextView(this);
            TextView score = new TextView(this);

            generateTextView(rank,"1");
            generateTextView(name, "Admin");
            generateTextView(score,"2345");

            ll.addView(rank);
            ll.addView(name);
            ll.addView(score);

            canvas.addView(ll);
        }
    }

    private void generateTextView(TextView view, String text) {
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f
        );
        view.setText(text);
        view.setTextSize(21);
        view.setGravity(Gravity.CENTER);
        view.setLayoutParams(param);
    }


    public void displayGlobalScore() {

    }

    public void onBackPressed() {
        Intent tmp = new Intent(this, StartingActivity.class);
        tmp.putExtra("GlobalCenter", globalCenter);
        startActivity(tmp);
        finish();
    }



}
