package cz.zcu.fav.ups.snake.model;


import cz.zcu.fav.ups.snake.model.event.InputEvent;
import cz.zcu.fav.ups.snake.model.event.input.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tovární třída, která vytváří eventy ze surových dat
 */
public class Protocol {

    private static final String EVENT_DELIMITER = ":";
    private static final String VALUE_DELIMITER = "\\|";
    private static final String INIT = "init";
    private static final String CHANGE_DIR = "changedir";
    private static final String SYNC = "sync";
    private static final String ADD_SNAKE = "addsnake";
    private static final String REMOVE_SNAKE = "remsnake";
    private static final String EAT_FOOD = "eatfood";
    private static final String GAME_OVER = "gameover";

    public static final int INDEX_SNAKE_ID = 0;
    public static final int INDEX_SNAKE_USERNAME = 1;
    public static final int INDEX_SNAKE_POS_X = 2;
    public static final int INDEX_SNAKE_POS_Y = 3;
    public static final int INDEX_SNAKE_DIR_X = 4;
    public static final int INDEX_SNAKE_DIR_Y = 5;
    public static final int INDEX_SNAKE_SCORE = 6;

    public static InputEvent parseEvent(String data) {
        final int delimiterIndex = data.indexOf(EVENT_DELIMITER);

        if (data.contains(INIT)) {
            String SIZE = "size:", PLAYERS = "players:", FOOD = "food:";
            String rawData = data.substring(delimiterIndex + 1);
            int indexOpenBracket = rawData.indexOf("{");
            int indexCloseBracket = rawData.indexOf("}");
            String snakeDataRaw = rawData.substring(indexOpenBracket + 1, indexCloseBracket);
            String[] snakeInfo = parseValues(snakeDataRaw, VALUE_DELIMITER);
            rawData = rawData.substring(rawData.indexOf(SIZE) + SIZE.length() + 1);
            indexCloseBracket = rawData.indexOf("}");
            String sizeDataRaw = rawData.substring(0, indexCloseBracket);
            String[] sizeInfo = parseValues(sizeDataRaw, VALUE_DELIMITER);
            rawData = rawData.substring(rawData.indexOf(PLAYERS) + PLAYERS.length() + 1);
            indexCloseBracket = rawData.indexOf("}");
            String playersDataRaw = rawData.substring(0, indexCloseBracket);
            List<String[]> playersInfo = parseSnakeInfoValues(playersDataRaw);
            rawData = rawData.substring(rawData.indexOf(FOOD) + FOOD.length() + 1);
            indexCloseBracket = rawData.indexOf("}");
            String foodDataRaw = rawData.substring(0, indexCloseBracket);
            List<String[]> foodInfo = parseFoodInfoValues(foodDataRaw);

            return new InitInputEvent(snakeInfo, sizeInfo, playersInfo, foodInfo);
        } else if (data.contains(CHANGE_DIR)) {
            final String uid = data.substring(data.indexOf("(") + 1, data.indexOf(")"));
            String rawData = data.substring(data.indexOf("[") + 1, data.indexOf("]"));
            String[] rawVectorArray = rawData.split(VALUE_DELIMITER);
            Vector2D vector = new Vector2D(Double.valueOf(rawVectorArray[0]), Double.valueOf(rawVectorArray[1]));

            return new SnakeChangeDirectionInputEvent(uid, vector);
        } else if (data.contains(REMOVE_SNAKE)) {
            String rawData = data.substring(delimiterIndex);
            final String uid = rawData.substring(rawData.indexOf("{") + 1, rawData.indexOf("}"));

            return new RemoveSnakeInputEvent(uid);
        } else if (data.contains(ADD_SNAKE)) {
            String rawData = data.substring(delimiterIndex + 1);
            int indexOpenBracket = rawData.indexOf("{");
            int indexCloseBracket = rawData.indexOf("}");
            String rawSnakeInfo = rawData.substring(indexOpenBracket + 1, indexCloseBracket);
            String[] snakeInfo = parseValues(rawSnakeInfo, VALUE_DELIMITER);

            return new AddSnakeInputEvent(snakeInfo);
        } else if (data.contains(SYNC)) {
            String rawData = data.substring(delimiterIndex + 1);
            int indexOpenBracket = rawData.indexOf("{");
            int indexCloseBracket = rawData.indexOf("}");
            String snakeDataRaw = rawData.substring(indexOpenBracket + 1, indexCloseBracket);
            List<String[]> snakeInfo = parseSnakeInfoValues(snakeDataRaw);

            return new SyncInputEvent(snakeInfo);
        } else if (data.contains(EAT_FOOD)) {
            String rawData = data.substring(delimiterIndex + 1);
            int indexOpenBracket = rawData.indexOf("{");
            int indexCloseBracket = rawData.indexOf("}");
            String rawSnakeInfo = rawData.substring(indexOpenBracket + 1, indexCloseBracket);
            String[] eatInfo = parseValues(rawSnakeInfo, VALUE_DELIMITER);

            return new EatFoodInputEvent(eatInfo);
        } else if (data.contains(GAME_OVER)) {
            String rawData = data.substring(delimiterIndex);
            final String uid = rawData.substring(rawData.indexOf("{") + 1, rawData.indexOf("}"));

            return new GameOverInputEvent(uid);
        }

        throw new IllegalArgumentException("Nebyl rozeznán event: " + data);
    }

    private static String[] parseValues(String rawData, String delimiter) {
        rawData = rawData.replaceAll("\\[|\\]", "");
        return rawData.split(delimiter);
    }

    private static List<String[]> parseSnakeInfoValues(String rawData) {
        if (rawData.isEmpty()) {
            return new ArrayList<>();
        }
        String[] rawArray = rawData.split(",");
        List<String[]> info = new ArrayList<>(rawArray.length);
        Arrays.stream(rawArray).forEach(s -> info.add(parseValues(s, VALUE_DELIMITER)));
        return info;
    }

    private static List<String[]> parseFoodInfoValues(String rawData) {
        if (rawData.isEmpty()) {
            return new ArrayList<>();
        }
        String[] rawArray = rawData.split(",");
        List<String[]> info = new ArrayList<>(rawArray.length);
        Arrays.stream(rawArray).forEach(s -> info.add(parseValues(s, VALUE_DELIMITER)));
        return info;
    }

}
