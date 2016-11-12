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
        final String inputData = "(64e23f84-9351-5f0e-a83a-cf845d8c4bae)" +
                "init:{(64e23f84-9351-5f0e-a83a-cf845d8c4bae)[-48.857408|-1.393302|1.000000|0.000000|25.000000]}" +
                "size:{[300.000000|300.000000]}" +
                "players:{}" +
                "food:{[0.000000|91.299967|-16.632577],[1.000000|36.772042|-66.585476],[2.000000|47.379812|-91.530624]," +
                "[3.000000|-2.108897|15.449813],[4.000000|95.182230|48.991074],[5.000000|-7.680315|91.580037]," +
                "[6.000000|107.756888|-44.126601],[7.000000|-8.736687|-4.227388],[8.000000|3.048429|52.540332]," +
                "[9.000000|103.413259|-38.702094]}";

        InputEvent event = Protocol.parseEvent(inputData);
    }

    @Test
    public void initWithPlayersInputEventTest() throws Exception {
        final String inputData = "(09a32633-5db9-53cb-8c4b-7abc01495334)" +
                "init:{[(09a32633-5db9-53cb-8c4b-7abc01495334)-23.526139|-14.087549|1.000000|0.000000|25.000000]}" +
                "size:{[300.000000|300.000000]}" +
                "players:{(015f55f4-f166-5b22-acf0-fc9da443b4e9)[213.000008|-1.393302|1.000000|0.000000|25.000000]}" +
                "food:{[0.000000|-94.078538|-39.934933],[1.000000|-74.329794|86.310695],[2.000000|-54.394213|-45.586766]," +
                "[3.000000|-76.479535|24.384227],[4.000000|117.755036|-30.121556],[5.000000|60.610215|111.528110]," +
                "[6.000000|-4.717194|-111.955567],[7.000000|-20.573997|62.702249],[8.000000|-123.017453|38.684786]," +
                "[9.000000|-73.465564|-35.931306]}";

        InputEvent event = Protocol.parseEvent(inputData);
    }

    @Test
    public void addSnakeInputEventTest() throws Exception {
        final String inputData = "(09a32633-5db9-53cb-8c4b-7abc01495334)" +
                "addsnake:{(09a32633-5db9-53cb-8c4b-7abc01495334)[-23.526139|-14.087549|1.000000|0.000000|25.000000}";

        InputEvent event = Protocol.parseEvent(inputData);
    }

    @Test
    public void syncInputEventTest() throws Exception {
        final String inputData = "(09a32633-5db9-53cb-8c4b-7abc01495334)" +
                "sync:{" +
                "(015f55f4-f166-5b22-acf0-fc9da443b4e9)[-218.249999|-1.393302|1.000000|0.000000|25.000000]," +
                "(09a32633-5db9-53cb-8c4b-7abc01495334)[126.000006|-14.087549|1.000000|0.000000|25.000000]}";

        InputEvent event = Protocol.parseEvent(inputData);
    }
}
