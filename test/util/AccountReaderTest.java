package util;

import model.Account;
import org.testng.annotations.Test;
import util.AccountReader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.*;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class AccountReaderTest {

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Account String is invalid. Format should be name id balance")
    public void testReadAccount_handlesEmptyString() {
        AccountReader.readAccount("");
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Account String is invalid. Format should be name id balance")
    public void testReadAccount_handlesInvalidString() {
        AccountReader.readAccount("invalid string sefesfj sfesef");
    }

    @Test
    public void testReadAccount_singleAccount() {
        /* String to read an account is of the format name id money
         * example of it is Sang 123123123 $100
         */

        String exampleAccount = "Sang 123123123 $100";
        Account account = AccountReader.readAccount(exampleAccount);
        assertEquals(account.getName(), "Sang");
        assertEquals(account.getID(), "123123123");
        assertEquals(account.getBalance(), 100.0);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void testReadAccount_negativeBalanceIsNotAllowed() {
        String exampleAccount = "Sang 123123123 $-100";
        Account account = AccountReader.readAccount(exampleAccount);
    }

    @Test
    public void testRegexPattern_balance() {
        Pattern pattern = Pattern.compile("\\$(\\d+)");
        Matcher matcher = pattern.matcher("$100");
        assertTrue(matcher.find());
        assertEquals(matcher.group(1), "100");
    }
}