package model.Agent;

import javafx.beans.property.*;
import model.Account.Account;
import model.AgentThreadMonitor;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public abstract class Agent implements Runnable {
    public enum Type {
        Withdraw, Deposit;

        @Override
        public String toString() {
            return String.format("%s %s", this.name(), "Agent");
        }
    }

    public enum Status {Running, Stopped, Paused, Blocked}

    protected Account account;
    protected volatile boolean paused;
    protected volatile boolean isActive;
    protected Object lock = new Object();
    protected AtomicInteger atomicCounter = new AtomicInteger(1);
    protected AgentThreadMonitor agentThreadMonitor;

    protected LongProperty timeInterval = new SimpleLongProperty();
    protected DoubleProperty runningAmount = new SimpleDoubleProperty();
    protected DoubleProperty transferAmount = new SimpleDoubleProperty();
    protected ObjectProperty<Type> type = new SimpleObjectProperty<>();
    protected ObjectProperty<Status> status = new SimpleObjectProperty<>();
    protected IntegerProperty counter = new SimpleIntegerProperty();

    public Agent(Account account, Type type, long timeInterval, double transferAmount) {
        this.account = account;
        this.type.set(type);
        this.timeInterval.set(timeInterval);
        this.transferAmount.set(transferAmount);
        this.setStatus(Status.Running);

        isActive = true;
        agentThreadMonitor = AgentThreadMonitor.getInstance();
    }

    public void run() {
        synchronized (lock) {
            while (paused) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    public void resume() {
        synchronized (lock) {
            paused = false;
            setStatus(Status.Running);
            lock.notify();
        }
    }

    public void pause() {
        synchronized (lock) {
            paused = true;
            setStatus(Status.Paused);
        }
    }

    public void stop() {
        isActive = false;
        Thread t = agentThreadMonitor.observableRunningAgents().get(this);
        if (t != null) {
            t.interrupt();
        }
    }

    public int getCounter() {
        return counter.get();
    }

    public IntegerProperty counterProperty() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter.set(counter);
    }

    public Status getStatus() {
        return status.get();
    }

    public ObjectProperty<Status> statusProperty() {
        return status;
    }

    public void setStatus(Status status) {
        this.status.set(status);
    }

    public Type getType() {
        return type.get();
    }

    public ObjectProperty<Type> typeProperty() {
        return type;
    }

    public void setType(Type type) {
        this.type.set(type);
    }

    public double getTransferAmount() {
        return transferAmount.get();
    }

    public DoubleProperty transferAmountProperty() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount.set(transferAmount);
    }

    public double getRunningAmount() {
        return runningAmount.get();
    }

    public DoubleProperty runningAmountProperty() {
        return runningAmount;
    }

    public void setRunningAmount(double runningAmount) {
        this.runningAmount.set(runningAmount);
    }

    public Long getTimeInterval() {
        return timeInterval.get();
    }

    public LongProperty timeIntervalProperty() {
        return timeInterval;
    }

    public void setTimeInterval(Long timeInterval) {
        this.timeInterval.set(timeInterval);
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
