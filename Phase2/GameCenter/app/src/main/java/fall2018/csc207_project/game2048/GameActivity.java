package fall2018.csc207_project.game2048;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fall2018.csc207_project.R;

public class GameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

}
