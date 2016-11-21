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

    private final IEventHandler eventHandler;
    private final BufferedReader reader;
    private boolean interupt = false;

    public ClientInput(IEventHandler eventHandler, InputStream inputStream) {
        this.eventHandler = eventHandler;
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public void shutdown() {
        interupt = true;
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
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
                    ex.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Sakra, dostal jsem se až na konec");
    }

    public interface IEventHandler {

        void handleEvent(InputEvent event);
    }
}
