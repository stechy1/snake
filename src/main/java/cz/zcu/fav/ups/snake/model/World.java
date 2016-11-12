package cz.zcu.fav.ups.snake.model;

import cz.zcu.fav.ups.snake.model.event.EventType;
import cz.zcu.fav.ups.snake.model.event.InputEvent;
import cz.zcu.fav.ups.snake.model.event.OutputEvent;
import cz.zcu.fav.ups.snake.model.event.output.DebugOutputEvent;
import cz.zcu.fav.ups.snake.model.event.output.LoginOutputEvent;
import cz.zcu.fav.ups.snake.model.event.output.LogoutOutputEvent;
import cz.zcu.fav.ups.snake.model.food.Food;
import cz.zcu.fav.ups.snake.model.food.FoodGraphicsComponent;
import cz.zcu.fav.ups.snake.model.network.ClientInput;
import cz.zcu.fav.ups.snake.model.network.ClientOutput;
import cz.zcu.fav.ups.snake.model.snake.Snake;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Třída představující herní svět
 */
public final class World implements ClientInput.IEventHandler, IUpdatable {

    // region Constants
    // Globální proměnná představující měřítko, podle kterého se všem objektům upravují velikosti
    public static final float SCALE = 1F;
    // endregion

    // region Variables

    // Herní smyčka
    private final MainLoop loop;
    // Plátno, na které se kreslí
    public final Canvas canvas;
    // Příznak určující, zda-li se bude herní smyčka opakovat do nekonečna, nebo pouze jednou
    private boolean noLoop = false;
    private int width = 100;
    private int height = 100;
    private boolean singleplayer = false;

    // Kolekce všech objektů, které se můžou hýbat
    private final Map<Integer, Snake> snakesOnMap = new HashMap<>();
    private final Map<Integer, Snake> snakesToAdd = new HashMap<>();
    private final List<Integer> snakesToRemove = new LinkedList<>();
    // Kolekce všech jídel - statické objekty
    private final Map<Integer, Food> foodOnMap = new HashMap<>();
    private final Map<Integer, Food> foodToAdd = new HashMap<>();
    private final List<Integer> foodToRemove = new LinkedList<>();

    // Kolekce odchozích eventů
    public final Queue<OutputEvent> outputEventQueue = new ConcurrentLinkedQueue<>();

    private ClientInput clientInput;
    private ClientOutput clientOutput;
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

//        Snake snake = new Snake(1, 25,
//                new SnakeMouseInputComponent(),
//                new SnakePhysicsComponent(),
//                new SnakeGraphicsComponent(), Vector2D.ZERO, Vector2D.RIGHT, new TailCircleGraphicsComponent());
//
//        snake.init(this);
//        snakesOnMap.put(1, snake);
//        generateFood();
    }
    // endregion

    // region Private methods
    private void generateFood() {
        GraphicsComponent graphicsComponent = new FoodGraphicsComponent();
        for (int i = 0; i < 100; i++) {
            foodOnMap.put(i, new Food(i, Vector2D.RANDOM(-width, -height, 2*width, 2*height), graphicsComponent));
        }
    }
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
        outputEventQueue.clear();
        clientInput = null;
        clientOutput = null;
    }
    // endregion

    // region Public methods

    /**
     * Spustí herní smyčku
     */
    public void start() {
        loop.start();
    }

    public void startSingleplayer() {
        singleplayer = true;
        start();
    }

    /**
     * Zastaví herní smysku
     */
    public void stop() {
        loop.stop();
    }

    /**
     * Zavolá jeden krok v herní smyčce
     *
     * @param time Rozdíl mezi předchozím a aktuálním snímkem
     */
    public void step(long time) {
        loop.handle(time);
    }

    public void debug(String message) {
        OutputEvent event = new DebugOutputEvent(message);
        outputEventQueue.add(event);
        clientOutput.goWork();
    }

    /**
     * Nastaví příznak aby se herní smyčka spustila pouze jednou
     */
    public void noLoop() {
        noLoop = true;
    }

    /**
     * Připojí hráče do hry
     *
     * @param loginModel Přihlašovací údaje
     * @param listener @{@link ConnectedListener} Listener, který se používá pro handle připojení
     */
    public void connect(LoginModel loginModel, ConnectedListener listener) {
        new ConnectionThread(loginModel, listener).start();
    }

    public void addSnake(Snake snake) {
        snakesToAdd.put(snake.getID(), snake);
    }

    public void removeSnake(int uid) {
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

    public final Map<Integer, Snake> getSnakesOnMap() {
        return snakesOnMap;
    }

    @Override
    public void handleEvent(InputEvent event) {
        if (event.getType() == EventType.WORLD) {
            event.applyEvent(this);
        } else {
            if (snakesOnMap.containsKey(event.getUserID())) {
                snakesOnMap.get(event.getUserID()).addEvent(event);
            }
        }
    }
    // endregion

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
        private boolean running = false;
        // endregion

        @Override
        public void handle(long now) {
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

            GraphicsContext graphic = canvas.getGraphicsContext2D();
            graphic.save();
            graphic.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            graphic.setStroke(Color.RED);
            graphic.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());

            graphic.scale(SCALE, SCALE);

            graphic.translate((canvas.getWidth() / 2) / SCALE, (canvas.getHeight() / 2) / SCALE);

            final double divide = lag / MS_PER_SECOND;
            snakesOnMap.forEach((uid, object) -> object.graphicsComponent.handleDraw(object, graphic, divide));
            foodOnMap.forEach((uid, food) -> food.graphicsComponent.handleDraw(food, graphic, divide));

            snakesToAdd.forEach((sid, snake) -> {
                snake.init(World.this);
                snakesOnMap.put(sid, snake);
            });
            foodToAdd.forEach(foodOnMap::put);
            snakesToAdd.clear();
            foodToAdd.clear();

            graphic.restore();

            if (!singleplayer)
                clientOutput.goWork();

            if (noLoop) {
                loop.stop();
            }
        }

        @Override
        public void start() {
            lastTime = System.nanoTime();
            running = true;
            super.start();
        }

        @Override
        public void stop() {
            super.stop();
            if (running) {
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
            running = false;
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
                Socket client = new Socket(InetAddress.getByName(loginModel.getHost()), loginModel.getPort());
                InputStream input = client.getInputStream();
                OutputStream output = client.getOutputStream();

                clientInput = new ClientInput(World.this, input);
                clientOutput = new ClientOutput(outputEventQueue, new DataOutputStream(output));

                // Přidám do event qeue přihlašovací event, který se zpracuje při spuštění vlákna
                outputEventQueue.add(new LoginOutputEvent(loginModel.getUsername()));

                clientInput.start();
                clientOutput.start();

            } catch (IOException ex) {
                Platform.runLater(() -> {
                    if (mListener != null) {
                        mListener.onConnectionFailed();
                    }
                });
                return;
            }

            Platform.runLater(() -> {
                if (mListener != null)
                    mListener.onConnected();
            });
        }
    }

    public interface ConnectedListener {
        default void onConnected() {
        }

        default void onConnectionFailed() {
        }
    }
}
