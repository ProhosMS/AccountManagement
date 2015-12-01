package model.Agent;

import javafx.application.Platform;
import model.Account.Account;
import model.Agent.Agent;

import java.util.concurrent.TimeUnit;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class WithdrawAgent extends Agent {
    public WithdrawAgent(Account account, Type type, long timeInterval, double transferAmount) {
        super(account, type, timeInterval, transferAmount);
    }

    @Override
    public void run() {
        while (isActive) {
            synchronized (lock) {
                while (paused && !isActive) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    /* Withdraw, Keep track of operations, Keep runningAmount */
                    /* while balance < 0, block the thread */
                    Double transfer = transferAmount.get();

                    while (account.getBalance() - transfer < 0) {
                        Platform.runLater(() -> setStatus(Status.Blocked));
                        lock.wait();
                    }

                    Platform.runLater(() -> {
                        setStatus(Status.Running);
                        account.withdraw(transfer);
                        counter.set(atomicCounter.getAndIncrement());
                        runningAmount.set(runningAmount.get() + transfer);
                    });

                    TimeUnit.SECONDS.sleep(timeInterval.get());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println(String.format("%s canceled", Thread.currentThread().getName()));
                }
            }
        }
    }
}
