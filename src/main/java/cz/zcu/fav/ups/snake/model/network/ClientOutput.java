package cz.zcu.fav.ups.snake.model.network;

import cz.zcu.fav.ups.snake.model.events.GameEvent;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.Semaphore;

/**
 * Třída představující vlákno, které posílá data ven na server
 */
@SuppressWarnings("InfiniteLoopStatement")
public class ClientOutput extends Thread {

    private final Semaphore semaphore = new Semaphore(0);

    private final Queue<GameEvent> outputEventQeue;
    private final DataOutputStream outputStream;
    private boolean running;
    private boolean interupt;

    public ClientOutput(Queue<GameEvent> outputEventQeue, DataOutputStream outputStream) {
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
                GameEvent event = outputEventQeue.poll();

                try {
                    outputStream.write(event.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                    running = false;
                    interupt = true;
                    break;
                }
            }
        }
    }
}
