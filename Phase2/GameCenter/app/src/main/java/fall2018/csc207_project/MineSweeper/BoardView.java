package fall2018.csc207_project.MineSweeper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;

import fall2018.csc207_project.R;

public class BoardView extends GridLayout {
    private MineSweeperGame game;
    private MovementController movementController;
    private Button[][] tileTable;
    private int DEFAULT_WIDTH = 108;


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

    public void initBoardView(Context context){
        int boardSize = 10;
        setColumnCount(boardSize);
        tileTable = new Button[boardSize][boardSize];
        movementController = new MovementController();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-1,-1);
        params.setMargins(10,10,10,10);

        for(int x = 0;x < boardSize; x++){
            for(int y = 0; y < boardSize; y++){
                Button button = new Button(getContext());
                button.setLongClickable(true);
                final int row = x;
                final int col = y;
                button.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        movementController.flip(getContext(),row, col);

                    }
                });
                button.setOnLongClickListener(new View.OnLongClickListener(){
                    @Override
                    public boolean onLongClick(View v){
                        movementController.changeLabelState(row, col);
                        return  true;
                    }
                });

                tileTable[x][y] = button;
                FrameLayout fl = new FrameLayout(getContext());
                tileTable[x][y].setBackgroundResource(R.drawable.button);
                fl.addView(tileTable[x][y], params);
                addView(fl, DEFAULT_WIDTH, DEFAULT_WIDTH);
            }
        }

    }

    public void initViewTable(){
        Board board = movementController.getGame().getBoard();
        int boardSize = board.getBoardSize();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-1,-1);
        params.setMargins(10,10,0,0);

        for(int y = 0;y < boardSize; y++){
            for(int x = 0; x < boardSize; x++){
                tileTable[x][y] = new Button(getContext());
                FrameLayout fl = new FrameLayout(getContext());
                tileTable[x][y].setBackgroundResource(R.drawable.button);
                fl.addView(tileTable[x][y], params);
                addView(fl, DEFAULT_WIDTH, DEFAULT_WIDTH);
            }
        }
    }

    public void setTableImage(){
        Tile tile;
        Board board = movementController.getGame().getBoard();
        Tile[][] boardTable = board.getTileTable();
        int boardSize = board.getBoardSize();
        for(int x = 0;x < boardSize; x++){
            for(int y = 0; y < boardSize; y++){
                tile = boardTable[x][y];
                int resourceID = tile.getTileImage();
                tileTable[x][y].setBackgroundResource(resourceID);
            }
        }
    }

    public void setTileImage(int index) {
        Board board = movementController.getGame().getBoard();
        Tile[][] boardTable = board.getTileTable();
        int boardSize = board.getBoardSize();
        int row = index / boardSize;
        int col = index%boardSize;
        Tile tile = boardTable[row][col];
        tileTable[row][col].setBackgroundResource(tile.getTileImage());
    }




    public void setGame(MineSweeperGame game) {
        this.game = game;
        movementController.setGame(game);

    }
}

