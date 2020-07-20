package com.neolab.heroesGame.samplesSockets;

import com.neolab.heroesGame.ClientPlayerImitation;
import com.neolab.heroesGame.client.dto.ExtendedServerResponse;
import com.neolab.heroesGame.enumerations.GameEvent;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;

/**
 * Консольный многопользовательский чат.
 * Клиент
 */
public class Client {

    private static final String IP = "127.0.0.1";//"localhost";
    private static final int PORT = 8081;
    private static final SimpleDateFormat DATE_FORMAT = PlayerSocket.DATE_FORMAT;

    private final String ip; // ip адрес клиента
    private final int port; // порт соединения

    private Socket socket = null;
    private BufferedReader in = null; // поток чтения из сокета
    private BufferedWriter out = null; // поток записи в сокет

    protected String requestJson;
    protected String answerJson;

    /**
     * для создания необходимо принять адрес и номер порта
     *
     * @param ip   ip адрес клиента
     * @param port порт соединения
     */
    private Client(final String ip, final int port){
        this.ip = ip;
        this.port = port;
    }

    private void startClient() {
        try {
            socket = new Socket(this.ip, this.port);
        } catch (final IOException e) {
            System.err.println("Socket failed");
            return;
        }

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (final IOException e) {
            downService();
            return;
        }

        System.out.println(String.format("Client started, ip: %s, port: %d", ip, port));
        new ReadWriteMsg().start(); // нить читающая сообщения из сокета в бесконечном цикле
    }

    /**
     * отсылка одного сообщения клиенту
     *
     * @param message сообщение
     */
    private void send(final String message) throws IOException {
        out.write( message + "\n");
        out.flush();
    }

    /**
     * закрытие сокета
     */
    private void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }
        } catch (final IOException ignored) {
        }
    }

    /**
     * нить для чтения и записи сообщений в сокет
     */
    private class ReadWriteMsg extends Thread {
        private ClientPlayerImitation player;

        @Override
        public void run() {

            try {
                /*
                 * получаем id и имя игрока от сервера и создаем нашего бота
                 */
                createPlayer();

                while (true) {
                    requestJson = in.readLine(); // ждем сообщения с сервера
                    if (requestJson == null) {
                        downService();
                        break;
                    }
                    ExtendedServerResponse response = ExtendedServerResponse.getResponseFromString(requestJson);
                    if (response.event == GameEvent.NOW_YOUR_TURN) {
                        answerJson = player.getAnswer(requestJson);
                        send(answerJson);
                    }
                    if (response.event == GameEvent.WAIT_ITS_NOT_YOUR_TURN) {
                        player.sendInformation(requestJson);
                    }
                }
            }
            catch (Exception e) {
                downService();
                e.printStackTrace();
            }
        }

        private void createPlayer() throws IOException {
            int playerId = Integer.parseInt(in.readLine());
            String playerName = in.readLine();
            player = new ClientPlayerImitation(playerId, playerName, false);
            send("OK");
        }
    }

    public static void main(final String[] args) {
        final Client client = new Client(IP, PORT);
        client.startClient();

        final Client client_2 = new Client(IP, PORT);
        client_2.startClient();

    }
}
