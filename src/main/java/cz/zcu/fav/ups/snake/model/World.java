package cz.zcu.fav.ups.snake.model;

import cz.zcu.fav.ups.snake.model.event.EventType;
import cz.zcu.fav.ups.snake.model.event.InputEvent;
import cz.zcu.fav.ups.snake.model.event.OutputEvent;
import cz.zcu.fav.ups.snake.model.event.output.LoginOutputEvent;
import cz.zcu.fav.ups.snake.model.event.output.LogoutOutputEvent;
import cz.zcu.fav.ups.snake.model.food.Food;
import cz.zcu.fav.ups.snake.model.food.FoodGraphicsComponent;
import cz.zcu.fav.ups.snake.model.network.ClientInput;
import cz.zcu.fav.ups.snake.model.network.ClientOutput;
import cz.zcu.fav.ups.snake.model.snake.Snake;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Třída představující herní svět
 */
public final class World implements IUpdatable {

    // region Constants
    // Globální proměnná představující měřítko, podle kterého se všem objektům upravují velikosti
    public static final float SCALE = 1F;
    // endregion

    // region Variables
    private static int localPort = -1;
    // Plátno, na které se kreslí
    public final Canvas canvas;
    // Kolekce odchozích eventů
    public final Queue<OutputEvent> outputEventQueue = new ConcurrentLinkedQueue<>();

    public final BooleanProperty playing = new SimpleBooleanProperty(false);
    // Herní smyčka
    private final MainLoop loop;
    // Kolekce všech objektů, které se můžou hýbat
    private final Map<String, Snake> snakesOnMap = new HashMap<>();
    private final Map<String, Snake> snakesToAdd = new HashMap<>();
    private final List<String> snakesToRemove = new ArrayList<>();
    // Kolekce všech jídel - statické objekty
    private final Map<Integer, Food> foodOnMap = new HashMap<>();
    private final Map<Integer, Food> foodToAdd = new HashMap<>();
    private final List<Integer> foodToRemove = new ArrayList<>();
    private final Object mutex = new Object();
    private int width = 200;
    private int height = 200;
    private boolean singleplayer = false;
    //private boolean running = false;
    private String mySnakeID;
    private LostConnectionListener lostConnectionListener;
    private ClientInput clientInput;
    private ClientOutput clientOutput;
    private int lostConnectionCounter = 0;
    private Socket client;

    // endregion

    // region Constructors
    /**
     * Vytvoří novou instanci světa
     *
     * @param canvas Plátno, na které se kreslí
     */
    public World(Canvas canvas) {
        this.canvas = canvas;
        loop = new MainLoop();
    }
    // endregion

    // region Private methods
    private void generateFood() {
        GraphicsComponent graphicsComponent = new FoodGraphicsComponent();
        for (int i = 0; i < 100; i++) {
            foodOnMap.put(i, new Food(i, Vector2D.RANDOM(-width, -height, 2*width, 2*height), graphicsComponent));
        }
    }

    private void handleLostConnection() {
        if (!playing.get()) {
            return;
        }

        loop.stop();
        clear();
        if (lostConnectionListener != null) {
            lostConnectionListener.onConnectionLost();
        }
    }

    // endregion

    // region Public methods

