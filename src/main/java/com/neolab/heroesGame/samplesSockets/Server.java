package com.neolab.heroesGame.samplesSockets;

import com.neolab.heroesGame.enumerations.GameEvent;

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
    private static int countPlayers  = 0;

    private void startServer() {
        System.out.println(String.format("Server started, port: %d", PORT));
        try (final ServerSocket serverSocket = new ServerSocket(PORT)) {

            while(true) {

                // Блокируется до возникновения нового соединения
                final Socket socket = serverSocket.accept();
                countPlayers = (countPlayers <= props.MAX_COUNT_PLAYERS) ? ++countPlayers : countPlayers;

                try {
                    if(countPlayers <= props.MAX_COUNT_PLAYERS){
                        createResponseSocket(socket, IntPlayerId.getNextId());
                        createGameRoom();
                    }
                    else {
                        final PlayerSocket playerSocket =  new PlayerSocket(socket, 0, "null");
                        playerSocket.send(GameEvent.MAX_COUNT_PLAYERS.toString());
                        playerSocket.downService();
                    }
                } catch (final IOException e) {
                    // Если завершится неудачей, закрывается сокет,
                    // в противном случае, нить закроет его:
                    socket.close();
                }
            }

        } catch (final Exception e) {
            downService();
            e.printStackTrace();
        }
    }

    /**
     * создаём серверный сокет для общения с конкретным клиентом
     * @param socket копия клиенсткого сокета на стороне сервера
     * @param playerId назначается сервером на игрока
     */
    private void createResponseSocket(Socket socket, int playerId) throws IOException, InterruptedException {
        final PlayerSocket playerSocket =  new PlayerSocket(socket, playerId, props.mapIdNamePlayers.get(playerId));

        if(playerSocket.isAssignIdAndNameClient()){
            serverList.add(playerSocket);
        }
    }

    /**
     * если в очереди есть 2 свободных игрока то создаем комнату
     */
    private void createGameRoom() throws Exception {
        if(serverList.size() >= 2){
            new GameRoom(serverList).start();
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

    /**
     * Для генеариия id игроков
     */
    private static class IntPlayerId {
        private static int id = 0;

        public static int getNextId() {
            return id++;
        }
    }

    public static void main(final String[] args) {
        final Server server = new Server();
        server.startServer();
    }
}

