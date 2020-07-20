package com.neolab.heroesGame.samplesSockets;

import com.neolab.heroesGame.server.serverNetwork.GameServer;

import java.io.*;
import java.net.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Консольный многопользовательский чат.
 * Сервер
 */
public class Server {

    public static final int PORT = 8081;

    enum Command {
        WARNING("warning"),
        STOP_CLIENT_FROM_SERVER("stop client from server"),
        STOP_CLIENT("stop client"),
        STOP_ALL_CLIENTS("stop all clients"),
        STOP_SERVER("stop server"),
        ;

        private final String commandName;

        Command(final String commandName) {
            this.commandName = commandName;
        }

        boolean equalCommand(final String message) {
            return commandName.equals(message);
        }
    }

    public static final ConcurrentLinkedQueue<PlayerSocket> serverList = new ConcurrentLinkedQueue<>();

    private void startServer() {
        System.out.println(String.format("Server started, port: %d", PORT));
        try (final ServerSocket serverSocket = new ServerSocket(PORT)) {
            // serverSocket.setSoTimeout(1000);

            //todo поменять
            int id = 1;
            String name = "one";

            while (true) { // приложение с помощью System.exit() закрывается по команде от клиента
                // Блокируется до возникновения нового соединения


                final Socket socket = serverSocket.accept();
                try {
                    PlayerSocket playerSocket =  new PlayerSocket(socket, id, name);
                    serverList.add(playerSocket);
                    if(serverList.size() == 2){
                        break;
                    }
                } catch (final IOException e) {
                    // Если завершится неудачей, закрывается сокет,
                    // в противном случае, нить закроет его:
                    socket.close();
                }

                id++;
                name = "two";
            }

            GameServer gameServer = new GameServer(serverList);
            gameServer.gameProcess();
            downService();

        } catch (final Exception e) {
            downService();
            e.printStackTrace();
        }
    }

    /**
     * закрытие сервера, удаление себя из списка нитей
     */
    private void downService() {
        for(PlayerSocket playerSocket : serverList){
            playerSocket.downService();
        }
    }

    public static void main(final String[] args) throws IOException {
        final Server server = new Server();
        server.startServer();
    }
}

