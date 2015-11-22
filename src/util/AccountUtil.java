package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Account;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class AccountUtil {
    private static final int ACCOUNT_STRING_FIELDS_LENGTH = 3;
    private static final Pattern accountPattern = Pattern.compile("(\\w+)\\s+(\\d+)\\s+\\$(\\d+)");

    public static Account readAccount(String accountString) {
        Matcher matcher = accountPattern.matcher(accountString);

        if (accountString.length() == 0 || !matcher.find()) {
            throw new IllegalArgumentException("Account String is invalid. Format should be name id balance");
        }

        return new model.Account(matcher.group(1), matcher.group(2), Double.parseDouble(matcher.group(3)));
    }
}
