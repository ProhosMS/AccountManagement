package model;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;


/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class AccountTest {

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Cannot withdraw. Balance would be less than zero")
    public void testAccount_withdrawThrowsAnExceptionIfBalanceIsLessThanZero() {
        Account account = new Account("Sang", "10000", 1.0);
        account.withdraw(1.1);
    }

    @Test
    public void testBalance_toString() {
        Account account = new Account("Sang", "10000", 100.004);
        String balance = account.getStringBalance();

        System.out.println(balance);
        assertEquals(balance, "$100.00");
    }
}
