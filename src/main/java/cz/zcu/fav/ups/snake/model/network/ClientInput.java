package cz.zcu.fav.ups.snake.model.network;

import cz.zcu.fav.ups.snake.model.Protocol;
import cz.zcu.fav.ups.snake.model.event.InputEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Třída představující vlákno, které přijímá data ze serveru
 */
@SuppressWarnings("InfiniteLoopStatement")
public class ClientInput extends Thread {

    private final EventHandler eventHandler;
    private final LostConnectionHandler lostConnectionHandler;
    private final BufferedReader reader;
    private boolean interupt = false;

    public ClientInput(EventHandler eventHandler, LostConnectionHandler lostConnectionHandler, InputStream inputStream) {
        this.eventHandler = eventHandler;
        this.lostConnectionHandler = lostConnectionHandler;
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public void shutdown() {
        interupt = true;
        try {
            reader.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            String received;
            while((received = reader.readLine()) != null && !interupt) {
                try {
                    InputEvent event = Protocol.parseEvent(received);
                    eventHandler.handleEvent(event);
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        if (!interupt) {
            if (lostConnectionHandler != null) {
                lostConnectionHandler.handleLostConnection();
            }
        }
    }

    public interface EventHandler {

        void handleEvent(InputEvent event);
    }

    public interface LostConnectionHandler {
        void handleLostConnection();
    }
}
