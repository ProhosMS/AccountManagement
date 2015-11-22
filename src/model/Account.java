package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class Account implements Comparable<Account> {

    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty ID = new SimpleStringProperty();
    private final DoubleProperty balance = new SimpleDoubleProperty();

//    private String name;
//    private String ID;
//    private Double balance;

    private static final String WItHDRAW_ERROR_MSG = "Cannot withdraw. Balance would be less than zero";
    private static final NumberFormat FORMATTER = new DecimalFormat("#0.00");

    public Account(String name, String id, Double balance) {
        setName(name);
        setID(id);
        setBalance(balance);
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

    public String getStringBalance() {
        String balanceString = FORMATTER.format(getBalance());
        return String.format("$%s", balanceString);
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
            throw new IllegalArgumentException(WItHDRAW_ERROR_MSG);
        } else {
            balance -= amount;
            setBalance(balance);
        }
    }

    public void deposit(double amount) {
        Double balance = getBalance();
        balance += amount;
        setBalance(balance);
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
        final Account other = (Account)o;
        return getID().equals(other.getID());
    }

}
