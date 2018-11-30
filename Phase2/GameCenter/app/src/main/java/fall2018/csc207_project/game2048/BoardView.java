package fall2018.csc207_project.game2048;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;

import fall2018.csc207_project.R;


/**
 * generate view of the board
 */
public class BoardView extends GridLayout {
    /**
     * error range in touch
     */
    public final static int THRESHOLD = 5;

    /**
     * view of the tiles
     */
    private TextView[][] tileLabel;

    /**
     * movement controller
     */
    private MovementController mController;

    /**
     * width of the grid (real width, not number of cells per row)
     */
    private static final int DEFAULT_WIDTH = 245;

    /**
     * width of the grid
     */
    private int complexity = 4;

    /**
     * game data model
     */
    private Game2048 game;


    /**
     * construct the board view by context
     *
     * @param context game activity
     */
    public BoardView(Context context) {
        super(context);
        initBoardView(context);
    }

    /**
     * costruct the board view by context and attribute attrs extended from GridLayout
     *
     * @param context game activity
     * @param attrs   attribute set extended from GridLayout
     */
    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBoardView(context);
    }


    /**
     * costruct the board view by context and attribute attrs and
     * defStyleAttr extended from GridLayout
     *
     * @param context      game activity
     * @param attrs        attribute set extended from GridLayout
     * @param defStyleAttr attribute set extended from GridLayout
     */
    public BoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBoardView(context);
    }

    /**
     * set the view (parametre like size, colour, location on screen) of the textview
     *
     * @param label textview to be set
     */
    private void formatTextView(TextView label) {
        label.setTextSize(35);
        label.setTypeface(null, Typeface.BOLD);
        label.setTextColor(getResources().getColor(R.color.default2048TextColor));
        label.setGravity(Gravity.CENTER);
    }

    /**
     * set the game data model to the view, connect the model to the controller
     *
     * @param game game data model
     */
    public void setGame(Game2048 game) {
        this.game = game;
        mController.setGame(game);
        //initialize the view
        initViewTable();
    }

    /**
     * get the movement controller
     *
     * @return the move controller
     */
    public MovementController getMController() {
        return mController;
    }

    /**
     * set thr text view of the number, if there is no number, set empty view, or set the
     * specific number
     *
     * @param label view of the label
     * @param num   the number
     */
    private void setTextViewLabel(TextView label, int num) {
        String n = num + "";
        if (num <= 0)
            label.setText("");
        else
            label.setText(n);
    }

    /**
     * initialize the view of the grid, put all tiles onto the
     * grid by the given size
     */
    private void initViewTable() {
        Board board = mController.getGame().getBoard();
        //set parametres of the grid layout
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-1, -1);
        params.setMargins(10, 10, 0, 0);

        // put tiles to the board and set the view by default parametres
        for (int y = 0; y < complexity; y++) {
            for (int x = 0; x < complexity; x++) {
                tileLabel[x][y] = new TextView(getContext());
                FrameLayout fl = new FrameLayout(getContext());
                formatTextView(tileLabel[x][y]);
                setTileColor(tileLabel[x][y], board.getTile(x, y).getNum());
                fl.addView(tileLabel[x][y], params);
                setTextViewLabel(tileLabel[x][y], board.getTile(x, y).getNum());
                addView(fl, DEFAULT_WIDTH, DEFAULT_WIDTH);
            }
        }
    }

    /**
     * change the display on the board after each move
     */
    public void updateDisplay() {
        Board board = mController.getGame().getBoard();
        for (int y = 0; y < complexity; y++) {
            for (int x = 0; x < complexity; x++) {
                setTextViewLabel(tileLabel[x][y], board.getTile(x, y).getNum());
                setTileColor(tileLabel[x][y], board.getTile(x, y).getNum());
            }
        }
    }


    /**
     * set color to the view of tiles by numbers on it
     *
     * @param label textview on tiles
     * @param num   number on tiles
     */
    private void setTileColor(TextView label, int num) {
        switch (num) {
            case 0:
                label.setBackgroundColor(getResources().getColor(R.color.tileHolder));
                break;
            case 2:
                label.setBackgroundColor(getResources().getColor(R.color.tile_2));
                break;
            case 4:
                label.setBackgroundColor(getResources().getColor(R.color.tile_4));
                break;
            case 8:
                label.setBackgroundColor(getResources().getColor(R.color.tile_8));
                break;
            case 16:
                label.setBackgroundColor(getResources().getColor(R.color.tile_16));
                break;
            case 32:
                label.setBackgroundColor(getResources().getColor(R.color.tile_32));
                break;
            case 64:
                label.setBackgroundColor(getResources().getColor(R.color.tile_64));
                break;
            case 128:
                label.setBackgroundColor(getResources().getColor(R.color.tile_128));
                break;
            case 256:
                label.setBackgroundColor(getResources().getColor(R.color.tile_256));
                break;
            case 512:
                label.setBackgroundColor(getResources().getColor(R.color.tile_512));
                break;
            case 1024:
                label.setBackgroundColor(getResources().getColor(R.color.tile_1024));
                break;
            case 2048:
                label.setBackgroundColor(getResources().getColor(R.color.tile_2048));
                break;
        }
        if (num > 4)
            label.setTextColor(getResources().getColor(R.color.white));
        else
            label.setTextColor(getResources().getColor(R.color.default2048TextColor));
    }

    /**
     * initialize the view of the board, by the touch requirement of the user
     *
     * @param context game activity
     */
    private void initBoardView(final Context context) {

        //set the column length (on screen, not number of tiles)
        setColumnCount(complexity);
        tileLabel = new TextView[complexity][complexity];
        mController = new MovementController(game);

        // supervise the touch requirement of the user
        setOnTouchListener(new View.OnTouchListener() {
            private float startX, startY, offsetX, offsetY;
            private boolean hasMoved;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // process touch input of the user by coordinates because the movement
                //in this game is a vector, not a single click
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;
                        hasMoved = mController.processMovement(getContext(),
                                getTouchDirection(offsetX, offsetY));
                        if (hasMoved)
                            updateDisplay();
                        break;
                }
                return true;
            }
        });
    }

    /**
     * calculate the move direction of the user, judge the move direction by the
     * permitted error threshold above
     *
     * @param offsetX x part of the movement vector (horizontal)
     * @param offsetY y part of the movement vector (vertical)
     * @return the direction of the movement
     */
    private int getTouchDirection(float offsetX, float offsetY) {
        int direction = 0;
        if (Math.abs(offsetX) > Math.abs(offsetY)) {
            if (offsetX < -THRESHOLD) {
                direction = 3;
            } else if (offsetX > THRESHOLD) {
                direction = 4;
            }
        } else {
            if (offsetY < -THRESHOLD) {
                direction = 1;
            } else if (offsetY > THRESHOLD) {
                direction = 2;
            }
        }
        return direction;
    }


}

