package br.com.bertolucci.mtgtools.deckbuilder.infra.util;

import java.util.ArrayList;
import java.util.List;

public abstract class MultiThreadUtil {

    private int numberOfThreads;
    private int taskQuotient;
    private List tasks;

    public MultiThreadUtil(int numberOfThreads, List tasks) {
        this.numberOfThreads = numberOfThreads;
        this.taskQuotient = tasks.size() / numberOfThreads;
        this.tasks = tasks;
    }

    public void start() {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            int start = taskQuotient*i;
            int end = i == (numberOfThreads - 1) ? tasks.size(): taskQuotient*(i+1);
            threads.add(new Thread(setRunnable(start, end)));
        }
        threads.forEach(Thread::start);
        while (threads.stream().anyMatch(Thread::isAlive));
    }

    protected abstract Runnable setRunnable(int start, int end);
}
