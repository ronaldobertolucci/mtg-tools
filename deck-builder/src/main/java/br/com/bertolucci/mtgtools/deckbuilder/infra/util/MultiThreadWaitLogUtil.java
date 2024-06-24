package br.com.bertolucci.mtgtools.deckbuilder.infra.util;

public abstract class MultiThreadWaitLogUtil {

    public void start(String logMessage) throws InterruptedException {
        Thread th0 = new Thread(setRunnable());
        th0.start();

        int i = 0;
        while (th0.isAlive()) {
            Thread.sleep(2000);
            String message = logMessage + " [" + ".".repeat(i) + " ".repeat(3-i) + "] - Aguarde";
            System.out.println(message);
            if (i == 3) {
                i = 0;
                continue;
            }
            i++;
        }
    }

    protected abstract Runnable setRunnable();
}
