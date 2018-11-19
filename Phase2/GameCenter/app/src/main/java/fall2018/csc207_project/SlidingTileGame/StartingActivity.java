package fall2018.csc207_project.SlidingTileGame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fall2018.csc207_project.GameCenter.Game;
import fall2018.csc207_project.GameCenter.GlobalCenter;
import fall2018.csc207_project.GameCenter.LocalGameCenter;
import fall2018.csc207_project.UI.LoadOrSaveGameActivity;
import fall2018.csc207_project.UI.LocalCenterActivity;

public class StartingActivity extends AppCompatActivity {

    private GlobalCenter globalCenter;
    private LocalGameCenter localCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globalCenter = (GlobalCenter) (getIntent().getSerializableExtra("GlobalCenter"));
        localCenter = globalCenter.getLocalGameCenter(globalCenter.getCurrentPlayer().getUsername());

        setContentView(fall2018.csc207_project.R.layout.game_page);
        addNewGameButtonListener();
        addLoadGameButtonListener();
        addSaveGameButtonListener();

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
                switchToComplexity();
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
