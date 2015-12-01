package model.Account;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.AccountUtil;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class AccountModel {

    private ObservableList<Account> accountList = FXCollections
            .observableArrayList(account -> new Observable[] {
                    account.getNameProperty(), account.getBalanceProperty(), account.getIDProperty()
            });
    private ObjectProperty<Account> currentAccount = new SimpleObjectProperty<Account>();

    public ObservableList<Account> getAccountList() {
        return accountList;
    }

    public ObjectProperty<Account> getCurrentAccountProperty() {
        return currentAccount;
    }

    public Account getCurrentAccount() {
        return currentAccount.get();
    }

    public void setCurrentAccount(Account account) {
        currentAccount.set(account);
    }

    public void loadFromFile(File file) throws IOException {
        /**
         * The system should have other exceptions as needed (e.g. in response to corrupted or inconsistent content of
         * the file with accounts;
         * e.g. inadmissible characters in the fields of an account entry; broken format of the file).
         * Exceptions should result in useful messages via dismissable pop-up windows.
         * In case of file inconsistencies messages should mention name of the file,
         * line number where inconsistency occurred,
         * nature of inconsistency,
         * suggestion to fix (e.g. “Name must have only letters” or “Amount must not be negative”).
         * If an error is unrecoverable the system should exit on dismissing the pop up window.
         */

        accountList.clear();

        BufferedReader reader = new BufferedReader(new FileReader(file));
        int lineNumber = 0;
        String lineText;

        while ((lineText = reader.readLine()) != null) {
            lineNumber++;
            try {
                Account account = AccountUtil.readAccount(lineText);
                if (accountList.contains(account)) {
                    throw new IllegalArgumentException("File has duplicated IDs");
                }
                accountList.add(account);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(String.format("Line %s: %s ", lineNumber, e.getMessage()));
            }
        }

        accountList.sorted((a, b) -> a.getID().compareTo(b.getID()));
    }

    public void saveToFile(File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        accountList
                .stream()
                .map(AccountUtil::writeAccount)
                .forEach(accountText -> {
                    try {
                        writer.write(accountText);
                        writer.newLine();
                    } catch (IOException e) {
                        throw new IllegalArgumentException(String.format("Error writing Account %s", accountText));
                    }
                });
        writer.close();
    }
}
