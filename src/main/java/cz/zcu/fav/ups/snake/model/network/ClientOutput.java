package cz.zcu.fav.ups.snake.model.network;

import cz.zcu.fav.ups.snake.model.events.GameEvent;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * Třída představující vlákno, které posílá data ven na server
 */
@SuppressWarnings("InfiniteLoopStatement")
public class ClientOutput extends Thread {

    private final Semaphore semaphore = new Semaphore(0);

    private final Queue<GameEvent> outputEventQeue;
    private final DataOutputStream outputStream;

    public ClientOutput(Queue<GameEvent> outputEventQeue, DataOutputStream outputStream) {
        this.outputEventQeue = outputEventQeue;
        this.outputStream = outputStream;
    }

    public void goWork() {
        semaphore.release();
    }

    @Override
    public void run() {
        for(;;) {
            if(outputEventQeue.isEmpty()) {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            GameEvent event = outputEventQeue.poll();
            try {
                outputStream.write(event.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
