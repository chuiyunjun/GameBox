package fall2018.csc207_project;

import org.junit.Assert;
import org.junit.Test;

import fall2018.csc207_project.GameCenter.Account;

public class AccountTest {
    private Account account = new Account("username", 1234);

    @Test
    private void testSignIn(){
        account = new Account("username", 1234);
        Assert.assertEquals(true, account.signIn(1234));
    }
}
