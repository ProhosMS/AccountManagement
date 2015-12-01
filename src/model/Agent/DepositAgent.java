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
            synchronized (lock) {
                while (paused && isActive) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    Double transfer = transferAmount.get();
                    Platform.runLater(() -> {
                        counter.set(atomicCounter.getAndIncrement());
                        account.deposit(transfer);
                        runningAmount.set(runningAmount.get() + transfer);
                    });

                    agentThreadMonitor
                            .inProgress()
                            .keySet()
                            .stream()
                            .filter(agent -> agent.getType().equals(Type.Withdraw))
                            .forEach(model.Agent.Agent::unblock);

                    TimeUnit.SECONDS.sleep(timeInterval.get());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println(String.format("%s canceled", Thread.currentThread().getName()));
                }
            }
        }
    }
}
