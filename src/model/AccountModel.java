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
        /* TODO Mock it for now */
        accountList.setAll(
                new Account("Person1", "aaa", 200.0),
                new Account("Person2", "caa", 300.0),
                new Account("Person3", "baa", 400.0),
                new Account("Person4", "baa", 400.0)
        );
        accountList.sorted((a, b) -> a.getID().compareTo(b.getID()));
    }

    public void saveToFile(String filepath) {
        /* TODO */
    }
}
