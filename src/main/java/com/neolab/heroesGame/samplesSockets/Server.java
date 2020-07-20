package com.neolab.heroesGame.samplesSockets;

import com.neolab.heroesGame.server.serverNetwork.GameServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Консольный многопользовательский чат.
 * Сервер
 */
public class Server {
    private static final PropsServerManager props = new PropsServerManager();
    public static final int PORT = props.PORT;
    private static final ConcurrentLinkedQueue<PlayerSocket> serverList = new ConcurrentLinkedQueue<>();

    private void startServer() {
        System.out.println(String.format("Server started, port: %d", PORT));
        try (final ServerSocket serverSocket = new ServerSocket(PORT)) {

            for(Integer playerId : props.mapIdNamePlayers.keySet()) {
                // Блокируется до возникновения нового соединения
                final Socket socket = serverSocket.accept();
                try {
                    // создаём серверный сокет для общения с конкретным клиентом
                    PlayerSocket playerSocket =  new PlayerSocket(socket, playerId, props.mapIdNamePlayers.get(playerId));

                    if(playerSocket.isAssignIdAndNameClient()){
                        serverList.add(playerSocket);
                    }
                } catch (final IOException e) {
                    // Если завершится неудачей, закрывается сокет,
                    // в противном случае, нить закроет его:
                    socket.close();
                }
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

    public static void main(final String[] args) {
        final Server server = new Server();
        server.startServer();
    }
}

