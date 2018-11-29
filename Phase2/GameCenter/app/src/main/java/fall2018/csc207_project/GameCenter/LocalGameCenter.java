package fall2018.csc207_project.GameCenter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LocalGameCenter implements Serializable {
    private static final long serialVersionUID = 3L;

    private final int SAVESLOTNUM = 3;

    public final int AUTOSAVEINDEX = 3;

    private Map<String, LinkedList<List<Object>>> localGames;

    private Game curGame;

    private String curGameName;

    public Map getLocalGames(){
        return localGames;
    }

    public LocalGameCenter() {
        curGame = null;
        curGameName = null;
        localGames = new HashMap<>();
    }

    public Game getCurGame() {
        return curGame;
    }

    public void setCurGame(Game game) {
        curGame = game;
    }

    public boolean addGame(String gameName) {
        if(localGames.containsKey(gameName))
            return false;
        else {
            LinkedList<List<Object>> slots = new LinkedList<>();
            for(int i=0;i<=SAVESLOTNUM;i++) {
                slots.add(new LinkedList<>());
            }
            localGames.put(gameName, slots);
        }
        return true;
    }

    public void setCurGameName(String gameName) {
        this.curGameName = gameName;
    }

    public int getSAVESLOTNUM() {
        return SAVESLOTNUM;
    }

    public int getAUTOSAVEINDEX() {
        return AUTOSAVEINDEX;
    }

    public String getCurGameName() {
        return this.curGameName;
    }

    public Set<String> getGames() {
        return localGames.keySet();
    }

    public LinkedList<List<Object>> getSavingSlots() {
        return localGames.get(curGameName);
    }

    public boolean hasGame(String gameName) {
        return localGames.get(gameName) != null;
    }

    public boolean removeGame(String gameName) {
        return localGames.remove(gameName) != null;
    }

    public boolean saveGame(int gameSlot, String timeStamp) {

        if(gameSlot > SAVESLOTNUM || gameSlot < 0 || curGame == null || curGameName == null) {
            return false;
        }
        List<Object> temp = localGames.get(curGameName).get(gameSlot);
        temp.clear();
        temp.addAll(curGame.getSetting());
        temp.add(timeStamp);

        return true;
    }

    public Game loadGame(String gameName, int gameSlot) {
        List<Object> settings= localGames.get(gameName).get(gameSlot);
        return new GameFactory().createGame(gameName, settings);
    }

    public Game newGame(String gameName, List<Object> settings) {
        return new GameFactory().createGame(gameName, settings);
    }


    public void autoSave(String timestamp) {
        saveGame(AUTOSAVEINDEX, timestamp);
    }

}
