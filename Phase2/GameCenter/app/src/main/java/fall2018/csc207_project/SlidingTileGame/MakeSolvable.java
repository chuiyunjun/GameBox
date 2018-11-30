package fall2018.csc207_project.SlidingTileGame;

import java.util.ArrayList;

public class MakeSolvable {
    private int blankTileRow;
    private int blankTileCol;
    private int complexity;
    private int inversion = 0;
    private Board board;
    private boolean solved;
    private ArrayList<Integer> tilesId = new ArrayList<>();

    private void recordData(){
        for(int row = 0; row != complexity;row++){
            for(int col = 0; col != complexity; col++){
                if(board.getTile(row, col).getId() == complexity*complexity){
                    blankTileCol = col;
                    blankTileRow = row;
                }else{
                    tilesId.add(board.getTile(row, col).getId());
                }
            }
        }
    }

    private void findInversion(){

        for(int i=0; i < tilesId.size();i++){
            for(int j=i+1;j<tilesId.size();j++){
                if(tilesId.get(i)>tilesId.get(j)){inversion+=1;}
            }
        }
    }

    private void judgeSolved(){

        if(complexity%2==1){
            if(inversion%2==0){solved = true;}else{solved = false;}
        }else {
            solved = ((complexity-blankTileRow) % 2 == 1)==(inversion%2==0);
        }
    }

    private void MakeBoardSolvable(){
        if(!solved){
            if(blankTileRow == complexity-1){
                if(blankTileCol == complexity-1){
                    board.swapTiles(complexity-1,complexity-2,complexity-1,complexity-3);
                } else if (blankTileCol == board.getNumCols() - 2) {
                    board.swapTiles(complexity-1,complexity-1,complexity-1,complexity-3);
                }
            }else{
                board.swapTiles(complexity-1,complexity-1,complexity-1,complexity-2);
            }

        }
    }

    void takeIn(Board board){
        this.complexity = board.getNumCols();
        this.board = board;
        recordData();
        findInversion();
        judgeSolved();
    }

    Board outputSolvableBoard() {
        MakeBoardSolvable();
        return board;
    }
}
