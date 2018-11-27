package fall2018.csc207_project.MineSweeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fall2018.csc207_project.R;


public class GameComplexityActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complexity);

        add10BombsButtonListener();
        add15BombsButtonListener();
        add20BombsButtonListener();


    }

    private int numBombs(int bombNum){
        return bombNum;
    }

    private void add10BombsButtonListener(){
        Button l10Button = findViewById(R.id.grid10);
        l10Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame(numBombs(10));
                finish();
            }
        });
    }

    private void add15BombsButtonListener(){
        Button l15Button = findViewById(R.id.grid15);
        l15Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame(numBombs(15));
                finish();
            }
        });
    }

    private void add20BombsButtonListener(){
        Button l20Button = findViewById(R.id.grid20);
        l20Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame(numBombs(20));
                finish();
            }
        });
    }

    private void switchToGame(int numBombs) {
        MineSweeperGame game = new MineSweeperGame(numBombs);
        Intent tmp = new Intent(this, GameActivity.class);
        tmp.putExtra("minesweeperGame",game);
        startActivity(tmp);
        finish();
    }
}
