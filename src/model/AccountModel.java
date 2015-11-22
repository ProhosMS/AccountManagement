package model;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    public void loadFromFile(String filepath) {
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
        /* TODO Mock it for now */
        accountList.setAll(
                new Account("Sang Mercado", "1000", 200.0),
                new Account("Gloria Jauregui", "2000", 300.0),
                new Account("Jared Pruett", "3000", 400.0),
                new Account("Yinebeb Zenaw", "4000", 400.0)
        );
        accountList.sorted((a, b) -> a.getID().compareTo(b.getID()));
    }

    public void saveToFile(String filepath) {
        /* TODO */
    }
}
