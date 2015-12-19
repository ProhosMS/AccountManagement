package model.Agent;

import javafx.application.Platform;
import model.Account.Account;
import model.Agent.Agent;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class DepositAgent extends Agent {
    public DepositAgent(Account account, Type type, long timeInterval, double transferAmount) {
        super(account, type, timeInterval, transferAmount);
    }

    @Override
    public void run() {
        while (isActive) {
            super.run();
             if (paused) {
                 break;
             }

            try {
                Double transfer = transferAmount.get();

                Platform.runLater(() -> {
                    account.deposit(transfer);
                    counter.set(atomicCounter.getAndIncrement());
                    runningAmount.set(runningAmount.get() + transfer);
                });

                Thread.sleep(timeInterval.get());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
