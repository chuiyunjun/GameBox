package fall2018.csc207_project.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fall2018.csc207_project.GameCenter.GlobalCenter;
import fall2018.csc207_project.GameCenter.LocalGameCenter;
import fall2018.csc207_project.R;
import fall2018.csc207_project.SlidingTileGame.ComplexityActivity;
import fall2018.csc207_project.game2048.GameActivity;

public class StartingActivity extends AppCompatActivity {

    private GlobalCenter globalCenter;
    private LocalGameCenter localCenter;
    final String slidingTileGame = "slidingTileGame";
    final String game2048 = "game2048";
    final String minesweeperGame = "minesweeperGame";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globalCenter = (GlobalCenter) (getIntent().getSerializableExtra("GlobalCenter"));
        localCenter = globalCenter.getLocalGameCenter(globalCenter.getCurrentPlayer().getUsername());
        setContentView(fall2018.csc207_project.R.layout.game_page);
        initBand();

        addNewGameButtonListener();
        addLoadGameButtonListener();
        addSaveGameButtonListener();

    }

    private void initBand() {
        TextView band = findViewById(R.id.GameText);
        String gameName = localCenter.getCurGameName();
        if(gameName.equals(slidingTileGame))
            band.setText(R.string.sliding_tile_band);
        else if(gameName.equals(game2048))
            band.setText(R.string.game_2048_band);

    }

    public void onBackPressed() {
        Intent tmp = new Intent(this, LocalCenterActivity.class);
        tmp.putExtra("GlobalCenter", globalCenter);
        startActivity(tmp);
        finish();
    }

    public void addNewGameButtonListener() {

        Button button = findViewById(fall2018.csc207_project.R.id.new_game_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(localCenter.getCurGameName().equals(slidingTileGame))
                    switchToComplexity();
                else if(localCenter.getCurGameName().equals(game2048))
                    switchTo2048();
            }
        });
    }

    public void addLoadGameButtonListener() {
        Button button = findViewById(fall2018.csc207_project.R.id.load_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGameSlot(true);
            }
        });
    }

    public void addSaveGameButtonListener() {
        Button button = findViewById(fall2018.csc207_project.R.id.save_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGameSlot(false);
            }
        });
    }

    public void switchToComplexity() {
        Intent tmp = new Intent(this, ComplexityActivity.class);
        tmp.putExtra("GlobalCenter", globalCenter);
        startActivity(tmp);
        finish();
    }

    public void switchTo2048() {
        Intent tmp = new Intent(this, GameActivity.class);
        tmp.putExtra("GlobalCenter", globalCenter);
        startActivity(tmp);
        finish();
    }

    public void switchToGameSlot(boolean saveOrLoad) {
        Intent tmp = new Intent(this, LoadOrSaveGameActivity.class);
        tmp.putExtra("loadGame?", saveOrLoad);
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
