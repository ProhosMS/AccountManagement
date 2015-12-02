package model.Account;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Agent.Agent;
import util.AccountUtil;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class Account implements Comparable<Account> {

    private static final String WITHDRAW_ERROR_MSG =
            "Insufficient funds: amount to withdraw is %s, it is greater than available funds: %s";

    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty ID = new SimpleStringProperty();
    private final DoubleProperty balance = new SimpleDoubleProperty();


    public Account(String name, String id, Double balance) {
        setName(name);
        setID(id);
        setBalance(balance);
    }

    public StringProperty getNameProperty() {
        return this.name;
    }

    public StringProperty getIDProperty() {
        return this.ID;
    }

    public DoubleProperty getBalanceProperty() {
        return this.balance;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getID() {
        return ID.get();
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public Double getBalance() {
        return balance.get();
    }

    public void setBalance(Double balance) {
        this.balance.set(balance);
    }

    @Override
    public String toString() {
        return String.format("%s %s ($%s)", this.ID, this.name, this.balance);
    }

    public void withdraw(double amount) {
        Double balance = getBalance();
        if (balance - amount < 0) {
            throw new IllegalArgumentException(String.format(WITHDRAW_ERROR_MSG, AccountUtil.FORMATTER.format(amount), AccountUtil.FORMATTER
                    .format(balance)));
        } else {
            Double newBalance = balance - amount;
            setBalance(newBalance);
        }
    }

    public void autoWithdraw(double amount, Agent agent) {
        withdraw(amount);
    }

    public void deposit(double amount) {
        Double balance = getBalance();
        Double newBalance = balance + amount;
        setBalance(newBalance);
    }

    @Override
    public int compareTo(Account o) {
        return this.getID().compareTo(o.getID());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Account other = (Account) o;
        return getID().equals(other.getID());
    }

}
