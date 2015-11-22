package model;

import java.math.BigDecimal;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class Account implements Comparable<Account> {
    private String name;
    private String ID;
    private Double balance;

    private static final String WItHDRAW_ERROR_MSG = "Cannot withdraw. Balance would be less than zero";

    public Account(String name, String id, Double balance) {
        this.name = name;
        this.ID = id;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Double getBalance() {
        return new BigDecimal(balance).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return String.format("%s %s ($%s)", this.ID, this.name, this.balance);
    }

    public void withdraw(double amount) {
        if (this.balance - amount < 0) {
            throw new IllegalArgumentException(WItHDRAW_ERROR_MSG);
        } else {
            this.balance -= amount;
        }
    }

    public void deposit(double amount) {
        this.balance += amount;
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
