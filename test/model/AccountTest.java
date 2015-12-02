package model;

import model.Account.Account;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;


/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class AccountTest {

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Insufficient funds: amount to withdraw is 1.10, it is greater than " +
                    "available funds: 1.00")
    public void testAccount_withdrawThrowsAnExceptionIfBalanceIsLessThanZero() {
        Account account = new Account("Sang", "10000", 1.0);
        account.withdraw(1.1);
    }
}
