package org.bxwbb.event;

import javax.swing.*;

public class RenderLoop {

    private final Runnable onRender;
    private Thread renderThread;
    private volatile boolean running = true;

    public RenderLoop(Runnable onRender) {
        this.onRender = onRender;
        start();
    }

    private void start() {
        renderThread = new Thread(() -> {
            final long frameTime = 1000 / 60;
            while (running) {
                long start = System.currentTimeMillis();

                SwingUtilities.invokeLater(onRender);

                long spend = System.currentTimeMillis() - start;
                long sleep = frameTime - spend;
                if (sleep > 0) {
                    try {
                        Thread.sleep(sleep);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        });
        renderThread.start();
    }

    public void stop() {
        running = false;
        renderThread.interrupt();
    }
}