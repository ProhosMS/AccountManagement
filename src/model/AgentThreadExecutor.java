package model;

import model.Agent.Agent;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class AgentThreadExecutor extends ThreadPoolExecutor {

    private final Map<Agent, Thread> inProgress = new ConcurrentHashMap<>();
    private final Map<Agent.Type, Boolean> withdrawInProgress = new ConcurrentHashMap<>();

    public AgentThreadExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        inProgress.put((Agent)r, t);
    }

    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        inProgress.remove((Agent) r);
    }

    public Map<Agent, Thread> getInProgress() {
        return inProgress;
    }
}
