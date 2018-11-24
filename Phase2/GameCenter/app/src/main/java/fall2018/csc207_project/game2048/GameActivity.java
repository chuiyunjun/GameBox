package fall2018.csc207_project.game2048;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fall2018.csc207_project.GameCenter.GlobalCenter;
import fall2018.csc207_project.GameCenter.LocalGameCenter;
import fall2018.csc207_project.R;
import fall2018.csc207_project.UI.StartingActivity;

public class GameActivity extends AppCompatActivity {
    private GlobalCenter globalCenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        globalCenter = (GlobalCenter) (getIntent().getSerializableExtra("GlobalCenter"));
        LocalGameCenter localGameCenter = globalCenter.getLocalGameCenter(globalCenter.getCurrentPlayer().getUsername());
        setContentView(R.layout.game_2048_activity);
//        addRestartButtonListener();
//        addUndoButtonListener();
//        initTextView();

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

}
