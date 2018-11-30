package fall2018.csc207_project.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import fall2018.csc207_project.GameCenter.GlobalCenter;
import fall2018.csc207_project.GameCenter.LocalGameCenter;
import fall2018.csc207_project.MineSweeper.MineSweeperGame;
import fall2018.csc207_project.R;
import fall2018.csc207_project.SlidingTileGame.SlidingTileGame;
import fall2018.csc207_project.game2048.Game2048;

import java.util.Set;

public class LocalCenterActivity extends AppCompatActivity {

    private GlobalCenter globalCenter;
    private LocalGameCenter localCenter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(fall2018.csc207_project.R.layout.local_center);
        globalCenter = (GlobalCenter) (getIntent().getSerializableExtra("GlobalCenter"));
        localCenter = globalCenter.getLocalGameCenter(globalCenter.getCurrentPlayer().getUsername());
        localCenter.setCurGame(null);

        TextView welcome = findViewById(fall2018.csc207_project.R.id.welcome);
        welcome.setText("Welcome!\nuser: " + globalCenter.getCurrentPlayer().getUsername());
        loadButtons();

        addAddGameButtonListener();
        addDeleteGameButtonListener();
        addLogOffButtonListener();

    }

    private void loadButtons() {

        Set<String> gameSet = localCenter.getGames();
        LinearLayout buttonLayout = findViewById(fall2018.csc207_project.R.id.local_center_button_layout);
        Button tmp;

        for(String s:gameSet) {
            tmp = new Button(this);
            buttonLayout.addView(tmp);
            if(s.equals(SlidingTileGame.GAMENAME)) {
                tmp.setText(fall2018.csc207_project.R.string.slidingTileGame);
                tmp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        localCenter.setCurGameName(SlidingTileGame.GAMENAME);
                        gameLaunch();
                    }
                });
            }
            else if(s.equals(Game2048.GAMENAME)) {
                tmp.setText(fall2018.csc207_project.R.string.game2048);
                tmp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        localCenter.setCurGameName(Game2048.GAMENAME);
                        gameLaunch();
                    }
                });
            }
            else if(s.equals(MineSweeperGame.GAMENAME)) {
                tmp.setText(fall2018.csc207_project.R.string.mineSweeperGame);
                tmp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        localCenter.setCurGameName(MineSweeperGame.GAMENAME);
                        gameLaunch();
                    }
                });
            }
        }
    }

    private void addAddGameButtonListener() {
        Button button = findViewById(fall2018.csc207_project.R.id.add_game_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToAddDelete(true);
            }
        });
    }

    private void switchToAddDelete(boolean add) {
        Intent tmp = new Intent(this, AddDeleteGameActivity.class);
        tmp.putExtra("addGame?", add);
        tmp.putExtra("GlobalCenter", globalCenter);
        startActivity(tmp);
        finish();
    }

    private void addDeleteGameButtonListener() {
        Button button = findViewById(fall2018.csc207_project.R.id.delete_game_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToAddDelete(false);

            }
        });
    }

    private void gameLaunch() {
        Intent tmp = new Intent(this, StartingActivity.class);
        tmp.putExtra("GlobalCenter", globalCenter);
        startActivity(tmp);
        finish();
    }

    private void addLogOffButtonListener() {
        Button button = findViewById(R.id.log_off_button);
        globalCenter.signOut();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        globalCenter.saveAll(getApplicationContext());
    }

    public void onBackPressed() {
        Intent tmp = new Intent(this, GlobalActivity.class);
        tmp = tmp.putExtra("GlobalCenter", globalCenter);
        startActivity(tmp);
        finish();
    }
}
