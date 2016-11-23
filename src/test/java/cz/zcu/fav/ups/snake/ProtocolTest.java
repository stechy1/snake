package cz.zcu.fav.ups.snake;

import cz.zcu.fav.ups.snake.model.Protocol;
import cz.zcu.fav.ups.snake.model.event.InputEvent;
import org.junit.Test;

/**
 * Testování třídy Protocol
 */
public class ProtocolTest {

    @Test
    public void initInputEventTest() throws Exception {
        final String inputData = "(3fd75210-5fa0-5e75-aaf6-9a45822abd6c)" +
                "init:{[3fd75210-5fa0-5e75-aaf6-9a45822abd6c|test1|73.186735038000791|60.765385330079795|1|0|25]}" +
                "size:{[300|300]}" +
                "players:{}" +
                "food:{[0|24.678495717417817|-112.6394255278683],[1|114.44103143717346|-78.293858099200435]," +
                "[2|76.78815312933159|32.664656315042279],[3|71.94524394106233|43.710296228925316]," +
                "[4|-56.453825938859779|-119.25225894730315],[5|-78.108085428947106|16.236318156499308]," +
                "[6|98.550988440392359|17.697451158818012],[7|109.92610731757401|-64.205020835718642]," +
                "[8|-1.6277783491378841|89.281904040501445],[9|-123.75530986157165|-81.732183273890058]}";

        InputEvent event = Protocol.parseEvent(inputData);
    }

    @Test
    public void initWithPlayersInputEventTest() throws Exception {
        final String inputData = "(0a34da95-a980-5003-ac38-954fd6b83e38)" +
                "init:{[0a34da95-a980-5003-ac38-954fd6b83e38|test2|0.18550690405194814|-91.477822215718419|1|0|25]}" +
                "size:{[300|300]}" +
                "players:{" +
                    "[0ee3b15b-0e5b-5e86-82c1-e5589d2f2ba4|test1|97.5|77.230444247209959|1|0|25]," +
                    "[3fd75210-5fa0-5e75-aaf6-9a45822abd6c|test3|-7.5|60.765385330079795|1|0|25}" +
                "food:{" +
                    "[0|24.678495717417817|-112.6394255278683],[1|114.44103143717346|-78.293858099200435]," +
                    "[2|76.78815312933159|32.664656315042279],[3|71.94524394106233|43.710296228925316]," +
                    "[4|-56.453825938859779|-119.25225894730315],[5|-78.108085428947106|16.236318156499308]," +
                    "[6|98.550988440392359|17.697451158818012],[7|109.92610731757401|-64.205020835718642]," +
                    "[8|-1.6277783491378841|89.281904040501445],[9|-123.75530986157165|-81.732183273890058]}";

        InputEvent event = Protocol.parseEvent(inputData);
    }

    @Test
    public void addSnakeInputEventTest() throws Exception {
        final String inputData = "(0ee3b15b-0e5b-5e86-82c1-e5589d2f2ba4)" +
                "addsnake:{[0a34da95-a980-5003-ac38-954fd6b83e38|test2|0.18550690405194814|-91.477822215718419|1|0|25]}";

        InputEvent event = Protocol.parseEvent(inputData);
    }

    @Test
    public void syncInputEventTest() throws Exception {
        final String inputData = "(9a4b061e-71d4-5ebc-8a13-95061cbe42ba)" +
                "sync:{" +
                    "[9a4b061e-71d4-5ebc-8a13-95061cbe42ba|test2|226.38264856548574|84.625876568017105|1|0|25]," +
                    "[e2ea1513-c455-54e8-8099-b77c7643be43|test1|52.5|15.859991658679874|1|0|25]}";

        InputEvent event = Protocol.parseEvent(inputData);
    }

    @Test
    public void changedirInputEventTest() throws Exception {
        final String inputData = "(f9c5cbbf-c156-5908-805a-2498d81cc28b)changedir:{[-0.99986114003960003|-0.016664352333993333]}";

        InputEvent event = Protocol.parseEvent(inputData);

    }

    @Test
    public void removeSnakeInputEventTest() throws Exception {
        final String inputData = "remsnake:{0a34da95-a980-5003-ac38-954fd6b83e38}";

        InputEvent event = Protocol.parseEvent(inputData);

    }
}
