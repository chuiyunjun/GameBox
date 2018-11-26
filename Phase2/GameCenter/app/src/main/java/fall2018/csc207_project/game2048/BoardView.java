package fall2018.csc207_project.game2048;


import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;

import fall2018.csc207_project.GameCenter.Game;
import fall2018.csc207_project.R;


public class BoardView extends GridLayout {
    public final static int THRESHOLD = 5 ;
    private TextView[][] tileLabel;
    private MovementController mController;
    private static final int DEFAULT_WIDTH = 245;
    private int complexity = 4;
    private Game2048 game;


    public BoardView(Context context) {
        super(context);
        initBoardView(context);
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBoardView(context);
    }

    public BoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBoardView(context);
    }

    private void formatTextView(TextView label) {
        label.setTextSize(32);
        label.setBackgroundColor(getResources().getColor(R.color.tileHolder));
        label.setGravity(Gravity.CENTER);
    }

    public void setGame(Game2048 game) {
        this.game = game;
        mController.setGame(game);
        initViewTable();
    }


    private void setTextViewLabel(TextView label,int num) {
        if(num <= 0)
            label.setText("");
        else
            label.setText(num+"");
    }

    private void initViewTable() {
        Board board = mController.getGame().getBoard();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-1,-1);
        params.setMargins(10,10,0,0);

        for(int y = 0;y < complexity; y++){
            for(int x = 0; x < complexity; x++){
                tileLabel[x][y] = new TextView(getContext());
                FrameLayout fl = new FrameLayout(getContext());
                formatTextView(tileLabel[x][y]);
                fl.addView(tileLabel[x][y], params);
                setTextViewLabel(tileLabel[x][y],board.getTile(x,y).getNum());
                addView(fl, DEFAULT_WIDTH, DEFAULT_WIDTH);
            }
        }
    }

    private void updateDisplay() {
        Board board = mController.getGame().getBoard();
        for(int y = 0; y < complexity; y++) {
            for(int x = 0; x < complexity; x++) {
                setTextViewLabel(tileLabel[x][y],board.getTile(x,y).getNum());
            }
        }
    }

    private void initBoardView(final Context context) {

        setColumnCount(complexity);
        tileLabel = new TextView[complexity][complexity];
        mController = new MovementController(game);

        setOnTouchListener(new View.OnTouchListener(){
            private float startX,startY,offsetX,offsetY;
            private boolean hasMoved;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;
                        hasMoved = mController.processMovement(getContext(), getTouchDirection(offsetX,offsetY));
                        if (hasMoved)
                            updateDisplay();
                        break;
                }
                return true;
            }
        });
    }

    private int getTouchDirection(float offsetX, float offsetY) {
        int direction = 0;
        if(Math.abs(offsetX)>Math.abs(offsetY)) {
            if(offsetX<-THRESHOLD) {
                direction = 3;
            }
            else if(offsetX> THRESHOLD) {
                direction = 4;
            }
        }
        else {
            if (offsetY<-THRESHOLD) {
                direction = 1;
            }
            else if (offsetY> THRESHOLD) {
                direction = 2;
            }
        }
        return direction;
    }


}

