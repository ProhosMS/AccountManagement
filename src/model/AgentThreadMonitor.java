package model;

import model.Agent.Agent;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class AgentThreadMonitor {

    private static AgentThreadMonitor instance = null;
    private static final int NUM_THREADS = 10;

    private AgentThreadExecutor taskExecutor;

    private AgentThreadMonitor() {
        taskExecutor = new AgentThreadExecutor(NUM_THREADS, NUM_THREADS,
                                                0L, TimeUnit.MILLISECONDS,
                                                new LinkedBlockingQueue<Runnable>());
    }

    public static AgentThreadMonitor getInstance() {
        if (instance == null) {
            instance = new AgentThreadMonitor();
        }
        return instance;
    }

    public void execute(Agent agent) {
        taskExecutor.execute(agent);
    }

    public Future<?> submit(Agent agent) {
        return taskExecutor.submit(agent);
    }

    public Map<Agent, Thread> inProgress() {
        return taskExecutor.getInProgress();
    }

    public void unblockAgents() {
        /* I need an internal list of agents that are running */
    }

    public void shutDown() {
        try {
            System.out.println("attempt to shutdown executor");
            taskExecutor.shutdown();
            taskExecutor.awaitTermination(5, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        } finally {
            if (!taskExecutor.isTerminated()) {
                System.err.println("cancel non-finished tasks");
            }
            taskExecutor.shutdownNow();
            System.out.println("shutdown finished");
        }
    }
}
