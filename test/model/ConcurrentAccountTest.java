package model;

import model.Account.Account;
import model.Account.ConcurrentAccount;
import model.Agent.Agent;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import util.JavaFXInitializer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static org.mockito.Mockito.times;
import static org.testng.Assert.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class ConcurrentAccountTest {

    @BeforeClass
    public void setUp() throws InterruptedException {
        JavaFXInitializer.initialize();
    }

    @Test
    public void testDeposit_balanceIsCorrect() throws InterruptedException {
        /* Inherently I can't make this deterministic */
        Account testAccount = new ConcurrentAccount("ConcurrentAccount", "1000", 0.0);

        Runnable depositTask = () -> {
            for (int i = 0; i < 100000; ++i) {
                testAccount.deposit(1);
            }
        };

        Thread depositThread1 = new Thread(depositTask);
        Thread depositThread2 = new Thread(depositTask);

        depositThread1.start();
        depositThread2.start();

        depositThread1.join();
        depositThread2.join();

        assertEquals(testAccount.getBalance(), 200000.0);
    }

    @Test
    public void testWithdraw_balanceIsCorrect() throws InterruptedException {
        /* Inherently I can't make this deterministic */
        Account testAccount = new ConcurrentAccount("ConcurrentAccount", "1000", 200000.0);

        Runnable withdrawTask = () -> {
            for (int i = 0; i < 100000; ++i) {
                testAccount.withdraw(1);
            }
        };

        Thread withdrawThread1 = new Thread(withdrawTask);
        Thread withdrawThread2 = new Thread(withdrawTask);

        withdrawThread1.start();
        withdrawThread2.start();

        withdrawThread1.join();
        withdrawThread2.join();

        assertEquals(testAccount.getBalance(), 0.0);
    }
}
