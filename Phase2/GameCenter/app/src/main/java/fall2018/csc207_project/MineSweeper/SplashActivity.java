package fall2018.csc207_project.MineSweeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import fall2018.csc207_project.R;

/**
 *
 * a welcome page
 *
 * adapted from https://github.com/lany192/Minesweeper.git, includes this activity and XML file
 * activity_splash, logo from github above as well
 *
 */

public class SplashActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Timer timer = new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,
                        GameComplexityActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        };
        timer.schedule(timerTask, 1000*2);
    }
}
