package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import model.Agent.Agent;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class AgentThreadExecutor extends ThreadPoolExecutor {

    private final Map<Agent, Thread> runningAgents = new ConcurrentHashMap<>();
    private final ObservableMap<Agent, Thread> observableRunningAgents = FXCollections.observableMap(runningAgents);

    public AgentThreadExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        if (r instanceof Agent) {
            observableRunningAgents.put((Agent)r, t);
        }
    }

    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        if (r instanceof Agent) {
            observableRunningAgents.remove((Agent) r);
        }
    }

    public ObservableMap<Agent, Thread> getObservableRunningAgents() {
        return observableRunningAgents;
    }
}