    /**
     * Vyčistí veškeré proměnné
     */
    private void clear() {
        snakesOnMap.clear();
        snakesToAdd.clear();
        snakesToRemove.clear();
        foodOnMap.clear();
        foodToAdd.clear();
        foodToRemove.clear();
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        outputEventQueue.clear();
        clientInput = null;
        clientOutput = null;
        lostConnectionCounter = 0;
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Spustí herní smyčku
     */
    public void start() {
        if (playing.get()) {
            return;
        }

        loop.start();
    }

    public void startSingleplayer() {
        singleplayer = true;
        generateFood();
        start();
    }

    /**
     * Zastaví herní smysku
     */
    public void stop() {
        loop.stop();
        localPort = -1;
    }

    /**
     * Zavolá jeden krok v herní smyčce
     *
     * @param time Rozdíl mezi předchozím a aktuálním snímkem
     */
    public void step(long time) {
        loop.handle(time);
    }

    /**
     * Připojí hráče do hry
     *
     * @param loginModel Přihlašovací údaje
     * @param listener   @{@link ConnectedListener} Listener, který se používá pro handle připojení
     */
    public void connect(LoginModel loginModel, ConnectedListener listener) {
        new ConnectionThread(loginModel, listener).start();
    }

    public void addSnake(Snake snake) {
        snakesToAdd.put(snake.getID(), snake);
    }

    public void removeSnake(String uid) {
        snakesToRemove.add(uid);
    }

    public void addFood(int uid, Food food) {
        foodToAdd.put(uid, food);
    }

    public void removeFood(int uid) {
        foodToRemove.add(uid);
    }

    public int getWidth() {
        return width;
    }

    public World setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public World setHeight(int height) {
        this.height = height;
        return this;
    }

    public final Map<String, Snake> getSnakesOnMap() {
        return snakesOnMap;
    }

    public void setMySnakeID(String mySnakeID) {
        this.mySnakeID = mySnakeID;
    }

    public void setLostConnectionListener(LostConnectionListener lostConnectionListener) {

        this.lostConnectionListener = lostConnectionListener;
    }

    // endregion

    private void handleEvent(InputEvent event) {
        synchronized (mutex) {
            lostConnectionCounter = 0;
        }
        if (event.getType() == EventType.WORLD) {
            event.applyEvent(this);
        } else {
            if (snakesOnMap.containsKey(event.getUserID())) {
                snakesOnMap.get(event.getUserID()).addEvent(event);
            }
        }
    }

    public interface ConnectedListener {
        default void onConnected() {
        }

        default void onConnectionFailed() {
        }
    }

    public interface LostConnectionListener {
        default void onConnectionLost() {}
    }

    private final ClientInput.LostConnectionHandler inputLostConnectionHandler = this::handleLostConnection;

    private final ClientInput.EventHandler inputEventHandler = this::handleEvent;

    /**
     * Třída představující herní smyčku světa
     */
    private class MainLoop extends AnimationTimer {

        // region Constants
        private static final double MS_PER_SECOND = 10000000.0;
        // endregion

        // region Variables
        // lag
        private long lag = 0;
        //předchozí čas
        private long lastTime = 0;
        // endregion

        @Override
        public void handle(long now) {
            synchronized (mutex) {
                if (lostConnectionCounter > 500) {
                    handleLostConnection();
                } else {
                    lostConnectionCounter++;
                }
            }
            final long elapsed = now - lastTime;
            lastTime = now;
            lag += elapsed;

            snakesToRemove.forEach(snakesOnMap::remove);
            foodToRemove.forEach(foodOnMap::remove);
            snakesToRemove.clear();
            foodToRemove.clear();

            snakesOnMap.forEach((uid, object) -> object.inputComponent.handleInput(object));

            while (lag >= MS_PER_SECOND) {
                snakesOnMap.forEach((uid, object) -> object.physicsComponent.handlePhysics(object, lag));
                lag -= MS_PER_SECOND;
            }

            GraphicsContext graphics = canvas.getGraphicsContext2D();
            graphics.save();
            graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            graphics.setStroke(Color.RED);
            graphics.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());

            graphics.scale(SCALE, SCALE);

            graphics.translate((canvas.getWidth() / 2) / SCALE, (canvas.getHeight() / 2) / SCALE);

            final double divide = lag / MS_PER_SECOND;
            snakesOnMap.entrySet().stream()
                    .filter(stringSnakeEntry -> stringSnakeEntry.getKey().equals(mySnakeID))
                    .findFirst()
                    .ifPresent(stringSnakeEntry -> {
                        Snake snake = stringSnakeEntry.getValue();
                        snake.graphicsComponent.handleDraw(snake, graphics, divide);
                    });

            snakesOnMap.entrySet().stream()
                    .filter(stringSnakeEntry -> !stringSnakeEntry.getKey().equals(mySnakeID))
                    .forEach((stringSnakeEntry) -> {
                        Snake snake = stringSnakeEntry.getValue();
                        snake.graphicsComponent.handleDraw(snake, graphics, divide);
                    });
            foodOnMap.forEach((uid, food) -> food.graphicsComponent.handleDraw(food, graphics, divide));

            graphics.setStroke(Color.ORANGE);
            graphics.strokeRect(-width, -height, 2*width, 2*height);

            snakesToAdd.forEach((sid, snake) -> {
                snake.init(World.this);
                snakesOnMap.put(sid, snake);
            });
            foodToAdd.forEach(foodOnMap::put);
            snakesToAdd.clear();
            foodToAdd.clear();

            graphics.restore();

            if (!singleplayer && clientOutput != null)
                clientOutput.goWork();
        }

        @Override
        public void start() {
            lastTime = System.nanoTime();
            playing.setValue(true);
            super.start();
        }

        @Override
        public void stop() {
            super.stop();
            if (playing.get()) {
                LogoutOutputEvent event = new LogoutOutputEvent();
                if (clientOutput != null) {
                    outputEventQueue.clear();
                    outputEventQueue.add(event);
                    clientOutput.goWork();
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            playing.setValue(false);
            clear();
        }
    }

    private class ConnectionThread extends Thread {

        private final ConnectedListener mListener;
        private final LoginModel loginModel;

        private ConnectionThread(LoginModel loginModel, ConnectedListener mListener) {
            this.loginModel = loginModel;
            this.mListener = mListener;
        }

        @Override
        public void run() {
            try {
                Thread socketThread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            if (localPort != -1) {
                                client = new Socket(InetAddress.getByName(loginModel.getHost()), loginModel.getPort(), InetAddress.getLocalHost(), localPort);
                            } else {
                                client = new Socket(loginModel.getHost(), loginModel.getPort());
                                localPort = client.getLocalPort();
                            }
                        } catch (Exception ex) {
                            // TODO nevim co s tim :)
                        }
                    }
                };
                socketThread.start();
                socketThread.join(3000);

                InputStream input = client.getInputStream();
                OutputStream output = client.getOutputStream();

                clientInput = new ClientInput(inputEventHandler, inputLostConnectionHandler, input);
                clientOutput = new ClientOutput(outputEventQueue, new DataOutputStream(output));

                // Přidám do event qeue přihlašovací event, který se zpracuje při spuštění vlákna
                outputEventQueue.add(new LoginOutputEvent(loginModel.getUsername()));

                clientInput.start();
                clientOutput.start();

            } catch (Exception ex) {
                Platform.runLater(() -> {
                    if (mListener != null) {
                        mListener.onConnectionFailed();
                    }
                    System.out.println(ex.getMessage());
                });
                return;
            }

            Platform.runLater(() -> {
                if (mListener != null)
                    mListener.onConnected();
            });
        }
    }
}
