package model.Account;

import model.Account.Account;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class SafeAccount extends Account {

    public SafeAccount(String name, String id, Double balance) {
        super(name, id, balance);
    }

    @Override
    synchronized public void deposit(double amount) {
        super.deposit(amount);
    }

    @Override
    synchronized public void withdraw(double amount) {
        super.withdraw(amount);
    }
}
