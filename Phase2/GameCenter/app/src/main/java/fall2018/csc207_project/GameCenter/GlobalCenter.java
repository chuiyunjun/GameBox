package fall2018.csc207_project.GameCenter;

import android.app.Activity;
import android.content.Context;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class GlobalCenter implements Serializable, Observer{

    private static final long serialVersionUID = 2L;

    private Map<String, Account> accounts = new HashMap<>();
    private Map<String, LocalGameCenter> localCenters = new HashMap<>();
    private Map<String, ScoreBoard> scoreboards = new HashMap<>(); // gameName -> ScoreBoard
    private Account currentPlayer;

    public Map getAccounts() {
        return accounts;
    }

    public Map getScoreBoards(){
        return scoreboards;
    }

    public Boolean signIn(String username, String password){

        if (accounts.containsKey(username)){
            Account user = accounts.get(username);
            if (user.signIn(password)){
                currentPlayer = user;
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean signUp(String username, String password1, String password2) throws Exception{
        if (username.equals("") || password1.equals("") || password2.equals(""))
            throw new EmptyFieldException();
        else if (accounts.containsKey(username)){
            throw new UsernameExistException();
        }
        else if (!password1.equals(password2)){
            throw new PasswordNotMatchingException();
        }
        else {
            Account newAccount = new Account(username, password1);
            accounts.put(username, newAccount);
            addLocalGameCenter(username);
            return signIn(username, password1);
        }
    }

    private void addLocalGameCenter(String username) {
        LocalGameCenter newLocalCenter = new LocalGameCenter();
        localCenters.put(username, newLocalCenter);
    }

    public Account getCurrentPlayer(){
        return currentPlayer;
    }

    public void signOut(){
        currentPlayer = null;
    }

    public LocalGameCenter getLocalGameCenter(String username) {
        return localCenters.get(username);
    }

    public void addScoreBoard(String gameName, ScoreBoard scoreboard){
        if (!scoreboards.containsKey(gameName)){
            scoreboards.put(gameName, scoreboard);
        }
    }

    public void saveAll(Context context){
        ObjectOutputStream out = null;
        try {
            FileOutputStream fileOut = context.openFileOutput("dataBase2.ser", Activity.MODE_PRIVATE);
            out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            fileOut.getFD().sync();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
