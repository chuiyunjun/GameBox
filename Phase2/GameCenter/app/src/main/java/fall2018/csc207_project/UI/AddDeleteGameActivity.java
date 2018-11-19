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
import fall2018.csc207_project.R;

import java.util.Set;

public class AddDeleteGameActivity extends AppCompatActivity {

    private GlobalCenter globalCenter;
    private LocalGameCenter localCenter;
    private boolean state;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_delete_game);
        state = getIntent().getBooleanExtra("addGame?", true);
        globalCenter = (GlobalCenter) (getIntent().getSerializableExtra("GlobalCenter"));
        localCenter = globalCenter.getLocalGameCenter(globalCenter.getCurrentPlayer().getUsername());

        initTextView();
        if(state)
            addState();
        else
            deleteState();

    }

    private void initTextView() {
        if (!state) {
            TextView addDeleteBand = findViewById(R.id.add_delete_band);
            TextView tapBand = findViewById(R.id.tap_band);

            addDeleteBand.setText(R.string.gamesToDelete);
            tapBand.setText(R.string.tapToDelete);
        }
    }


    private void addState() {
        Set<String> gameSet = localCenter.getGames();

        if (gameSet.contains("slidingTileGame"))
            findViewById(R.id.sliding_tile_button).setVisibility(View.GONE);
        else
            addSlidingTileGameListener();

        if (gameSet.contains("game2048"))
            findViewById(R.id.game2048_button).setVisibility(View.GONE);
        else
            addGame2048Listener();

        if (gameSet.contains("minesweeperGame"))
            findViewById(R.id.snake_button).setVisibility(View.GONE);
        else
            addSnakeGameListener();

    }

    private void addSlidingTileGameListener() {
        final Button button = findViewById(R.id.sliding_tile_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localCenter.addGame("slidingTileGame");
                button.setVisibility(View.GONE);
            }
        });
    }

    private void addGame2048Listener() {
        final Button button = findViewById(R.id.game2048_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localCenter.addGame("game2048");
                button.setVisibility(View.GONE);
            }
        });
    }

    private void addSnakeGameListener() {
        final Button button = findViewById(R.id.snake_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localCenter.addGame("minesweeperGame");
                button.setVisibility(View.GONE);
            }
        });
    }

    private void deleteState() {
        Set<String> gameSet = localCenter.getGames();
        LinearLayout ll = findViewById(R.id.add_delete_game_button_layout);
        ll.removeAllViews();

        for (String s : gameSet) {
            final Button tmp = new Button(this);
            ll.addView(tmp);
            if (s.equals("slidingTileGame")) {
                tmp.setText(R.string.slidingTileGame);
                tmp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        localCenter.removeGame("slidingTileGame");
                        tmp.setVisibility(View.GONE);
                    }
                });
            } else if (s.equals("game2048")) {
                tmp.setText(R.string.game2048);
                tmp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        localCenter.removeGame("game2048");
                        tmp.setVisibility(View.GONE);
                    }
                });
            } else if (s.equals("minesweeperGame")) {
                tmp.setText(R.string.mineSweeperGame);
                tmp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        localCenter.removeGame("minesweeperGame");
                        tmp.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    public void onBackPressed() {
        Intent tmp = new Intent(this, LocalCenterActivity.class);
        tmp.putExtra("GlobalCenter", globalCenter);
        startActivity(tmp);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        globalCenter.saveAll(getApplicationContext());
    }

}
