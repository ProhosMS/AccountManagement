package model.Agent;

import javafx.application.Platform;
import model.Account.Account;

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
            super.run();
            if (paused) {
                break;
            }

            Double transfer = transferAmount.get();
            account.withdraw(transfer, this);

            Platform.runLater(() -> {
                setStatus(Status.Running);
                counter.set(atomicCounter.getAndIncrement());
                runningAmount.set(runningAmount.get() + transfer);
            });

            try {
                Thread.sleep(timeInterval.get());
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
