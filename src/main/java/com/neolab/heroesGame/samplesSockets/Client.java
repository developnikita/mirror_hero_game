package com.neolab.heroesGame.samplesSockets;

import com.neolab.heroesGame.ClientPlayerImitation;

import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;

/**
 * Консольный многопользовательский чат.
 * Клиент
 */
public class Client {

    private static final String IP = "127.0.0.1";//"localhost";
    private static final int PORT = Server.PORT;
    private static final SimpleDateFormat DATE_FORMAT = PlayerSocket.DATE_FORMAT;

    private final String ip; // ip адрес клиента
    private final int port; // порт соединения

    private Socket socket = null;
    private BufferedReader in = null; // поток чтения из сокета
    private BufferedWriter out = null; // поток записи в сокет

    protected String requestJson;
    protected String answerJson;

    public ClientPlayerImitation getPlayer() {
        return player;
    }

    private final ClientPlayerImitation player;

    /**
     * для создания необходимо принять адрес и номер порта
     *
     * @param ip   ip адрес клиента
     * @param port порт соединения
     */
    private Client(final String ip, final int port, Integer playerId, String clientName) throws Exception {
        this.player = new ClientPlayerImitation(playerId, clientName, false);
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
        @Override
        public void run() {

            try {
                while (true) {
                    requestJson = in.readLine(); // ждем сообщения с сервера
                    if(requestJson == null){
                        downService();
                        break;
                    }
                    answerJson = getPlayer().getAnswer(requestJson);
                    send(answerJson);
                }
            }
            catch (Exception e) {
                downService();
                e.printStackTrace();
            }
        }
    }

    public static void main(final String[] args) throws Exception {
        //todo имя и ид из должны быть из конфига
        final Client client = new Client(IP, PORT,  1,"игрок 1");
        client.startClient();

        final Client client_2 = new Client(IP, PORT,  2,"игрок 2");
        client_2.startClient();

    }
}
