package cz.zcu.fav.ups.snake.model;

import cz.zcu.fav.ups.snake.model.events.DebugEvent;
import cz.zcu.fav.ups.snake.model.events.GameEvent;
import cz.zcu.fav.ups.snake.model.events.LoginEvent;
import cz.zcu.fav.ups.snake.model.food.Food;
import cz.zcu.fav.ups.snake.model.network.ClientInput;
import cz.zcu.fav.ups.snake.model.network.ClientOutput;
import cz.zcu.fav.ups.snake.model.snake.*;
import cz.zcu.fav.ups.snake.model.snake.tail.TailCircleGraphicsComponent;
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
    public static final float SCALE = 0.2F;
    // endregion

    // region Variables

    // Herní smyčka
    private final MainLoop loop;
    // Plátno, na které se kreslí
    public final Canvas canvas;
    // Příznak určující, zda-li se bude herní smyčka opakovat do nekonečna, nebo pouze jednou
    private boolean noLoop = false;
    private int width = 800;
    private int height = 600;

    // Kolekce všech objektů, které se můžou hýbat
    //private final List<BaseObject> snakesOnMap = new ArrayList<>();
    private final Map<Integer, Snake> snakesOnMap = new HashMap<>();
    private final Map<Integer, Snake> snakesToAdd = new HashMap<>();
    private final List<Integer> snakesToRemove = new LinkedList<>();
    // Kolekce všech jídel - statické objekty
    //private final List<Food> foodList = new ArrayList<>();
    private final Map<Integer, Food> foodOnMap = new HashMap<>();
    private final Map<Integer, Food> foodToAdd = new HashMap<>();
    private final List<Integer> foodToRemove = new LinkedList<>();


    // Kolekce odchozích eventů
    public final Queue<GameEvent> outputEventQeue = new ConcurrentLinkedQueue<>();

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

//        loadFood();
    }
    // endregion

    // region Public methods

    /**
     * Spustí herní smyčku
     */
    public void start() {
        loop.start();
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
        GameEvent event = new DebugEvent(message);
        outputEventQeue.add(event);
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

    public void addSnake(int uid, Snake snake) {
        snakesToAdd.put(uid, snake);
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

    @Override
    public void handleEvent(GameEvent event) {
        if (event.getType() == GameEvent.EventType.WORLD) {
            event.applyEvent(this);
        } else {
            snakesOnMap.get(event.getUserId()).addEvent(event);
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
        // endregion

        @Override
        public void handle(long now) {
            final long elapsed = now - lastTime;
            lastTime = now;
            lag += elapsed;

            snakesToRemove.forEach(snakesOnMap::remove);
            snakesToRemove.clear();

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

            graphic.strokeRect(-width, -height, width, height);
            graphic.setFill(Color.LIGHTGRAY);
            graphic.fillRect(-width, -height, width, height);
            graphic.translate((canvas.getWidth() / 2) / SCALE, (canvas.getHeight() / 2) / SCALE);

            final double divide = lag / MS_PER_SECOND;
            snakesOnMap.forEach((uid, object) -> object.graphicsComponent.handleDraw(object, graphic, divide));
            foodOnMap.forEach((uid, food) -> food.graphicsComponent.handleDraw(food, graphic, divide));

            snakesToAdd.forEach((sid, snake) -> {
                snake.init(World.this);
                snakesOnMap.put(sid, snake);
            });
            snakesToAdd.clear();

            graphic.restore();

            clientOutput.goWork();

            if (noLoop) {
                loop.stop();
            }
        }

        @Override
        public void start() {
            lastTime = System.nanoTime();
            super.start();
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

                clientInput = new ClientInput(World.this, new BufferedInputStream(input));
                clientOutput = new ClientOutput(outputEventQeue, new DataOutputStream(output));

                // Přidám do event qeue přihlašovací event, který se zpracuje při spuštění vlákna
                outputEventQeue.add(new LoginEvent(loginModel.getUsername()));

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
