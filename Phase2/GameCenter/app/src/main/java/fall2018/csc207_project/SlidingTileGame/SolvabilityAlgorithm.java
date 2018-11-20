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
     * construct the algorithm
     * @param board the board to play with
     */
    SolvabilityAlgorithm(Board board) {

        this.width = board.getNumCols();
        for (Tile tile : board) {
            tileID.add(tile.getId());
            // if the blank tile is found, assign its bottom row order to bottomOrder
            if (tile.getId() == width * width) {
                bottomOrder = width - ((tileID.size() - 1) / width);
            }
        }
    }

    /**
     * count the total number of inversions in the board
     * @return the number of inversions
     */
    private int countInversion(){
        int inversion = 0;
        for (int start = 0; start < tileID.size(); start++) {
            for (int follow = start + 1; follow < tileID.size(); follow++) {
                if (tileID.get(start) > tileID.get(follow)) {
                    inversion++;
                }
            }
        }
        return inversion;
    }

    /**
     * return whether the game is solvable
     * @return whether the game is solvable
     */
    public boolean solvable() {
        boolean result = false;
        int numOfInversion = countInversion();
        if ((width % 2 != 0) && (numOfInversion % 2 == 0)) {
            result = true;
        } else if (width % 2 == 0) {
            if ((bottomOrder % 2 == 0) && (numOfInversion % 2 != 0)) {
                result = true;
            } else if ((bottomOrder % 2 != 0) && (numOfInversion % 2 == 0)) {
                result = true;
            }
        }
        return result;
    }
}
