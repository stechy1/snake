package cz.zcu.fav.ups.snake.model;


import cz.zcu.fav.ups.snake.model.event.InputEvent;
import cz.zcu.fav.ups.snake.model.event.input.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Tovární třída, která vytváří eventy ze surových dat
 */
public class Protocol {

    private static final String EVENT_DELIMITER = ":";
    private static final String VALUE_DELIMITER = "\\|";
    private static final String INIT = "init";
    private static final String CHANGE_DIR = "changedir";
    private static final String ADD_SNAKE = "addsnake";
    private static final String REMOVE_SNAKE = "remsnake";
    private static final String SYNC = "sync";

    public static InputEvent parseEvent(String data) {
        final int delimiterIndex = data.indexOf(EVENT_DELIMITER);

        if (data.contains(INIT)) {
            String SIZE = "size:", PLAYERS = "players:", FOOD = "food:";
            String rawData = data.substring(delimiterIndex + 1);
            int indexOpenBracket = rawData.indexOf("{");
            int indexCloseBracket = rawData.indexOf("}");
            String snakeDataRaw = rawData.substring(indexOpenBracket + 1, indexCloseBracket);
            double[] snakeInfo = parseValues(snakeDataRaw, VALUE_DELIMITER);
            rawData = rawData.substring(rawData.indexOf(SIZE) + SIZE.length() + 1);
            indexCloseBracket = rawData.indexOf("}");
            String sizeDataRaw = rawData.substring(0, indexCloseBracket);
            double[] sizeInfo = parseValues(sizeDataRaw, VALUE_DELIMITER);
            rawData = rawData.substring(rawData.indexOf(PLAYERS) + PLAYERS.length() + 1);
            indexCloseBracket = rawData.indexOf("}");
            String playersDataRaw = rawData.substring(0, indexCloseBracket);
            List<double[]> playersInfo = parseValues(playersDataRaw);
            rawData = rawData.substring(rawData.indexOf(FOOD) + FOOD.length() + 1);
            indexCloseBracket = rawData.indexOf("}");
            String foodDataRaw = rawData.substring(0, indexCloseBracket);
            List<double[]> foodInfo = parseValues(foodDataRaw);

            return new InitInputEvent(snakeInfo, sizeInfo, playersInfo, foodInfo);
        } else if (data.contains(CHANGE_DIR)) {
            final int uid = Integer.parseInt(data.substring(data.indexOf("{") + 1, data.indexOf("}")));
            String rawData = data.substring(delimiterIndex + 1);
            String[] rawVectorArray = rawData.split(VALUE_DELIMITER);
            Vector2D vector = new Vector2D(Double.valueOf(rawVectorArray[0]), Double.valueOf(rawVectorArray[1]));

            return new SnakeChangeDirectionInputEvent(uid, vector);
        } else if (data.contains(REMOVE_SNAKE)) {
            final int uid = Integer.parseInt(data.substring(data.indexOf("{") + 1, data.indexOf("}")));

            return new RemoveSnakeInputEvent(uid);
        } else if (data.contains(ADD_SNAKE)) {
            String rawData = data.substring(delimiterIndex + 1);
            int indexOpenBracket = rawData.indexOf("{");
            int indexCloseBracket = rawData.indexOf("}");
            String rawPlayerInfo = rawData.substring(indexOpenBracket + 1, indexCloseBracket);
            double[] playerInfo = parseValues(rawPlayerInfo, VALUE_DELIMITER);

            return new AddSnakeInputEvent(playerInfo);
        } else if (data.contains(SYNC)) {
            String rawData = data.substring(delimiterIndex + 1);
            int indexOpenBracket = rawData.indexOf("{");
            int indexCloseBracket = rawData.indexOf("}");
            String snakeDataRaw = rawData.substring(indexOpenBracket + 1, indexCloseBracket);
            List<double[]> snakeInfo = parseValues(snakeDataRaw);

            return new SyncInputEvent(snakeInfo);
        } else

        throw new IllegalArgumentException("Nebyl rozeznán event: " + data);
    }

    private static List<double[]> parseValues(String rawData) {
        List<double[]> list = new LinkedList<>();
        if (rawData.isEmpty()) {
            return list;
        }

        String[] rawValues = rawData.split(",");
        for (String rawValue : rawValues) {
            list.add(parseValues(rawValue, VALUE_DELIMITER));
        }

        return list;
    }

    private static double[] parseValues(String rawData, String delimiter) {
        rawData = rawData.replaceAll("\\[|\\]", "");
        return Arrays.stream(rawData.split(delimiter))
                .mapToDouble(Double::parseDouble)
                .toArray();
    }

}
