package fall2018.csc207_project.MineSweeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.LinkedList;

import fall2018.csc207_project.GameCenter.GlobalCenter;
import fall2018.csc207_project.GameCenter.LocalGameCenter;
import fall2018.csc207_project.R;
import fall2018.csc207_project.SlidingTileGame.SlidingTileGame;
import fall2018.csc207_project.UI.StartingActivity;


public class GameComplexityActivity extends Activity {

    private GlobalCenter globalCenter;
    private LocalGameCenter localCenter;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complexity);
        globalCenter = (GlobalCenter) (getIntent().getSerializableExtra("GlobalCenter"));
        localCenter = globalCenter.getLocalGameCenter(globalCenter.getCurrentPlayer().getUsername());
        add10BombsButtonListener();
        add15BombsButtonListener();
        add20BombsButtonListener();

    }

//    private int numBombs(int bombNum){
//        return bombNum;
//    }

    private void add10BombsButtonListener(){
        Button l10Button = findViewById(R.id.grid10);
        l10Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame(10);
                finish();
            }
        });
    }

    private void add15BombsButtonListener(){
        Button l15Button = findViewById(R.id.grid15);
        l15Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame(15);
                finish();
            }
        });
    }

    private void add20BombsButtonListener(){
        Button l20Button = findViewById(R.id.grid20);
        l20Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame(20);
                finish();
            }
        });
    }

//    private void switchToGame(int numBombs) {
//        MineSweeperGame game = new MineSweeperGame(numBombs);
//        Intent tmp = new Intent(this, GameActivity.class);
//        tmp.putExtra("GlobalCenter", globalCenter);
//        tmp.putExtra("minesweeperGame",game);
//        startActivity(tmp);
//        finish();
//    }

    private void switchToGame(int complexity) {
        LinkedList<Object> settings = new LinkedList<>();
        settings.add(complexity);
        MineSweeperGame game = (MineSweeperGame) localCenter.newGame("minesweeperGame", settings);
        Intent tmp = new Intent(this, GameActivity.class);
        tmp.putExtra("GlobalCenter", globalCenter);
        localCenter.setCurGame(game);
        startActivity(tmp);
        finish();
    }

    public void onBackPressed() {
        Intent tmp = new Intent(this, StartingActivity.class);
        tmp.putExtra("GlobalCenter", globalCenter);
        startActivity(tmp);
        finish();
    }
}
