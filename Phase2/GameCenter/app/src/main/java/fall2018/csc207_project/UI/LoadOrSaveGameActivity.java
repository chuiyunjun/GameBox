package fall2018.csc207_project.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import fall2018.csc207_project.GameCenter.Game;
import fall2018.csc207_project.GameCenter.GlobalCenter;
import fall2018.csc207_project.GameCenter.LocalGameCenter;
import fall2018.csc207_project.R;
import fall2018.csc207_project.SlidingTileGame.GameActivity;
import fall2018.csc207_project.SlidingTileGame.SlidingTileGame;
import fall2018.csc207_project.game2048.Game2048;

public class LoadOrSaveGameActivity extends AppCompatActivity {

    private GlobalCenter globalCenter;
    private LocalGameCenter localCenter;
    private boolean state;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_and_save);
        state = getIntent().getBooleanExtra("loadGame?", true);
        globalCenter = (GlobalCenter) (getIntent().getSerializableExtra("GlobalCenter"));
        localCenter = globalCenter.getLocalGameCenter(globalCenter.getCurrentPlayer().getUsername());

        initTextView();
        addSlotButton();
    }

    private void initTextView() {
        if(state) {
            TextView addDeleteBand = findViewById(R.id.save_load_band);
            TextView tapBand = findViewById(R.id.select_band);

            addDeleteBand.setText(R.string.load_band_text);
            tapBand.setText(R.string.tap_load);
        }
    }


    private void addSlotButton() {
        final LinkedList<List<Object>> slots = localCenter.getSavingSlots();
        LinearLayout ll = findViewById(R.id.save_load_slot);
        for(int i=0;i<localCenter.getSAVESLOTNUM();i++) {
            final Button button = new Button(this);
            button.setId(i);
            initButtonLabel(button, slots.get(i));
            ll.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(state) {
                        try {
                            Game game = localCenter.loadGame(localCenter.getCurGameName(), button.getId());
                            switchToGame(game);
                        } catch (IndexOutOfBoundsException e) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "There is no saved game for this block yet!",
                                Toast.LENGTH_SHORT);

                        toast.show();
                    }
                    } else {
                        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                                .format(Calendar.getInstance().getTime());
                        localCenter.saveGame(button.getId(), timeStamp);
                        initButtonLabel(button, slots.get(button.getId()));
                    }
                }
            });
        }
        if(state) {
            addAutoSaveView();
        }

    }

    private void addAutoSaveView() {
        LinearLayout ll = findViewById(R.id.save_load_slot);
        TextView tv = new TextView(this);
        Button button = new Button(this);
        initButtonLabel(button, localCenter.getSavingSlots().get(localCenter.getAUTOSAVEINDEX()));
        tv.setText(R.string.auto_save_slot);
        tv.setTextSize(17);
        tv.setGravity(Gravity.CENTER);
        ll.addView(tv);
        ll.addView(button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Game game = localCenter.loadGame(localCenter.getCurGameName(), localCenter.getAUTOSAVEINDEX());
                switchToGame(game);
            }
        });
    }

    private void switchToGame(Game game) {

        if(game instanceof SlidingTileGame) {
            Intent tmp = new Intent(this, fall2018.csc207_project.SlidingTileGame.GameActivity.class);
            tmp.putExtra("GlobalCenter", globalCenter);
            tmp.putExtra("slidingTileGame", (SlidingTileGame) game);
            localCenter.setCurGame(game);
            startActivity(tmp);
        } else if(game instanceof Game2048) {
            Intent tmp = new Intent(this, fall2018.csc207_project.game2048.GameActivity.class);
            tmp.putExtra("GlobalCenter", globalCenter);
            tmp.putExtra("game2048", (Game2048) game);
            localCenter.setCurGame(game);
            startActivity(tmp);
        }

        finish();
    }

    private void initButtonLabel(Button button, List<Object> slot) {
        if(slot.size() == 0) {
            button.setText("(Blank Slot)");
        } else if(localCenter.getCurGameName().equals("slidingTileGame")){
            int complexity = (Integer) ((LinkedList<Object>) slot).getFirst();;
            String time = (String) ((LinkedList<Object>) slot).getLast();
            button.setText("Complexity: " + complexity + "x" + complexity +"\n" + "Save Time: "+time);
        } else if(localCenter.getCurGameName().equals("game2048")) {
            //TODO:
            int score = (Integer) slot.get(3);
            String time = (String) ((LinkedList<Object>) slot).getLast();
            button.setText("Score: "+score+"\n" + "Save Time: "+time);
        }
    }

    public void onBackPressed() {
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
}
