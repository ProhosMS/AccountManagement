package model.Account;

import javafx.application.Platform;
import model.Agent.Agent;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class ConcurrentAccount extends Account {

    public ConcurrentAccount(String name, String id, Double balance) {
        super(name, id, balance);
    }

    @Override
    synchronized public void deposit(double amount) {
        super.deposit(amount);
        notifyAll();
    }

    @Override
    synchronized public void withdraw(double amount) {
        super.withdraw(amount);
    }

    @Override
    synchronized public void autoWithdraw(double amount, Agent agent) {
        try {
            while (getBalance() - amount < 0) {
                Platform.runLater(() -> agent.setStatus(Agent.Status.Blocked));
                wait();
            }

            Platform.runLater(() -> withdraw(amount));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
