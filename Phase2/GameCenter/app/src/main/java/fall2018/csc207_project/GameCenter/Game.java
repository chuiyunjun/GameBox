package fall2018.csc207_project.GameCenter;

import java.util.List;
import java.util.Observable;

/**
 * the abstract class of Game
 */

public abstract class Game extends Observable {

    /**
     * get the list of setting information
     * @return List which contains setting information
     */
    public abstract List<Object> getSetting();

}
