package cz.zcu.fav.ups.snake.model.network;

import cz.zcu.fav.ups.snake.model.events.GameEvent;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Queue;

/**
 * Třída představující vlákno, které přijímá data ze serveru
 */
@SuppressWarnings("InfiniteLoopStatement")
public class ClientInput extends Thread {

    private static final int BUFFER_SIZE = 1024;

    private final Queue<GameEvent> inputEventQeue;
    private final BufferedInputStream inputStream;

    private final byte[] buffer = new byte[BUFFER_SIZE];

    public ClientInput(Queue<GameEvent> inputEventQeue, BufferedInputStream inputStream) {
        this.inputEventQeue = inputEventQeue;
        this.inputStream = inputStream;
    }

    @Override
    public void run() {

        try {
            while(inputStream.read(buffer) != -1) {
                String readed = new String(buffer);
                System.out.println(readed);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Sakra, dostal jsem se až na konec");
    }
}
