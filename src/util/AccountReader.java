package util;

import model.Account;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class AccountReader {
    private final static int ACCOUNT_STRING_FIELDS_LENGTH = 3;
    private final static Pattern accountPattern = Pattern.compile("(\\w+)\\s+(\\d+)\\s+\\$(\\d+)");

    public static Account readAccount(String accountString) {
        Matcher matcher = accountPattern.matcher(accountString);

        if (accountString.length() == 0 || !matcher.find()) {
            throw new IllegalArgumentException("Account String is invalid. Format should be name id balance");
        }

        return new Account(matcher.group(1), matcher.group(2), Double.parseDouble(matcher.group(3)));
    }
}
