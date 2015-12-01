package util;

import model.Account.Account;
import model.Account.SafeAccount;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class AccountUtil {
    private static final int ACCOUNT_STRING_FIELDS_LENGTH = 3;
    private static final Pattern accountPattern = Pattern.compile("(\\w+\\s*\\w+)\\s+(\\d+)\\s+\\$(\\d+)");
    public static final NumberFormat FORMATTER = new DecimalFormat("#0.00");

    public static Account readAccount(String accountString) throws IllegalArgumentException {
        Matcher matcher = accountPattern.matcher(accountString);

        if (accountString.length() == 0 || !matcher.find()) {
            throw new IllegalArgumentException("Account String is invalid. Format should be name id balance");
        }

        return new SafeAccount(matcher.group(1), matcher.group(2), Double.parseDouble(matcher.group(3)));
    }

    public static String getStringBalance(Double balance) {
        String balanceString = FORMATTER.format(balance);
        return String.format("$%s", balanceString);
    }

    public static String writeAccount(Account account) {
        return String.format("%s %s %s",
                             account.getName(),
                             account.getID(),
                             AccountUtil.getStringBalance(account.getBalance()));
    }
}
