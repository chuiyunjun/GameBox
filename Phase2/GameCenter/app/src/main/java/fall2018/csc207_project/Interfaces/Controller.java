package fall2018.csc207_project.Interfaces;

import android.content.Context;

public interface Controller {

    void endGame(final Context context, final String message);

    void setGame(Game game);


}
