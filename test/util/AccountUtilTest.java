package util;

import model.Account.Account;
import org.testng.annotations.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.*;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class AccountUtilTest {

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Account String is invalid. Format should be name id balance")
    public void testReadAccount_handlesEmptyString() {
        AccountUtil.readAccount("");
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Account String is invalid. Format should be name id balance")
    public void testReadAccount_handlesInvalidString() {
        AccountUtil.readAccount("invalid string sefesfj sfesef");
    }

    @Test
    public void testReadAccount_singleAccount() {
        /* String to read an account is of the format name id money
         * example of it is Sang 123123123 $100
         */

        String exampleAccount = "Sang 123123123 $100";
        Account account = AccountUtil.readAccount(exampleAccount);
        assertEquals(account.getName(), "Sang");
        assertEquals(account.getID(), "123123123");
        assertEquals(account.getBalance(), 100.0);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void testReadAccount_negativeBalanceIsNotAllowed() {
        String exampleAccount = "Sang 123123123 $-100";
        Account account = AccountUtil.readAccount(exampleAccount);
    }

    @Test
    public void testRegexPattern_balance() {
        Pattern pattern = Pattern.compile("\\$(\\d+)");
        Matcher matcher = pattern.matcher("$100");
        assertTrue(matcher.find());
        assertEquals(matcher.group(1), "100");
    }

    @Test
    public void testRegexPattern_allowOnlyDigits() {
        Pattern pattern = Pattern.compile("^(?:0|[1-9\\.]\\d{0,2}(?:,?\\d{3})*)(?:\\.\\d+)?$");
        Matcher matcher1 = pattern.matcher("200.0");
        Matcher matcher2 = pattern.matcher("2,000.5");
        Matcher matcher3 = pattern.matcher(".200");
        Matcher matcher4 = pattern.matcher("1");

        assertTrue(matcher1.find());
        assertTrue(matcher2.find());
        assertTrue(matcher3.find());
        assertTrue(matcher4.find());
    }

    @Test
    public void testRegexPattern_dropCommas() {
        Pattern pattern = Pattern.compile("[^\\d+\\.]+");

        assertEquals(pattern.matcher("2,000").replaceAll(""), "2000");
        assertEquals(pattern.matcher("2,000.000").replaceAll(""), "2000.000");
        assertEquals(pattern.matcher("200.0").replaceAll(""), "200.0");
        assertEquals(pattern.matcher(".00").replaceAll(""), ".00");
    }

    @Test
    public void testBalance_toString() {
        Account account = new Account("Sang", "10000", 100.004);
        String balance = AccountUtil.getStringBalance(account.getBalance());

        assertEquals(balance, "$100.00");
    }

    @Test
    public void testReadAccount_canContainSpaceInName() {
        String accountText = "Sang Mercado 10000 $100.0";
        Account account = AccountUtil.readAccount(accountText);
        Account expected = new Account("Sang Mercado", "10000", 100.0);
        assertEquals(account.getName(), expected.getName());
        assertEquals(account.getID(), expected.getID());
        assertEquals(account.getBalance(), expected.getBalance());
    }

    @Test
    public void testWrite_accountToString() {
        Account account = new Account("Sang Mercado", "10000", 100.0);
        /* Should write Sang Mercado */
        String accountText = AccountUtil.writeAccount(account);
        assertEquals(accountText, "Sang Mercado 10000 $100.00");
    }
}