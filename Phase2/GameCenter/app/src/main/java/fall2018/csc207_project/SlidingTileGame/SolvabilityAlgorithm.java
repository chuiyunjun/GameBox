package fall2018.csc207_project.SlidingTileGame;

import java.util.ArrayList;

/**
 * a check service for whether a slidingtile game is solvable
 *
 * cite algorithm from
 * https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
 */

class SolvabilityAlgorithm {

    /**
     * ID of tiles in the board
     */
    private ArrayList<Integer> tileID = new ArrayList<>();

    /**
     * the width of the board
     */
    private int width;

    /**
     * the row order of the blank tile, count from bottom
     */
    private int bottomOrder;
    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles;

    private Board board;

    /**
     * construct the algorithm
     * @param board the board to play with
     */
    SolvabilityAlgorithm(Board board) {

        this.board = board;
        this.width = board.getNumCols();
        for (Tile tile : board) {
            tileID.add(tile.getId());
            // if the blank tile is found, assign its bottom row order to bottomOrder
            if (tile.getId() == 0) {
            }
        }
    }

    /**
     * count the total number of inversions in the board
     * @return the number of inversions
     */
    private int countInversion(){
        int inversion = 0;
        for (int start = 0; start < tileID.size() - 1; start++) {
            for (int follow = start + 1; follow < tileID.size(); follow++) {
                if (tileID.get(start) != 0 && tileID.get(follow) != 0 && (tileID.get(start) > tileID.get(follow))) {
                    inversion++;
                }
            }
        }
        return inversion;
    }

    /**
     *  find the position of blank tile
     * @return the position of blank tile
     */
    private int findPosition(){
        int position = 0;
        this.width = board.getNumCols();
        this.tiles = board.getTiles();
        for(int i = this.width - 1; i >= 0; i--){
            for(int j = this.width - 1; j >=0; j--){
                if(tiles[i][j].getId() == 0){
                    position = this.width-i;
                }
            }
        }
        return position;
    }

    /**
     * return whether the game is solvable
     * @return whether the game is solvable
     */
    boolean solvable() {
        boolean result;
        int numOfInversion = countInversion();
        int pos = findPosition();
        if (width % 2 == 1) {
            return numOfInversion % 2 == 0;
        } else {
            if (pos % 2 ==1) {
                result = numOfInversion % 2 == 1;
            } else {
                result = numOfInversion % 2 == 0;
            }
        }
        return result;
    }
}
