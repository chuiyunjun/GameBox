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

import java.util.Set;

public class LocalCenterActivity extends AppCompatActivity {

    private GlobalCenter globalCenter;
    private LocalGameCenter localCenter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(fall2018.csc207_project.R.layout.local_center);
        globalCenter = (GlobalCenter) (getIntent().getSerializableExtra("GlobalCenter"));
        localCenter = globalCenter.getLocalGameCenter(globalCenter.getCurrentPlayer().getUsername());

        TextView welcome = findViewById(fall2018.csc207_project.R.id.welcome);
        welcome.setText("Welcome!\nuser: " + globalCenter.getCurrentPlayer().getUsername());
        loadButtons();

        addAddGameButtonListener();
        addDeleteGameButtonListener();

    }

    private void loadButtons() {
        final String slidingTileGame = "slidingTileGame";
        final String game2048 = "game2048";
        final String minesweeperGame = "minesweeperGame";

        Set<String> gameSet = localCenter.getGames();
        LinearLayout buttonLayout = findViewById(fall2018.csc207_project.R.id.local_center_button_layout);
        Button tmp;

        for(String s:gameSet) {
            tmp = new Button(this);
            buttonLayout.addView(tmp);
            if(s.equals(slidingTileGame)) {
                tmp.setText(fall2018.csc207_project.R.string.slidingTileGame);
                tmp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        localCenter.setCurGameName(slidingTileGame);
                        gameLaunch();
                    }
                });
            }
            else if(s.equals(game2048)) {
                tmp.setText(fall2018.csc207_project.R.string.game2048);
                tmp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        localCenter.setCurGameName(game2048);
                        gameLaunch();
                    }
                });
            }
            else if(s.equals(minesweeperGame)) {
                tmp.setText(fall2018.csc207_project.R.string.mineSweeperGame);
                tmp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

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
