package fall2018.csc207_project.SlidingTileGame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.LinkedList;

import fall2018.csc207_project.GameCenter.GlobalCenter;
import fall2018.csc207_project.GameCenter.LocalGameCenter;
import fall2018.csc207_project.R;

/**
 * The setting for SlidingTileGame activity.
 */
public class ComplexityActivity extends AppCompatActivity {

    private GlobalCenter globalCenter;
    private LocalGameCenter localCenter;
    int undoStep = 3;

    protected void onCreate(Bundle savedInstanceState) {
        globalCenter = (GlobalCenter) (getIntent().getSerializableExtra("GlobalCenter"));
        localCenter = globalCenter.getLocalGameCenter(globalCenter.getCurrentPlayer().getUsername());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complexity);
        add3x3ButtonListener();
        add4x4ButtonListener();
        add5x5ButtonListener();
        addSetUndoButtonListener();
    }

    public void onBackPressed() {
        Intent tmp = new Intent(this, StartingActivity.class);
        tmp.putExtra("GlobalCenter", globalCenter);
        startActivity(tmp);
        finish();
    }

    private void add3x3ButtonListener(){
        Button l3Button = findViewById(R.id.button3x3);
        l3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame(3);
                finish();
            }
        });
    }

    private void add4x4ButtonListener(){
        Button l3Button = findViewById(R.id.button4x4);
        l3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame(4);
                finish();
            }
        });
    }

    private void add5x5ButtonListener(){
        Button l3Button = findViewById(R.id.button5x5);
        l3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame(5);
                finish();
            }
        });
    }

    private void addSetUndoButtonListener() {

        Button button = findViewById(R.id.accept_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText undoStepsText = findViewById(R.id.undo_step_input);
                String steps = undoStepsText.getText().toString();
                if(steps.equals("")) {
                    Toast.makeText(getApplicationContext(), "Field can't be empty!",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    undoStep = Integer.parseInt(steps);
                    Toast.makeText(getApplicationContext(), "Setting complete!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void switchToGame(int complexity) {
        LinkedList<Object> settings = new LinkedList<>();
        settings.add(complexity);
        SlidingTileGame game = (SlidingTileGame) localCenter.newGame("slidingTileGame", settings);
        game.setUndoStep(undoStep);
        Intent tmp = new Intent(this, GameActivity.class);
        tmp.putExtra("GlobalCenter", globalCenter);
        tmp.putExtra("slidingTileGame",game);
        localCenter.setCurGame(game);
        startActivity(tmp);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        globalCenter.saveAll(getApplicationContext());
    }
}
