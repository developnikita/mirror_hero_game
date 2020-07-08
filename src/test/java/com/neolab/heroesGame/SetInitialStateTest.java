package com.neolab.heroesGame;

import com.neolab.heroesGame.arena.SquareCoordinate;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SetInitialStateTest {

    private static final String IP = "127.0.0.1";//"localhost";
    private static final int PORT = Server.PORT;

    @Test
    public void startServerAndClientsTest() throws InterruptedException {

        SquareCoordinate sq1 = new SquareCoordinate(1,1);
        SquareCoordinate sq2 = new SquareCoordinate(1,1);

        Map<SquareCoordinate, String> map = new HashMap<>();
        map.put(sq1, "aaa");

        assertEquals("aaa", map.get(sq1));


//        Thread server = new Thread(() -> {
//            final Server server1 = new Server();
//            try {
//                server1.startServer();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//
//        server.start();
//
//        Thread client = new Thread(() -> {
//            final Client client1 = new Client(IP, PORT);
//            client1.startClient();
//            try {
//                client1.send("stop server");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//
//        client.start();
//
//        server.join();
    }
}
