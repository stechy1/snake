package cz.zcu.fav.ups.snake.model.network;

import cz.zcu.fav.ups.snake.model.Protocol;
import cz.zcu.fav.ups.snake.model.events.GameEvent;

import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * Třída představující vlákno, které přijímá data ze serveru
 */
@SuppressWarnings("InfiniteLoopStatement")
public class ClientInput extends Thread {

    private static final int BUFFER_SIZE = 1024;
    private static final String DELIMITER = ";";

    private final IEventHandler eventHandler;
    private final BufferedInputStream inputStream;

    private final byte[] buffer = new byte[BUFFER_SIZE];

    private String recBuffer = "";

    public ClientInput(IEventHandler eventHandler, BufferedInputStream inputStream) {
        this.eventHandler = eventHandler;
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        try {
            while(inputStream.read(buffer) != -1) {
                String received = new String(buffer);
                received = recBuffer + received;
                System.out.println(received);
                int index;
                while((index = received.indexOf(DELIMITER)) != -1) {
                    int size = received.length();
                    String tmp = received.substring(0, index);
                    GameEvent event = Protocol.parseEvent(tmp);
                    eventHandler.handleEvent(event);

                    if (size - index + 1 > 0) {
                        received = received.substring(index + 1, size);
                    } else {
                        received = "";
                    }
                }

                recBuffer = received;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Sakra, dostal jsem se až na konec");
    }

    public interface IEventHandler {

        void handleEvent(GameEvent event);
    }
}
