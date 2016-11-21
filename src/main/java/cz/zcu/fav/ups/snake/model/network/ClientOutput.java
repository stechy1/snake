package cz.zcu.fav.ups.snake.model.network;

import cz.zcu.fav.ups.snake.model.event.OutputEvent;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.Semaphore;

/**
 * Třída představující vlákno, které posílá data ven na server
 */
public class ClientOutput extends Thread {

    private final Semaphore semaphore = new Semaphore(0);

    private final Queue<OutputEvent> outputEventQeue;
    private final DataOutputStream outputStream;
    private boolean running;
    private boolean interupt;

    public ClientOutput(Queue<OutputEvent> outputEventQeue, DataOutputStream outputStream) {
        this.outputEventQeue = outputEventQeue;
        this.outputStream = outputStream;
        running = false;
        interupt = false;
    }

    public synchronized void goWork() {
        if (running)
            return;

        running = true;
        semaphore.release();
    }

    public void shutdown() {
        interupt = true;
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        outputEventQeue.clear();
        semaphore.release();
    }

    @Override
    public void run() {
        while(!interupt) {
            while(outputEventQeue.isEmpty()) {
                try {
                    running = false;
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    running = true;
                }
            }

            while(!outputEventQeue.isEmpty()) {
                OutputEvent event = outputEventQeue.poll();

                try {
                    outputStream.write(event.getData());
                } catch (IOException e) {
                    running = false;
                    interupt = true;
                    break;
                }
            }
        }

    }
}
